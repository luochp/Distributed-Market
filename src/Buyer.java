import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;


public class Buyer extends Peer {

    private int currentRequestMessageID = 0;
    private HashSet<Message> replyPool = new HashSet<>();   // Record all replys from other sellers

    public Buyer(int peerID, int peerType, IP ip, List<Integer> neighborPeerID, Map<Integer, IP> peerIDIPMap ){
        super(peerID, peerType, ip, neighborPeerID, peerIDIPMap);
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

    public class CheckReplyMessageAndBuyThread extends Thread{
        public void run() {
            while(true){

                LookUp();

                try {
                    Thread.sleep((int)(Node.INTERVAL_TIME/2));
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
        if(peerID == m.getBuyerPeerID()) { // initial buyer
            if (currentRequestMessageID == m.getID()){
                synchronized(replyPool){
                    replyPool.add( m );
                    return;
                }
            }
        } else { // mid node
            backward(m);
        }

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

        currentRequestMessageID = m.getID();
        replyPool = new HashSet<>();
        new latencyBuyThread().start();

        System.out.println( " Buyer " + this.peerID +
                            " LookUp " + m.getItemType() +
                            " MessageID " + m.getID() );
        spread(m);
    }

    // after sending out LOOKUP over a specific amount of time, attemp to BUY it.
    private class latencyBuyThread extends Thread{

        public latencyBuyThread(){
        }
        public void run() {
            try {
                Thread.sleep( (int)(Node.INTERVAL_TIME)/2 );
                sendBuyMessage();
            }catch(InterruptedException ie){
                System.out.println(ie);
            }
        }
    }

    private void sendBuyMessage(){
        Message buyM;
        synchronized(replyPool) {
            buyM = randomPickSeller();
        }
        if(buyM == null){
            System.out.println("No Seller found!" );
            return;
        }
        else{
            buyM = buyM.withOperationType(Message.Operation.BUY);
            sendMessage(buyM, buyM.getSellerIP());
            System.out.println( " Peer " + this.peerID +
                                " BUY " + buyM.getItemType() +
                                " from Peer" + buyM.getBuyerPeerID());
        }
    }

    private Message randomPickSeller(){
        if(replyPool.size() > 0){
            Iterator<Message> itr = replyPool.iterator();
            if(itr.hasNext()){
                Message m = itr.next();
                return m;
            }
        }
        return null;
    }

}