package io.rmi.demo;

public class Peer2 {
    public Peer2() {
        Thread seller = new Thread(new SellerRunnable(8001, "HotDog"));
        Thread buyer = new Thread(new BuyerRunnable(8000, "Fish"));
        seller.start();
        buyer.start();
    }

    public static void main(String[] args) {
        Peer2 peer = new Peer2();
    }
}
