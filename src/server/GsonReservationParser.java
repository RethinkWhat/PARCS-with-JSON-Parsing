package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;

public class GsonReservationParser {

    GsonBuilder builder;
    Gson gson;

    final File reservationsFile = new File("res/reservationList");

    /**
     * Default Constructor
     * Algorithm:
     *      1. Initializer builder
     *      2. Create gson object
     */
    public GsonReservationParser() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
    }

    /**
     * Method to create a JSON reservation object
     * @param identifier
     * @param date
     * @param startTime
     * @param duration
     * @param username
     * @return
     */
    public String createJsonReservationObj(String identifier, String date, String startTime, String duration, String username) {
        String[] reservation = {identifier, date, startTime, duration, username};
        return gson.toJson(reservation);
    }

    /**
     * Method to write a JSON object to the reservations file
     * @param object
     */
    public void writeJsonObject(String object) {
        try {
            FileWriter writer = new FileWriter(reservationsFile);
            writer.write(object);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get all the reservations found in the JSON file
     * @return reservations array
     */
    public String[] getAllReservations() {
        String[] reservation = new String[0];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(reservationsFile));

            reservation = gson.fromJson(reader, String[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    //TODO: Delete
    public static void main(String[] args) {
        GsonReservationParser parser = new GsonReservationParser();
        String obj = parser.createJsonReservationObj("C1", "3/3/24", "9:00", "1", "rithik");
        //parser.writeJsonObject(obj);
        System.out.println(parser.getAllReservations()[0]);
    }
}
