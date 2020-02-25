package io.rmi.demo;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Seller implements Runnable {
    private FishImpl node1 = null;

    @Override
    public void run() {

    }

    public Seller(int port, String name) {
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            node1 = new FishImpl();
            registry.rebind(name, node1);
            System.out.println("Server is ready for " + name);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
