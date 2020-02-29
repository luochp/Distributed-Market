import java.io.*;
import java.util.*;


public class Seller extends Peer {

    private int productType;        // Integer in range [0,2]
    private int stock;
    private Set<Message> holdSet;   // After received "LOOKUP" message, seller hold product and waiting "BUY" message
                                    // Seller will discard message after messages' life time (for example, 100ms)

    public Seller(int peerID, int peerType, IP ip, List<Integer> neighborPeerID, Map<Integer, IP> peerIDIPMap ){
        super(peerID, peerType, ip, neighborPeerID, peerIDIPMap);
        System.out.println("Seller Initiated");
    }

    protected void handleLookUp(Message m) {

    }

    protected void handleReply(Message m) {

    }

    protected void handleBuy(Message m) {

    }

    // Delete messages that out of life time
    private void cleanHoldSet(){

    }


}