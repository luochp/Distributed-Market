import java.io.*;
import java.util.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Node implements Runnable {
    private Peer peer;

    protected final IP ip;
    protected final int peerID;

    public Queue<Message> messageQueue;

    public static int INTERVAL_TIME =  5000;

    public Node(int peerID, int peerType, IP ip, List<Integer> neighborPeerID, Map<Integer, IP> peerIDIPMap){
        this.peerID = peerID;
        this.ip = ip;
        this.messageQueue = new LinkedList<>();
        peerGeneration(peerID, peerType, ip, neighborPeerID, peerIDIPMap);

        new MessageQueueCheckThread().start();

        new Thread(new RMIServerThread(ip)).start();
    }

    public void run(){

    }


    // For each node, check its message constantly
    public class MessageQueueCheckThread extends Thread{
        public void run(){

            while(true){
                checkMessageQueue();
            }

        }
    }


    // implement it with rmi
    // functions to add Message into messageQueue
    public class RMIServerThread implements Runnable{
        private IP my_ip;
        private String name = "RMIserver";

        public RMIServerThread(IP ip){
            this.my_ip = ip;

            try {
                Registry registry = LocateRegistry.createRegistry(ip.getPort());
                ServerFunction serverInstance = new ServerFunction();
                registry.rebind(name, serverInstance);
                System.out.println("Server is ready for " + name);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void run(){
            System.out.println("ServerThread Running" + peer.type);
            /*
            while(true){
                System.out.println("ServerThread Running" + peer.type);
            }
            */

            // Here is just a test
            while(true) {
                System.out.println("Requesting Server");
                try {
                    Thread.sleep(5000);
                    String host = InetAddress.getLocalHost().getHostAddress();
                    RemoteInterface serverFunction = (RemoteInterface) Naming.lookup("//" + host + ":" + peer.peerIDIPMap.get( peer.neighborPeerID.get(0) ).getPort()  + "/" + "RMIserver");
                    System.out.println(serverFunction.helloworld());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }

    public interface RemoteInterface extends Remote {
        public String helloworld() throws RemoteException;
    }

    public class ServerFunction extends UnicastRemoteObject implements RemoteInterface {
        public ServerFunction() throws RemoteException {
            super();
        }

        @Override
        public String helloworld() throws RemoteException {
            return "Hello World!";
        }
    }


    // generate peer based on its type
    private void peerGeneration(int peerID, int peerType, IP ip, List<Integer> neighborPeerID, Map<Integer, IP> peerIDIPMap){
        if(peerType == 0){       // Buyer
            peer = new Buyer(peerID, peerType, ip, neighborPeerID, peerIDIPMap);
        }
        else if(peerType == 1) { //Seller
            peer = new Seller(peerID, peerType, ip, neighborPeerID, peerIDIPMap);
        }
        else{                    // 3: BuyerAndSeller
            peer = new BuyerAndSeller(peerID, peerType, ip, neighborPeerID, peerIDIPMap);
        }
    }

    // If there is a message, then handle it
    // to prevent problems in paralle computing, we serialize message
    private void checkMessageQueue(){
        synchronized (messageQueue){
            if( messageQueue.isEmpty() != true){
                peer.handleMessage( messageQueue.poll() );
                messageQueue.notifyAll();
            }
        }
    }


}