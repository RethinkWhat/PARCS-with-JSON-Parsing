package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

/**
 * Parses the information of all reservations.
 */
public class GsonReservationParser {
    /**
     * TODO: Documentation
     */
    private GsonBuilder builder;
    /**
     * Instance of GSON.
     */
    private Gson gson;
    /**
     * List of the parking spots, both motorcycle and car.
     */
    private ArrayList<ParkingSpot> parkingSpots;
    /**
     * File path of the JSON file.
     */
    final File reservationsFile = new File("src/server/res/reservationList.json");

    /**
     * Default Constructor
     * Algorithm:
     *      1. Initializer builder
     *      2. Create gson object
     */
    public GsonReservationParser() {
        builder = new GsonBuilder();
        gson = builder.setPrettyPrinting().create();
        parkingSpots = new ArrayList<>();
        Collections.addAll(parkingSpots, getAllReservations());

    }

    /**
     * Method to get all the reservations found in the JSON file
     */
    private ParkingSpot[] getAllReservations() {
        ParkingSpot[] parkingSpots = new ParkingSpot[0];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(reservationsFile));

            parkingSpots = gson.fromJson(reader, ParkingSpot[].class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parkingSpots;
    }

    /**
     * TODO: Documentation
     */
    private synchronized void updateFile() {
        try {
            String jsonString = gson.toJson(parkingSpots);
            BufferedWriter writer = new BufferedWriter(new PrintWriter(reservationsFile));
            writer.write(jsonString);
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
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
    private void createJsonReservationObj(String identifier, String date, String startTime, String duration, String username) {

        boolean parkingBookingExists = false;
        boolean bookingOnDateExists = false;
        String endTime = computeEndTime(startTime, duration);
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.getIdentifier().equals(identifier)) {
                parkingBookingExists = true;

                for (Reservations reservationsList : parkingSpot.getReservationsList()) {
                    if (reservationsList.getDate().equals(date)) {
                        reservationsList.addToTimeAndUserMap(startTime,endTime, username);
                        bookingOnDateExists = true;
                        break;
                    }
                }
                if (!bookingOnDateExists) {
                    parkingSpot.addReservation(new Reservations(date, startTime, endTime, username));
                }
            }
        }
        if (!parkingBookingExists) {
            ParkingSpot parkingSpot = new ParkingSpot(identifier);
            parkingSpot.addReservation(new Reservations(date, startTime, endTime, username));
            parkingSpots.add(parkingSpot);
        }
    }

    /**
     * Method to handle making a reservation
     * @param identifier
     * @param date
     * @param startTime
     * @param duration
     * @param username
     * @return
     */
    public synchronized boolean makeReservation(String identifier, String date, String startTime, String duration, String username) {
        try {
            createJsonReservationObj(identifier, date, startTime, duration, username);
            updateFile();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * TODO: Documentation
     * @param identifier
     * @param date
     * @param startTime
     * @param duration
     * @return
     */
    public boolean hasSchedulingConflicts(String identifier, String date, String startTime, String duration) {
        TimeRange toCheck = new TimeRange(startTime,computeEndTime(startTime,duration));
        List<String> toCheckRange = toCheck.getStartToEndTime();
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.getIdentifier().equals(identifier)) {
                for (Reservations reservations : parkingSpot.getReservationsList()){
                    if (reservations.getDate().equals(date)) {
                        Map<TimeRange, String> timeRangeStringMap = reservations.getTimeAndUserMap();
                        for (TimeRange key : timeRangeStringMap.keySet()) {
                            List<String> unavailable = key.getStartToEndTime();
                            for (String bookingRange: toCheckRange) {
                                if (unavailable.contains(bookingRange)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method to get the list of time a specific spot is available for reservation
     * startTime, endTime follows the format 1:00
     * 24hr format
     * @param identifier
     * @param duration
     * @param date
     * @return
     */
    public List<String> spotTimeAvailable(String identifier, int duration, String date, String startTime, String endTime) {
        List<String> unavailableTimeRange = getUnavailableTimeRange(identifier,date);


        List<String> availableTimeRange = new ArrayList<>();
        int startTimeAsInt = Integer.parseInt(startTime.split(":")[0]);
        int endTimeAsInt = Integer.parseInt(endTime.split(":")[0]);

        availableTimeRange.add("Available Time: ");
        for (int i=startTimeAsInt; i <endTimeAsInt; i++) {
            if (!((i + duration) > endTimeAsInt)) {
                availableTimeRange.add(i + ":00");
            }
        }

        if (!unavailableTimeRange.isEmpty()) {
            ArrayList<String> toRemove = new ArrayList<>();
            for (String time : availableTimeRange) {
                for (int x = 0; x < duration; x++) {
                    int timeAsInt = Integer.valueOf(time.split(":")[0]);
                    String incremented = (timeAsInt + x) + ":00";
                    if (unavailableTimeRange.contains(incremented)
                            || unavailableTimeRange.contains("0" + incremented)) {
                        toRemove.add(timeAsInt + ":00");
                    }
                }
            }


            for (String remove : toRemove) {
                availableTimeRange.remove(remove);
                availableTimeRange.remove("0" + remove);
            }
        }
        return availableTimeRange;
    }

    /**
     * TODO: Documentation
     * @param identifier
     * @param date
     * @return
     */
    private List<String> getUnavailableTimeRange(String identifier, String date) {
        List<Reservations> booked = getSpotBookings(identifier);
        List<String> unavailableTimeRange = new ArrayList<>();
        if (booked != null) {
            for (Reservations reservation : booked) {
                if (reservation.getDate().equals(date)) {
                    Map<TimeRange, String> bookings = reservation.getTimeAndUserMap();
                    for (TimeRange timeRange : bookings.keySet()) {
                        List<String> timeRangeList = timeRange.getStartToEndTime();
                        for (String time : timeRangeList) {
                            unavailableTimeRange.add(time);
                        }
                    }
                    break;
                }
            }
        }
        return unavailableTimeRange;
    }

    /**
     * TODO: Documentation
     * @param identifier
     * @return
     */
    private List<Reservations> getSpotBookings(String identifier) {
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.getIdentifier().equals(identifier)) {
                return parkingSpot.getReservationsList();
            }
        }
        return null;
    }

    /**
     * TODO: Documentation
     * @param username
     * @param date
     * @return
     */
    public int getUserTotalBookings(String username, String date) {
        int count = 0;
        for (ParkingSpot parkingSpot : parkingSpots) {
            for (Reservations reservations : parkingSpot.getReservationsList()) {
                if (reservations.getDate().equals(date)) {
                    Map<TimeRange, String> timeUserMap = reservations.getTimeAndUserMap();
                    for (TimeRange key : timeUserMap.keySet()) {
                        if (timeUserMap.get(key).equals(username)) {
                            count +=1;
                        }
                    }
                }
            }
        }
        return count;
    }


    /**
     * Computes the end time based on the start time and duration provided.
     *
     * @param startTime The start time in HH:mm format.
     * @param duration The duration in hours.
     * @return The end time in HH:mm format.
     */
    public String computeEndTime(String startTime, String duration) {
        String[] startTimeParts = startTime.split(":");
        int endTime = Integer.parseInt(startTimeParts[0]) + Integer.parseInt(duration);
        return endTime+":00";
    }

    /**
     * Computes the duration between the start time and end time provided.
     *
     * @param startTime The start time in HH:mm format.
     * @param endTime The end time in HH:mm format.
     * @return The duration between the start and end time in hours.
     */
    public String computeDuration(String startTime, String endTime){
        List<Integer> duration = new ArrayList<>();

        String[] startTimeParts = startTime.split(":");
        String[] endTimeParts = endTime.split(":");

        int totalHours = Integer.parseInt(endTimeParts[0]) - Integer.parseInt(startTimeParts[0]);

        duration.add(totalHours);
        duration.add(00);

        return String.valueOf(totalHours);
    }

    /**
     * Returns a Map of Reservations by a given user
     * KEY: Parking identifier
     * VALUE: Reservations Object
     *
     * @author Yung ginatekeep ni Jullainne Candy Lou
     */
    public Map<String, Reservations> getUserReservations(String username){
        Map<String, Reservations> userReservationMap = new HashMap<>();

        for (ParkingSpot currParkingSpot : parkingSpots){
            String identifier = currParkingSpot.getIdentifier();

            //Creates a new Reservations object for each parking spot
            Reservations reservation = new Reservations();

            //Loops through the reservations of the Parking Spot
            for (Reservations currReservation : currParkingSpot.getReservationsList()){

                //Loops through the time and user map of the current reservation
                for (Map.Entry<TimeRange, String> entry : currReservation.getTimeAndUserMap().entrySet()){

                    //Checks if the username of a certain TimeRange is equals to the username being passed
                    if (entry.getValue().equalsIgnoreCase(username)){

                        //Adds the time range (if valid) to the new Reservation object's time and user map
                        reservation.addToTimeAndUserMap(entry.getKey().startTime(), entry.getKey().endTime(), username);
                        reservation.setDate(currReservation.getDate());

                    }
                }
            }

            //Adds the Parking Identifier with its corresponding Reservations object to the map
            //If the created reservation object is empty, continue.
            if (!reservation.getTimeAndUserMap().isEmpty()){
                userReservationMap.put(identifier, reservation);
            }
        }
        return userReservationMap;
    }

    public List<List<String>> getAllCarBookings() {
        List<List<String>> carBookings = new ArrayList<>();

        for (ParkingSpot parkingSpot : parkingSpots) {
            for (Reservations reservations : parkingSpot.getReservationsList()) {
                if (parkingSpot.getIdentifier().contains("C")) {
                    String date = reservations.getDate();
                    String identifier = parkingSpot.getIdentifier();
                    String username = null;
                    String timeIn = null;
                    String timeOut = null;
                    String duration = null;

                    for (Map.Entry<TimeRange, String > entry : reservations.getTimeAndUserMap().entrySet()) {
                        username = entry.getValue();
                        timeIn = entry.getKey().startTime();
                        timeOut = entry.getKey().endTime();
                        duration = computeDuration(timeIn, timeOut);
                    }

                    List<String> currentReservation = new ArrayList<>();
                    currentReservation.add(username);
                    currentReservation.add(identifier);
                    currentReservation.add(date);
                    currentReservation.add(timeIn);
                    currentReservation.add(timeOut);
                    currentReservation.add(duration);

                    carBookings.add(currentReservation);
                }
            }
        }
        return carBookings;
    }

    /**
     * Retrieves all the current motorcycle booking reservations.
     * FORMAT: [username, parking identifier, date, start time, end time, duration]
     * @return
     */
    public List<List<String>> getAllMotorBookings() {
        List<List<String>> motorBookings = new ArrayList<>();

        for (ParkingSpot parkingSpot : parkingSpots) {
            for (Reservations reservations : parkingSpot.getReservationsList()) {
                if (parkingSpot.getIdentifier().contains("M")) {
                    String date = reservations.getDate();
                    String identifier = parkingSpot.getIdentifier();
                    String username = null;
                    String timeIn = null;
                    String timeOut = null;
                    String duration = null;

                    for (Map.Entry<TimeRange, String > entry : reservations.getTimeAndUserMap().entrySet()) {
                        username = entry.getValue();
                        timeIn = entry.getKey().startTime();
                        timeOut = entry.getKey().endTime();
                        duration = computeDuration(timeIn, timeOut);
                    }

                    List<String> currentReservation = new ArrayList<>();
                    currentReservation.add(username);
                    currentReservation.add(identifier);
                    currentReservation.add(date);
                    currentReservation.add(timeIn);
                    currentReservation.add(timeOut);
                    currentReservation.add(duration);

                    motorBookings.add(currentReservation);
                }
            }
        }
        return motorBookings;
    }


    //TODO: Delete
    public static void main(String[] args) {
        GsonReservationParser parser = new GsonReservationParser();
        //parser.makeReservation("C69", "10/10/10", "7:00", "1", "rithik");
        //String identifier, int duration, String date, String startTime, String endTime) {
        //System.out.println(parser.spotTimeAvailable("C1", 2, "03/15/24", "7:00", "16:00"));

//        System.out.println(parser.getUserReservations("monem"));
    }
}
