package io.rmi.demo;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SellerRunnable implements Runnable {
    private Peer peer = null;

    @Override
    public void run() {

    }

    public SellerRunnable(int port, String name) {
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            switch(name) {
                case "HotDog":
                    peer = new SellerImpl();
                    break;
                case "Fish":
                    peer = new BuyerImpl();
                    break;
                default:
                    System.out.println("Can't find " + name);
            }
            registry.rebind(name, peer);
            System.out.println("Server is ready for " + name);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
