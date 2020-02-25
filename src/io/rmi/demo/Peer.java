package io.rmi.demo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Product extends Remote {
    public void cmdInterface() throws RemoteException;
}
