package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shared.User;
import shared.Vehicle;

import java.io.*;
import java.util.*;

public class GsonUserParser {

    Gson gson;
    BufferedReader reader;

    volatile ArrayList<User> userArrayList;

    /** File to hold user objects */
    private final File file = new File("src/server/res/userList.json");

    /**
     * Default Constructor
     * Define gson object and fill userArrayList
     */
    public GsonUserParser() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        userArrayList = new ArrayList<>();
        Collections.addAll(userArrayList, getUsers());
    }

    public synchronized void updateUserList() {
        try {
            String jsonString = gson.toJson(userArrayList);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method to get a list of all the users
     * @return
     */
    private User[] getUsers() {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        User[] userList = gson.fromJson(reader, User[].class);
        return userList;
    }

    /**
     * Method to validate whether a user with a passed in username and password exist
     * @param username
     * @param password
     * @return
     */
    public boolean validateLogin(String username, String password) {
        for (User user : userArrayList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                if (user.getPassword().equals(password))
                    return true;
                break;
            }
        }
        return false;
    }

    /**
     * Method to get the full name of a user
     * @param username
     * @return
     */
    public String getUserFullName(String username) {
        for (User user : userArrayList) {
            if (user.getUsername().equals(username)) {
                return user.getFirstName() + " " + user.getLastName();
            }
        }
        return "";
    }

    /**
     * Method to get a list of all the vehicles of a user
     * Map<PlateNumber, List<Type,Model>>
     * @param username
     * @return
     */
    public List<Vehicle> getUserVehicles(String username) {
        List<Vehicle> vehicleList = new ArrayList<>();
        for (User user : userArrayList) {
            if (user.getUsername().equals(username)) {
                if (user.getVehicles() != null)
                    return user.getVehicles();
            }
        }
        return vehicleList;
        
    }


    /**
     * This method allows the program to insert values concerning new users to the Json file.
     * To do this, ArrayList userArrayList is used to store arrays of current users stored in the
     * server, and then parameters regarding new user would be combined into a variable called
     * [User], before inserting it into an array and parse it into Json file
     * @param firstName First name of the new user
     * @param lastName last name of the new user
     * @param username username of the new user
     * @param phoneNumber phone number of the new user
     * @param password encrypted password associated to the new user
     * @return
     */
    public boolean createUser(String firstName, String lastName, String username, String phoneNumber, String
            password){
        //params for user: String username, String type, String password, String lastName, String firstName, String phoneNumber

        System.out.println("STARTING PARSER");
        for (User user: userArrayList) {
            if (user.getUsername().equalsIgnoreCase(username))
                return false;
        }
        System.out.println("END OF FOR LOOP");
            User newPerson = new User(username, "user", password, lastName, firstName, phoneNumber, null);
            userArrayList.add(newPerson);
            this.updateUserList();

        return true;
    }
      /** Method to delete the user's account
    * @param username
    * 
    */
    public void deleteUser(String username) {  // might update
        getUsers();
        for (User user : userArrayList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                userArrayList.remove(user);
                break;
            }
        }
        updateUserList();
    
    }
    /** Method to change user's password 
    * @param username
    * @param newPassword
    */
    public void changePassword(String username, String newPassword) {
        for (User user : userArrayList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                user.setPassword(newPassword);
                break;
            }
        }
        updateUserList();
    }

    /**
     * This method allows the user to edit the details of a client's car,
     * it searches by username and plate number.
     *
     * @param username
     * @param plateNumber
     * @param newPlate
     * @param newModel
     * @param newType
     * @return
     */
    public boolean editVehicleInformation(String username, String plateNumber, String newPlate, String newModel, String newType) {
        for (User user : userArrayList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                List<Vehicle> vehicles = user.getVehicles();
                if (vehicles != null) {
                    for (Vehicle vehicle : vehicles) {
                        if (vehicle.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                            vehicle.setPlateNumber(newPlate);
                            vehicle.setModel(newModel);
                            vehicle.setType(newType);
                            updateUserList();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if a new vehicle has been successfully added into a specific user's vehicle list
     * @param username
     * @param type
     * @param model
     * @param plateNumber
     * @return
     */
    public boolean addVehicle(String username, String type, String model, String plateNumber){

        if (plateNumberExist(plateNumber)){
            return false;
        }

        for (User user : userArrayList){
            if (user.getUsername().equalsIgnoreCase(username)){
                Vehicle newVehicle = new Vehicle(type, model, plateNumber);
                user.getVehicles().add(newVehicle);
                updateUserList();
                return true;
            }
        }


        return false;
    }

    /**
     * Returns true if plate number already exists in the system
     * @param plateNumber
     * @return
     */
    public boolean plateNumberExist(String plateNumber){
        for (User user : userArrayList){
            if (user.getVehicles() == null){
                return false;
            }
            for (Vehicle vehicle : user.getVehicles()){
                if (vehicle.getPlateNumber().equalsIgnoreCase(plateNumber)){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * This method edits the information of a user with the given username.
     *
     * @param username
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @return
     */
    public boolean editUserInformation (String username, String firstName, String lastName, String phoneNumber){
        for (User user : userArrayList){
            if (user.getUsername().equalsIgnoreCase(username)){
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPhoneNumber(phoneNumber);
                updateUserList();
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        GsonUserParser parser = new GsonUserParser();
    }


}
