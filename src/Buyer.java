import java.io.*;
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

    }

    protected void handleReply(Message m) {

    }

    // No one will send "BUY" message to buyer
    protected void handleBuy(Message m) {
        return;
    }

    private void LookUp(){
        Message m = new Message().withOperationType(Message.Operation.LOOKUP)
                                 .withBuyerPeerID(this.peerID)
                                 .withItemType( Math.abs(new Random().nextInt()%3) );
        System.out.println("MessageID:" + m.getID() + ", Buyer " + this.peerID + ", LookUp " + m.getItemType() );
        spread(m);
    }



}