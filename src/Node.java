import java.io.*;
import java.util.*;
import java.net.*;

public class Node implements Runnable{
    protected final int NodeType;    // 0=Buyer   1=Seller    2=BuyerAndSeller
    protected final int myPeerID;
    protected final String myIP;
    protected final List<String> neighborPeerID;
    protected final Map<Integer, String> peerIPMap;    // neighborPeerID   ->    neighborIP

    public Queue<Message> messageQueue;



    public Node(int myPeerID, int NodeType, String myIP,List<String> neighborPeerID, Map<Integer, String> peerIPMap ){
        this.myPeerID = myPeerID;
        this.NodeType = NodeType;
        this.myIP = myIP;
        this.neighborPeerID = neighborPeerID;
        this.peerIPMap = peerIPMap;
    }

    public void run(){
        // Main Thread in Each Node
    }

    protected void forward(Message msg){
        // Forward Message to neighbors
    }

}