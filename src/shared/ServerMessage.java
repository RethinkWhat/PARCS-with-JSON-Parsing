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
    boolean login(String username, String password) throws RemoteException;


    /**
     * Method to get the full name of user, given their username
     * @param username
     * @return
     * @throws RemoteException
     */
    String getFullName(String username) throws RemoteException;


    /**
     * Get the total booking count of a user
     * @param username
     * @return
     * @throws RemoteException
     */
    int getUserTotalBookings(String username) throws RemoteException;

    /**
     * Get the vehicle list of a user
     * @param username
     */
    Map<String, List<String>> getUserVehicles(String username) throws RemoteException;

    /**
     * Get the available time for a spot on a specific date
     * @param identifier
     * @param duration
     * @param date
     * @return
     */
    List<String> spotTimeAvailable(String identifier, String duration, String date) throws RemoteException;

    boolean bookReservation(String identifier, String date, String startTime, String duration, String username) throws RemoteException;

    /**

     * A method to allow user logging out from the main menu
     * @param username
     * @throws RemoteException
     */
    boolean logout(String username) throws RemoteException;

    /**
     * A method that allows the user to create account, where it passes information to server
     * @param firstName, lastName, username, phoneNumber, password
     * @throws RemoteException
     */
    boolean createAccount(String firstName, String lastName, String username, String phoneNumber, String password) throws RemoteException;
     
     /**
     * FROM client.model.application_pages.UserProfileModel
     * @param username
     * @throws RemoteException
     */
    List<List<String>> viewHistory(String username) throws RemoteException;

    List<String> getClosestReservation (String username) throws RemoteException;

    String getDuration (List<String> userReservation) throws RemoteException;

     /**
     *
     */
    void deleteAccount (String firstname) throws RemoteException;
    /**
     *
     */

    boolean editPassword(String newPassword) throws RemoteException;

}
