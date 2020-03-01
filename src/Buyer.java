import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;


public class Buyer extends Peer {

    public Buyer(int peerID, int peerType, IP ip, List<Integer> neighborPeerID, Map<Integer, IP> peerIDIPMap ){
        super(peerID, peerType, ip, neighborPeerID, peerIDIPMap);
        System.out.println("Buyer Initiated");

        new LookUpThread().start();
    }

    public class LookUpThread extends Thread{
        public void run() {
            while(true){
                LookUp();
                try {
                    Thread.sleep(Node.INTERVAL_TIME);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    protected void handleLookUp(Message m) {
        m.getRoutePath().add(peerID);
        spread(m);
    }

    protected void handleReply(Message m) {
        if(peerID == m.getBuyerPeerID()) { // initial buyer
            try { // connect to seller directly
                String host = InetAddress.getLocalHost().getHostAddress();
                Node.RemoteInterface serverFunction = (Node.RemoteInterface) Naming.lookup("//" + host + ":" + m.getSellerIP().getPort() + "/" + "RMIserver");
                m.withOperationType(Message.Operation.BUY);
                serverFunction.handleMessage(m);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        } else { // mid node
            int lastIndex = m.getRoutePath().size() - 1;
            m.getRoutePath().remove(lastIndex); // remove itself from route path
            backward(m);
        }
    }

    // No one will send "BUY" message to buyer
    protected void handleBuy(Message m) {
        return;
    }

    protected void LookUp(){
        Message m = new Message().withOperationType(Message.Operation.LOOKUP)
                                 .withBuyerPeerID(this.peerID)
                                 .withItemType( Math.abs(new Random().nextInt()%3) );
        System.out.println("MessageID:" + m.getID() + ", Buyer " + this.peerID + ", LookUp " + m.getItemType() );
        spread(m);
    }


}