package io.rmi.demo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class HotDogImpl extends UnicastRemoteObject implements HotDog {
    public HotDogImpl() throws RemoteException {
        super();
    }

    public int substract(int a, int b) {
        return a - b;
    }

    public void cmdInterface() {
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("Enter a: ");
            int a = in.nextInt();
            System.out.println("Enter b: ");
            int b = in.nextInt();
            System.out.printf("Result: %d", a-b);
        }
    }
}
