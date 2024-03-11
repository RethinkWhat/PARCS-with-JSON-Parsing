package client.model;

import client.controller.LoginController;
import client.view.LoginView;
import shared.ServerMessage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {

    public Client() throws RemoteException, NotBoundException{
        Registry server = LocateRegistry.getRegistry(2000);
        this.remote = (ServerMessage) server.lookup("server");

    }
    ServerMessage remote;

    public ServerMessage getRemote() {
        return remote;
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            LoginModel model = new LoginModel(client);
            LoginView view = new LoginView();
            new LoginController(view, model);

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}