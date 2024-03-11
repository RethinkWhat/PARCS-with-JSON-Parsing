package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerMessage extends Remote {
    public boolean login(String username, String password) throws RemoteException;
}
