package server;

import server.controller.AdminApplicationController;
import server.model.ServerImplementation;
import server.view.AdminApplicationView;
import shared.ServerMessage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * The Main class of the admin/server application.
 */
public class Main {
    /**
     * Main entry point of the program.
     * @param args The command line argument.s
     */
    public static void main(String[] args) {
        new AdminApplicationController(new AdminApplicationView());
    }
}
