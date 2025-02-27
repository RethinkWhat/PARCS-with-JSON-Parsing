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
     * Method to validate if a user is already logged in
     * @param username
     * @return
     * @throws RemoteException
     */
    boolean isUserLoggedIn(String username) throws RemoteException;


    /**
     * Method to get the full name of user, given their username
     * @param username
     * @return
     * @throws RemoteException
     */
    String getFullName(String username) throws RemoteException;
    String getFirstName(String username) throws RemoteException;
    String getLastName(String username) throws RemoteException;
    String getContact(String username) throws RemoteException;

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

    List<Vehicle> getUserVehicles(String username) throws RemoteException;

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


    /**
     * This method allows the user to edit the user's vehicle information.
     *
     * @param username
     * @param plateNumber
     * @param newPlate
     * @return
     * @throws RemoteException
     */
    boolean editVehicleInformation(String username,String plateNumber, String newPlate, String newModel, String newType) throws RemoteException;

    /**
     * This method edits the user's information with the first name, last name, and contact Number.
     *
     * @param firstname
     * @param lastName
     * @param contactNo
     * @return
     * @throws RemoteException
     */
    boolean editUserInformation(String username, String firstname, String lastName, String contactNo) throws  RemoteException;


    List<String> getClosestReservation (String username) throws RemoteException;

    String getDuration (List<String> userReservation) throws RemoteException;

    /** This method delete the user's account permanently.
     * @param username
     * @throws RemoteException
     */
    void deleteAccount (String username) throws RemoteException;

   /** This method will edit the user's password
     * @param password
     * @param newPassword
     * @return
     * @throws RemoteException
     */
    boolean editPassword(String username, String password, String newPassword) throws RemoteException;

    /**
     * Retrieves all car bookings in a list of lists.
     * @return List of lists of all car bookings.
     * @throws RemoteException If error or exception occurs.
     */
    List<List<String>> getAllCarBookings() throws RemoteException;

    /**
     * Retrieves all motor bookings in a list of lists.
     * @return List of lists of all car bookings.
     * @throws RemoteException If error or exception occurs.
     */
    List<List<String>> getAllMotorBookings() throws RemoteException;


    /**
     * Method to handle adding a vehicle
     * @param username
     * @param type
     * @param model
     * @param plateNumber
     * @return
     * @throws RemoteException
     */
    boolean addVehicle(String username, String type, String model, String plateNumber) throws RemoteException;
}
