import java.io.*;
import java.util.*;


public class BuyerAndSeller extends Node {

    public BuyerAndSeller(int myPeerID,int NodeType,  String myIP,List<String> neighborPeerID, Map<Integer, String> peerIPMap ){
        super(myPeerID, NodeType, myIP, neighborPeerID, peerIPMap);
        System.out.println("BuyerAndSeller Initiated");
    }

    public void lookup(){
        // Look up for product function
    }


    public void reply(){
        // Reply for Lookup Request
    }

}