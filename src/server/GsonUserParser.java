package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shared.User;
import shared.Vehicle;

import java.io.*;
import java.util.*;

public class GsonUserParser {

    Gson gson;
    BufferedReader reader;

    ArrayList<User> userArrayList;

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

    public void updateUserList() {
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
     * @param username
     * @return
     */
    public Map<String, List<String>> getUserVehicles(String username) {
        Map<String, List<String>> vehicleList = new HashMap<>();
        for (User user : userArrayList) {
            if (user.getUsername().equals(username)) {
                if (user.getVehicles() != null) {
                    for (Vehicle vehicle : user.getVehicles()) {
                        ArrayList vehicleInfo = new ArrayList();
                        vehicleInfo.add(vehicle.getType());
                        vehicleInfo.add(vehicle.getModel());
                        vehicleList.put(vehicle.getPlateNumber(), vehicleInfo);
                    }
                    break;
                }
            }
        }
        return vehicleList;
    }

     public void deleteAccount(String username) {

    }

    public void changePassword(String username, String newPassword) {

    }

    public static void main(String[] args) {
        GsonUserParser parser = new GsonUserParser();
        User[] users = parser.getUsers();
        Map<String, List<String>> userVehicles = parser.getUserVehicles("yuen");
        for (String key : userVehicles.keySet()) {
            System.out.println(key + ": " + userVehicles.get(key));
        }
    }



}
