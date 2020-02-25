package io.rmi.demo;

public class Peer1 {
    public Peer1() {
        Thread seller = new Thread(new SellerRunnable(8000, "Fish"));
        Thread buyer = new Thread(new BuyerRunnable(8001, "HotDog"));
        seller.start();
        buyer.start();
    }

    public static void main(String[] args) {
        Peer1 peer = new Peer1();
    }
}
