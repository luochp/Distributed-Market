import java.io.*;
import java.rmi.Naming;
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

    protected void LookUp() {};


    // When Seller send "REPLY" back to buyer, mid nodes need to pass message backward the routepath
    public void backward(Message m){
        int lastIndex = m.getRoutePath().size() - 1;
        IP prevIP = m.getRoutePath().remove(lastIndex);
        try {
            System.out.println("Reply to IP " + "//" + prevIP.getAddr() + ":" + prevIP.getPort() );
            Node.RemoteInterface serverFunction = (Node.RemoteInterface) Naming.lookup("//" + prevIP.getAddr() + ":" + prevIP.getPort() + "/" + "RMIserver");
            serverFunction.handleMessage(m);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Spread message to all its neighbor
    public void spread(Message m){
        // node has been visited
        for(IP routeIP: m.getRoutePath()) {
            if(ip.getAddr().equals(routeIP.getAddr()) && ip.getPort() == routeIP.getPort()) {
                return;
            }
        }

        m.getRoutePath().add(ip);

        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            for (int peerID : peerIDIPMap.keySet()) {
                IP ip = peerIDIPMap.get(peerID);
                Node.RemoteInterface serverFunction = (Node.RemoteInterface) Naming.lookup("//" + host + ":" + ip.getPort() + "/" + "RMIserver");
                serverFunction.handleMessage(m);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }



}