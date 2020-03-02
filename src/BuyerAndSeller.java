import java.io.*;
import java.net.InetAddress;
import java.rmi.Naming;
import java.util.*;


public class BuyerAndSeller extends Peer {

    private int productType;        // Integer in range [0,2]
    private int stock;
    private Set<Message> holdSet;   // After received "LOOKUP" message, seller hold product and waiting "BUY" message
    // Seller will discard message after messages' life time (for example, 100ms)

    public BuyerAndSeller(int peerID, int peerType, IP ip, List<Integer> neighborPeerID, Map<Integer, IP> peerIDIPMap ){
        super(peerID, peerType, ip, neighborPeerID, peerIDIPMap);

    }

    protected void handleLookUp(Message m) {
        /*
        if(productType == m.getItemType() && stock > 1) { // true seller with enough stocks
            m.withOperationType(Message.Operation.REPLY)
             .withSellerPeerID(peerID)
             .withSellerIP(ip);
            backward(m);
        } else { // mid node
            m.getRoutePath().add(ip);
            spread(m);
        }

         */
    }

    protected void handleReply(Message m) {
        /*
        if(peerID == m.getBuyerPeerID()) { // initial buyer
            try { // connect to seller directly
                String host = InetAddress.getLocalHost().getHostAddress();
                Node.RemoteInterface serverFunction = (Node.RemoteInterface) Naming.lookup("//" + host + ":" + m.getSellerIP().getPort() + "/" + "RMIserver");
                m.withOperationType(Message.Operation.BUY);
                serverFunction.handleMessage(m);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        } else { // mid node
            int lastIndex = m.getRoutePath().size() - 1;
            m.getRoutePath().remove(lastIndex); // remove itself from route path
            backward(m);
        }

         */
    }

    protected void handleBuy(Message m) {
        /*
        System.out.println("MessageID:" + m.getID() + ", BuyerAndSeller " + this.peerID + ", handleBuy " + m.getItemType() );
        stock--;

         */
    }


}