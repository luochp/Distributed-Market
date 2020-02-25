package io.rmi.demo;

import java.rmi.RemoteException;

public interface HotDog extends Product {
    public int substract(int a, int b) throws RemoteException;
}
