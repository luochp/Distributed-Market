import java.io.*;
import java.util.*;


public class Buyer extends Peer {

    public Buyer(int peerID, int peerType, IP ip, List<Integer> neighborPeerID, Map<Integer, IP> peerIDIPMap ){
        super(peerID, peerType, ip, neighborPeerID, peerIDIPMap);
        System.out.println("Buyer Initiated");
    }

    protected void handleLookUp(Message m) {

    }

    protected void handleReply(Message m) {

    }

    // No one will send "BUY" message to buyer
    protected void handleBuy(Message m) {
        return;
    }




}