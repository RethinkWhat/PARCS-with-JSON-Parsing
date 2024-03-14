package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;

public class GsonReservationParser {

    GsonBuilder builder;
    Gson gson;

    final File reservationsFile = new File("src/server/res/reservationList.json");

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
    public void createJsonReservationObj(String identifier, String date, String startTime, String duration, String username) {
        //TODO: Make method
    }

    /**
     * Method to write a JSON object to the reservations file
     * @param object
     */
    public void writeJsonObject(String object) {
        //TODO: Make method
    }

    /**
     * Method to get all the reservations found in the JSON file
     * @return reservations array
     */
    public ParkingSpot getAllReservations() {
        ParkingSpot parkingSpot = new ParkingSpot();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(reservationsFile));

            parkingSpot = gson.fromJson(reader, ParkingSpot.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parkingSpot;
    }

    //TODO: Delete
    public static void main(String[] args) {
        GsonReservationParser parser = new GsonReservationParser();
        System.out.println(parser.getAllReservations());
    }
}
