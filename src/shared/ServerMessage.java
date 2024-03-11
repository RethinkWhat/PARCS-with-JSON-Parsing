package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ServerMessage extends Remote {

    /**
     * Method to handle the logging in of user
     * @param username
     * @param password
     * @return
     * @throws RemoteException
     */
    public boolean login(String username, String password) throws RemoteException;


    public String getFullName(String username) throws RemoteException;

    public int getUserTotalBookings(String username) throws RemoteException;

    public void getUserVehicles(String username);


}
