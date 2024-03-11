package server;

import shared.ServerMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class ServerImplementation extends UnicastRemoteObject implements ServerMessage {

    protected ServerImplementation() throws RemoteException {
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        UserParser userParser = new UserParser();

        Map<String, String> accounts = userParser.getUserLoginCredentials();
        String associatedPass = accounts.getOrDefault(username, "");
        boolean authenticateLogin = password.equals(associatedPass);
        System.out.println(authenticateLogin);

        /*
        if (authenticateLogin) {
            if (server.getUserLog().contains(username)) {
                writer.println("userExists");
            }else {
                server.accountLogin(username);
                writer.println("true");
            }
        }
        else
            writer.println("false");
    }
         */
        return authenticateLogin;
    }
}
