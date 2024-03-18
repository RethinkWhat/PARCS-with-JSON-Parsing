package shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Template for a user object.
 * The user object contains the needed attributes to identify a single end user.
 * 1. Username
 * 2. Password encrypted in SHA 256
 * 3. Last Name
 * 4. First Name
 * 5. Phone Number
 * 6.
 * @see Vehicle
 */
public class User {
    /**
     * The username of the user.
     */
    private String username;
    /**
     * The password of the user.
     */

    /**
     * The type of the user
     */
    private String type;
    private String password;
    /**
     * The last name of the user.
     */
    private String lastName;
    /**
     * The first name of the user.
     */
    private String firstName;
    /**
     * The cellular number of user.
     */
    private String phoneNumber;
    /**
     * The list of vehicles owned by the user.
     */
    List<Vehicle> vehicles;


    public User(String username, String type, String password, String lastName, String firstName, String phoneNumber, ArrayList<Vehicle> vehicles) {
        this.username = username;
        this.type = type;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        if (vehicles == null)
            this.vehicles = new ArrayList<>();
        else
            this.vehicles = vehicles;
    }

    /**
     * Constructs a user with specified values.
     * The list of vehicles remain unpopulated.
     * @param username The specified username.
     * @param type The specified type
     * @param password The specified password.
     * @param lastName The specified last name.
     * @param firstName The specified first name.
     * @param phoneNumber The specified phone number.
     */
    /*
    public User(String username, String type, String password, String lastName, String firstName, String phoneNumber) {
        this.username = username;
        this.type = type;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.vehicles = new ArrayList<>();
    }

     */

/*
    public User(ArrayList<String> userInfo) {
        this.username = userInfo.get(0);
        this.type = userInfo.get(1);
        this.password = userInfo.get(2);
        this.lastName = userInfo.get(3);
        this.firstName = userInfo.get(4);
        this.phoneNumber = userInfo.get(5);
        this.vehicles = new ArrayList<>();
        for (int x = 6; x <userInfo.size(); x++) {
            vehicles.add(new Vehicle(userInfo.get(x).split(",")));
        }
    }

 */

    /**
     * Retrieves the current username of the user.
     * @return The current username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets a new username of the user.
     * @param username The new username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the current decoded password of the user.
     * The password is decoded using SHA 256.
     * TODO: Decoding algorithm using SHA 256
     * @return The current decoded password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a new encrypted password of the user.
     * @param password A new encrypted password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the current last name of the user.
     * @return The current last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets a new last name of the user.
     * @param lastName The new last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the current first name of the user.
     * @return The current first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets a new first name of the user.
     * @param firstName The new first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the current phone number of the user.
     * @return The current phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets a new phone number of the user.
     * @param phoneNumber the current phone number of the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the current vehicles of the user.
     * @return The current vehicles of the user.
     */
    public List<Vehicle> getVehicles() {
        return this.vehicles;
    }

    /**
     * Sets a new list of vehicles of the user.
     * @param vehicles The new list of vehicles of the user.
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Returns a string representation of the user.
     * The string contains only the username.
     *
     * @return A string representation of the user.
     */
    @Override
    public String toString() {
        String toReturn = username + "," + password + "," + lastName + "," + firstName + "," + phoneNumber;
        if (vehicles!= null) {
            for (Vehicle vehicle: vehicles) {
                toReturn += "," + vehicle;
            }
        }else {
            toReturn += "," + null;
        }
        return toReturn;
    }
}
