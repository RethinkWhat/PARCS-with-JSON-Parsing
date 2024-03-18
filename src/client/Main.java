package client;

import client.controller.LoginController;
import client.model.Client;
import client.model.LoginModel;
import client.view.LoginView;
import client.view.ServerOfflineDialog;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * The main entry point of the Client application.
 */
public class Main {

    /**
     * The main method to start the client application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            Client client = new Client();
            LoginModel model = new LoginModel(client);
            LoginView view = new LoginView();
            new LoginController(view, model);
        } catch (RemoteException | NotBoundException e) {
            new ServerOfflineDialog();
            System.exit(0);
        }
    }
}
