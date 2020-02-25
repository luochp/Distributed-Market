package io.rmi.demo;

import java.rmi.RemoteException;

public interface Fish extends Product {
    public int add(int a, int b) throws RemoteException;
}
