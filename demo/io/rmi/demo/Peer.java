package io.rmi.demo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Peer extends Remote {
    public void cmdInterface() throws RemoteException;
}
