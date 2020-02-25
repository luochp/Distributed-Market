import java.io.*;
import java.util.*;
import java.lang.*;

public class Main{
    public static void main(String[] args)throws IOException, InterruptedException{
        // args[0] is the address of config file
        String fileAddress = args[0];
        int                   peerID          = Integer.parseInt(args[1]);
        int                   nodeType        = 0;   // Buyer=0  Seller=1  BuyerAndSeller=2
        String                nodeIP          = null;
        List<Integer>         neighborPeerID  = new ArrayList<Integer>();
        Map<Integer, String>  peerIPMap       = new HashMap<Integer, String>();

        // Read configuration file
        FileReader file = new FileReader(fileAddress);
        BufferedReader br = new BufferedReader(file);

        String line;
        while( (line = br.readLine())!= null){
            String[] params = line.split(" ");
            // Only Initialize specific Node in this terminal
            if( peerID != Integer.parseInt(params[0]) ){
                continue;
            }

            // Get Parameters from configuration file
            nodeType                      = Integer.parseInt(params[1]);
            nodeIP                        = params[2];
            String[] inputNeighborPeerIDs = params[3].split(",");
            String[] inputNeighborIPs     = params[4].split(",");

            for(int i=0; i<inputNeighborPeerIDs.length; i++){
                neighborPeerID.add(Integer.parseInt(inputNeighborPeerIDs[i]));
                peerIPMap.put(Integer.parseInt(inputNeighborPeerIDs[i]), inputNeighborIPs[i] );
            }
        }

        // Create Nodes and Run Nodes
        //myPeerID, NodeType, myIP, neighborPeerID, peerIPMap
        System.out.println("myPeerID = "+peerID);
        System.out.println("NodeType = "+nodeType);
        System.out.println("myIP = "+nodeIP);
        System.out.println("neighborPeerID:");
        System.out.println(neighborPeerID);
        System.out.println("peerIPMap:");
        System.out.println(peerIPMap);

        // Define Nodes as Buyer, Seller or Both

        System.out.print("Initialization Finished, System Running\n");
    }
}