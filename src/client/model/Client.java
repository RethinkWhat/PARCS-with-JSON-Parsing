package client.model;

import client.controller.LoginController;
import client.view.LoginView;
import server.model.DateTime;
import shared.ServerMessage;

import javax.swing.*;
import javax.swing.text.View;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


/**
 * The Client class is responsible for establishing a connection to the remote server
 * using Java RMI and providing functionalities to interact with the server.
 */
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
        Registry server = LocateRegistry.getRegistry(host, 2020);
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

    public String getTime() {
        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(time);
    }
}