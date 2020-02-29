import java.io.*;
import java.util.*;
import java.net.*;

public class Peer {
    protected final int type;    // 0=Buyer   1=Seller    2=BuyerAndSeller
    protected final IP ip;
    protected final int peerID;
    protected final List<Integer> neighborPeerID;
    protected final Map<Integer, IP> peerIDIPMap;    // neighborPeerID   ->    neighborIP

    public Peer(int peerType, int peerID, IP ip, List<Integer> neighborPeerID, Map<Integer, IP> peerIDIPMap){
        this.type = peerType;
        this.peerID = peerID;
        this.ip = ip;
        this.neighborPeerID = neighborPeerID;
        this.peerIDIPMap = peerIDIPMap;
    }

    public void handleMessage(Message m){
        switch (m.getOperationType()){
            case LOOKUP:
                handleLookUp(m);
                break;
            case REPLY:
                handleReply(m);
                break;
            case BUY:
                handleBuy(m);
                break;
        }
    }

    protected void handleLookUp(Message m) {};
    protected void handleReply(Message m) {};
    protected void handleBuy(Message m) {};


    // When Seller send "REPLY" back to buyer, mid nodes need to pass message backward the routepath
    public void backward(Message m){

    }



}