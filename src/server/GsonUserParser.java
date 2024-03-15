package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shared.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class GsonUserParser {

    Gson gson;
    BufferedReader reader;

    private final File file = new File("src/server/res/userList.json");

    public GsonUserParser() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public User[] getUsers() {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        User[] userList = gson.fromJson(reader, User[].class);
        return userList;
    }

    public static void main(String[] args) {
        GsonUserParser parser = new GsonUserParser();
        User[] users = parser.getUsers();
        System.out.print(users[0]);
    }



}
