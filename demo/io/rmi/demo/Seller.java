package io.rmi.demo;

import java.rmi.RemoteException;

public interface Seller extends Peer {
    public int subtract(int a, int b) throws RemoteException;
}
