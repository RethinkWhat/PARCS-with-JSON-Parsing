package client.model;

import client.controller.LoginController;
import client.view.LoginView;
import shared.ServerMessage;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {
    ServerMessage remote;
    String username;

    public Client() throws RemoteException, NotBoundException{
        Registry server = LocateRegistry.getRegistry(2000);
        this.remote = (ServerMessage) server.lookup("server");

    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return LiveDateTime.getDate();
    }

    /**
     * Logs out the user from the server and starts the GUI.
     */
    public boolean logout(String username) {
        try{
            return this.remote.logout(username);
        }catch (RemoteException re){
            re.printStackTrace();
            return false;
        }
    }
}