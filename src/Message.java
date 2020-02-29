import java.io.*;
import java.util.*;

public class Message implements Serializable{
    private int id;
    public static final int MAX_HOP = 6;
    private Operation operationType;
    private List<Integer> routePath;   // from buyer to seller, represented by peerID
    private int buyerPeerID;           // assigned when creating the message
    private int sellerPeerID;          // assigned only when sending "REPLY" message
    private IP buyerIP;                // assigned only when sending "BUY" message
    private IP sellerIP;               // assigned only when sending "REPLY" message

    private int itemType;
    private int hop;

    public Message(){
        generateID();
        this.operationType = null;
        this.routePath = new LinkedList<Integer>();
        this.buyerPeerID = -1;
        this.sellerPeerID = -1;
        this.buyerIP = null;
        this.sellerIP = null;
        this.itemType = -1;
        this.hop = 0;
    }

    private void generateID(){
        Random r = new Random();
        id = r.nextInt(100000);  // generate id in range [0-99999]
    }

    public Message withOperationType(Operation operationType){
        this.operationType = operationType;
        return this;
    }

    public Message withBuyerPeerID(int buyerPeerID){
        this.buyerPeerID = buyerPeerID;
        return this;
    }

    public Message withSellerPeerID(int sellerPeerID){
        this.sellerPeerID = sellerPeerID;
        return this;
    }

    public Message withBuyerIP(IP buyerIP){
        this.buyerIP = buyerIP;
        return this;
    }

    public Message withSellerIP(IP sellerIP){
        this.sellerIP = sellerIP;
        return this;
    }

    public Message withItemType(int itemType){
        this.itemType = itemType;
        return this;
    }

    public int getID(){
        return this.id;
    }

    public int getBuyerPeerID(){
        return this.buyerPeerID;
    }

    public int getSellerPeerID(){
        return this.sellerPeerID;
    }

    public IP getBuyerIP(){
        return this.buyerIP;
    }

    public IP getSellerIP(){
        return this.sellerIP;
    }

    public int getItemType(){
        return this.itemType;
    }

    public Operation getOperationType(){
        return this.operationType;
    }

    public static enum  Operation {
        LOOKUP,
        REPLY,
        BUY
    }

}