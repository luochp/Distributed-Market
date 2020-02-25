package io.rmi.demo;

import java.rmi.RemoteException;

public interface Buyer extends Peer {
    public int add(int a, int b) throws RemoteException;
}
