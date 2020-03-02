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
        // dont spread if node in the path
        for(IP routeIP: m.getRoutePath()) {
            if(ip.getAddr().equals(routeIP.getAddr()) && ip.getPort() == routeIP.getPort()) {
                return;
            }
        }

        // dont spread if step over MAX_HOP
        if( m.getHop() >= m.MAX_HOP ){
            return;
        }
        m.hopAddOne();

        m.routePathAddRear(this.ip);
        spread(m);
    }

    protected void handleReply(Message m) {
        /*
        if(peerID == m.getBuyerPeerID()) { // initial buyer
            try { // connect to seller directly
                String host = InetAddress.getLocalHost().getHostAddress();
                Node.RemoteInterface serverFunction = (Node.RemoteInterface) Naming.lookup("//" + host + ":" + m.getSellerIP().getPort() + "/" + "RMIserver");
                System.out.println("//" + host + ":" + m.getSellerIP().getPort() + "/" + "RMIserver");
                m.withOperationType(Message.Operation.BUY);
                System.out.println("MessageID:" + m.getID() + ", Buyer " + this.peerID + ", handleReply " + m.getItemType() );
                serverFunction.handleMessage(m);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        } else { // mid node
            int lastIndex = m.getRoutePath().size() - 1;
            m.getRoutePath().remove(lastIndex); // remove itself from route path
            System.out.println("MessageID:" + m.getID() + ", Buyer " + this.peerID + ", handleReply " + m.getItemType() );
            backward(m);
        }
        */
    }

    // No one will send "BUY" message to buyer
    protected void handleBuy(Message m) {
        return;
    }

    private void LookUp(){
        // Create Message
        Message m = new Message().withOperationType(Message.Operation.LOOKUP)
                                 .withBuyerPeerID(this.peerID)
                                 .withBuyerIP(this.ip)
                                 .withItemType( Math.abs(new Random().nextInt()%3) );
        m.routePathAddRear(this.ip);
        m.hopAddOne();

        System.out.println("MessageID:" + m.getID() + ", Buyer " + this.peerID + ", LookUp " + m.getItemType() );
        spread(m);
    }


}