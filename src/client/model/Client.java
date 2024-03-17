package client.model;

import client.controller.LoginController;
import client.view.LoginView;
import shared.ServerMessage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Client {
    ServerMessage remote;
    String username;

    String host;

    public Client() throws RemoteException, NotBoundException{
        try {
            Scanner reader = new Scanner(new File("src/client/host"));
            host = reader.nextLine();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Registry server = LocateRegistry.getRegistry(host, 2000);
        this.remote = (ServerMessage) server.lookup("server");

    }

    public ServerMessage getRemote() {
        return remote;
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