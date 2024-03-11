package server;

import shared.ServerMessage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {
        try {
            ServerMessage server = new ServerImplementation();
            Registry reg = LocateRegistry.createRegistry(2000);
            reg.rebind("server", server);
            System.out.println("bound");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
