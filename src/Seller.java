import java.io.*;
import java.net.InetAddress;
import java.rmi.Naming;
import java.util.*;


public class Seller extends Peer {

    private int productType;        // Integer in range [0,2]
    private int stock = 0;
    private Set<Message> holdSet;   // After received "LOOKUP" message, seller hold product and waiting "BUY" message
                                    // Seller will discard message after messages' life time (for example, 100ms)

    public Seller(int peerID, int peerType, IP ip, List<Integer> neighborPeerID, Map<Integer, IP> peerIDIPMap ){
        super(peerID, peerType, ip, neighborPeerID, peerIDIPMap);
        checkAndIniStock();
        holdSet = new HashSet<Message>();

        new StockAndHoldSetCheckThread().start();

    }

    public class StockAndHoldSetCheckThread extends Thread{
        public void run() {
            while(true){
                cleanHoldSet();
                checkAndIniStock();
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

        if ( m.getItemType() == productType && stock > holdSet.size() ){    // can provide goods
            if ( holdSetHas(m) ) { // Replyed before
                return;
            }
            else{   // new lookup message
                System.out.println( " Peer " + this.peerID +
                                    " Reply LookUp from " + m.getBuyerPeerID() +
                                    " for " + m.getItemType() +
                                    " MessageID " + m.getID() );
                m.startHold(System.currentTimeMillis());
                holdSet.add(m);
                // send "REPLY" message
                m = m.withOperationType(Message.Operation.REPLY)
                        .withSellerPeerID(peerID)
                        .withSellerIP(ip);
                backward(m);
            }
        }
        else {      // cannot provide goods
            System.out.println( " Peer " + this.peerID +
                                " Spread LookUp from " + m.getBuyerPeerID() +
                                " for " + m.getItemType() +
                                " MessageID " + m.getID() );
            m.routePathAddRear(this.ip);
            spread(m);
        }
    }

    protected void handleReply(Message m) {
        backward(m);
    }

    protected void handleBuy(Message m) {
        System.out.println( " Peer " + this.peerID +
                " received BUY from " + m.getBuyerPeerID() +
                " for " + m.getItemType() +
                " and Sold it" +
                " MessageID " + m.getID() );
        holdSetRemove(m);
        stock--;
    }

    // Check if holdSet has message m inside
    private boolean holdSetHas(Message inputM){
        Iterator<Message> iterator = holdSet.iterator();
        while (iterator.hasNext()) {
            Message m = iterator.next();
            if ( m.getID() == inputM.getID() ) {
                return true;
            }
        }
        return false;
    }

    // Delete messages that out of life time
    private void cleanHoldSet(){
        Iterator<Message> iterator = holdSet.iterator();
        while (iterator.hasNext()) {
            Message m = iterator.next();
            if ( m.outDate(System.currentTimeMillis()) ) {
                iterator.remove();
            }
        }
    }

    private void checkAndIniStock(){
        if (stock == 0){
            productType = Math.abs(new Random().nextInt()%3);
            stock = new Random().nextInt(5);
        }
    }

    private void holdSetRemove(Message m){
        Iterator<Message> iterator = holdSet.iterator();
        while (iterator.hasNext()) {
            Message msg = iterator.next();
            if ( msg.getID() == m.getID() ) {
                iterator.remove();
            }
        }
    }

}