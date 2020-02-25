package io.rmi.demo;

import java.net.InetAddress;
import java.rmi.Naming;

public class Buyer implements Runnable{
    private FishImpl node1 = null;

    @Override
    public void run() {

    }

    public Buyer(int port, String name) {
        while(true) {
            try {
                System.out.println("Buying " + name);
                Thread.sleep(5000);
                String host = InetAddress.getLocalHost().getHostAddress();
                Product product = (Product) Naming.lookup("//" + host + ":" + port + "/" + name);
                product.cmdInterface();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
