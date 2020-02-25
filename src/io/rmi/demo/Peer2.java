package io.rmi.demo;

public class Server2 {
    public Server2() {
        Thread seller = new Thread(new Seller(8001, "Node2"));
        Thread buyer = new Thread(new Buyer(8000, "Node1"));
        seller.start();
        buyer.start();
    }

    public static void main(String[] args) {
        Server2 server = new Server2();
    }
}
