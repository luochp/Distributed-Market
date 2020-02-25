package io.rmi.demo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class BuyerImpl extends UnicastRemoteObject implements Buyer {
    public BuyerImpl() throws RemoteException {
        super();
    }

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

    public void cmdInterface() throws RemoteException {
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("Enter a: ");
            int a = in.nextInt();
            System.out.println("Enter b: ");
            int b = in.nextInt();
            System.out.printf("Result: %d\n", add(a, b));
        }
    }
}
