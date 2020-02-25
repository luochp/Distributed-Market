import java.io.*;
import java.util.*;


public class Seller extends Node {

    public Seller(int myPeerID, int NodeType, String myIP,List<String> neighborPeerID, Map<Integer, String> peerIPMap ){
        super(myPeerID, NodeType, myIP, neighborPeerID, peerIPMap);
        System.out.println("Seller Initiated");
    }

    public void reply(){
        // Reply for Lookup Request
    }

}