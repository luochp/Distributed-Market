package io.rmi.demo;

import java.rmi.RemoteException;

public class Server1 {
    public Server1() {
        Thread seller = new Thread(new Seller(8000, "Node1"));
        Thread buyer = new Thread(new Buyer(8001, "Node2"));
        seller.start();
        buyer.start();
    }

    public static void main(String[] args) throws RemoteException {
        Server1 server = new Server1();
    }
}
