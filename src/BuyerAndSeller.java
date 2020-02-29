import java.io.*;
import java.util.*;


public class BuyerAndSeller extends Peer {

    public BuyerAndSeller(int peerID, int peerType, IP ip, List<Integer> neighborPeerID, Map<Integer, IP> peerIDIPMap ){
        super(peerID, peerType, ip, neighborPeerID, peerIDIPMap);
        System.out.println("BuyerAndSeller Initiated");
    }

    protected void handleLookUp(Message m) {

    }

    protected void handleReply(Message m) {

    }

    protected void handleBuy(Message m) {

    }


}