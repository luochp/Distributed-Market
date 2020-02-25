package io.rmi.demo;

import java.net.InetAddress;
import java.rmi.Naming;

public class BuyerRunnable implements Runnable{
    private BuyerImpl node1 = null;

    @Override
    public void run() {

    }

    public BuyerRunnable(int port, String name) {
        while(true) {
            try {
                System.out.println("Buying " + name);
                Thread.sleep(5000);
                String host = InetAddress.getLocalHost().getHostAddress();
                Peer peer = (Peer) Naming.lookup("//" + host + ":" + port + "/" + name);
                peer.cmdInterface();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
