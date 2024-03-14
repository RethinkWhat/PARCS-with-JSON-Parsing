package client.model.application_pages;

import client.model.Client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The UserProfileModel contains the data.
 */
public class UserProfileModel {
    /**
     * The connection of the client to the server
     */
    private Client client;
    /**
     * The first name of the user.
     */
    private String firstName;
    /**
     * The last name of the user.
     */
    private String lastName;
    /**
     * The username of the user.
     */
    private String username;
    /**
     * The encrypted password of the user.
     */
    private String password;
    /**
     * The contact number of the user.
     */
    private String contactNo;
    /**
     * The mapping of the user to their vehicles.
     */
    private HashMap<String, List<String>> vehicles;
    /**
     * The String array list of vehicles, populated by a HashMap.
     */
    private ArrayList<String> vehicleList;


    /** Histroy page variables */
    private int historyPageNo =0;

    private List<List<String>> bookings;

    /**
     * Constructs a model of UserProfile with a specified client.
     *
     * @param client The specified client.
     */
    public UserProfileModel(Client client) {
        this.client = client;
    }

    public List<List<String>> getBookings() {
        return bookings;
    }

    /**
     * Retrieves the user's vehicles.
     */
    public void getVehiclesInfo() {
        //TODO: RMI Implementation
    }

    /**
     * Retrieves the full credentials (excluding password) of the current user.
     */
    public void getCredentials() {
        //TODO: RMI Implementation
    }

    /**
     * Method to handle deleting a user account
     */
    public void deleteAccount() {
        //TODO: RMI Implementation
         
        try{
            this.remote.deleteAccount(username);
        }catch (RemoteException re){
            re.printStackTrace();
        }
    }


    /**
     * Retrieves the current first name of the user.
     *
     * @return The current first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the current last name of the user.
     *
     * @return The current last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieves the current username of the user.
     *
     * @return The current username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the current password of the user.
     *
     * @return The current password (encrypted).
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the current contact number of the user.
     *
     * @return The current contact number.
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * Retrieves the list of vehicles associated with the user.
     *
     * @return The list of vehicles in a formatted string.
     */
    public ArrayList<String> getVehicleList() {
        return vehicleList;
    }

    /**
     * Edits the user's information with the specified first name, last name, and contact number.
     *
     * @param firstName The new first name.
     * @param lastName  The new last name.
     * @param contactNo The new contact number.
     * @return True if the edit was successful. False if otherwise.
     */
    public boolean editUserInformation(String firstName, String lastName, String contactNo) {
        //TODO: RMI Implementation
        return false;
    }

    /**
     * Edits the user's vehicle information with a specified vehicle type, model, and plateNumber.
     *
     * @param plateNumber        The specified plateNumber.
     * @param newInfo       The new vehicle information.
     * @param plateNumber The specified plate number.
     * @return True if the edit was successful. False if otherwise.
     */
    public boolean editVehicleInfo(String plateNumber, String newInfo) {
        //TODO: RMI Implementation
        return false;
    }

    /**
     * Changes the password of the user with a specified new password.
     * The client sends the information to the server and parses it.
     *
     * @param password The specified new password.
     * @return True if successful. False if otherwise.
     */
    public boolean editPassword(String password) {
        //TODO: RMI Implementation
        try{
            this.getClient().getRemote().editPassword(password);
            return true;
        }catch (RemoteException e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Retrieves and displays the booking history of the user.
     */
    public void viewHistory() {
        try {
            this.bookings = client.getRemote().viewHistory(client.getUsername());
        }catch (RemoteException re){
            re.printStackTrace();
        }
    }

    /**
     * Retrieves the current client.
     *
     * @return The current client.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Validates and updates the current index for viewing booking history.
     *
     * @param number The specified number to move forward or backward in history.
     * @return True if the index is valid and updated. False if otherwise.
     */
    public boolean validateEndOrStart(int number) {
        int indexToCheck = historyPageNo + number;
        boolean isValidIndex = indexToCheck >= 0 && indexToCheck < bookings.size();
        if (isValidIndex) {
            historyPageNo += number;
        }
        return isValidIndex;
    }

    /**
     * Retrieves the type of the current booking.
     *
     * @return The type of the current booking.
     */
    public String getType() {
        return bookings.get(historyPageNo).get(0);
    }

    /**
     * Retrieves the parking spot of the current booking.
     *
     * @return The parking spot of the current booking.
     */
    public String getSpot() {
        return bookings.get(historyPageNo).get(1);
    }

    /**
     * Retrieves the check-in time of the current booking.
     *
     * @return The check-in time of the current booking.
     */
    public String getCheckIn() {
        return bookings.get(historyPageNo).get(2);
    }

    /**
     * Retrieves the check-out time of the current booking.
     *
     * @return The check-out time of the current booking.
     */
    public String getCheckOut() {
        return bookings.get(historyPageNo).get(3);
    }

    /**
     * Retrieves the duration of the current booking.
     *
     * @return The duration of the current booking.
     */
    public String getDuration() {
        return bookings.get(historyPageNo).get(4);
    }
    

}
