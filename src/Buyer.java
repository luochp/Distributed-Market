import java.io.*;
import java.util.*;


public class Buyer extends Node {

    public Buyer(int myPeerID, int NodeType, String myIP,List<String> neighborPeerID, Map<Integer, String> peerIPMap ){
        super(myPeerID, NodeType, myIP, neighborPeerID, peerIPMap);
        System.out.println("Buyer Initiated");
    }

    public void lookup(){
        // Look up for product function
    }

}