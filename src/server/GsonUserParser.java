package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shared.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

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
     *
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
    public boolean createAccount(String firstName, String lastName, String username, String phoneNumber, String
            password){
        //params for user: String username, String type, String password, String lastName, String firstName, String phoneNumber
        User newPerson  = new User(username, "user",password,lastName, firstName,phoneNumber);
        userArrayList.add(newPerson);

        //userArrayList 入落 json
        this.updateUserList();
        return false;
    }

    public static void main(String[] args) {
        GsonUserParser parser = new GsonUserParser();
        User[] users = parser.getUsers();
        //System.out.print(users[0]);
        parser.createAccount("Yuen", "Ka Hang", "Perhaps", "09456123870","perhapas");
        //System.out.print(users[1].getUsername());
    }



}
