package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.*;

public class GsonReservationParser {

    GsonBuilder builder;
    Gson gson;

    ArrayList<ParkingSpot> parkingSpots;

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

        for (int i=startTimeAsInt; i <endTimeAsInt; i++) {
            availableTimeRange.add(i +":00");
        }

        ArrayList<String> toRemove = new ArrayList<>();
        for (String time : availableTimeRange) {
            for (int x =0; x <duration; x ++) {
                int timeAsInt = Integer.valueOf(time.split(":")[0]);
                String incremented = (timeAsInt+x) + ":00";
                if (unavailableTimeRange.contains(incremented)
                        || unavailableTimeRange.contains("0" + incremented)
                        || (timeAsInt+x) > endTimeAsInt-1) {
                    toRemove.add(timeAsInt + ":00");
                }
            }
        }

        for (String remove : toRemove) {
            availableTimeRange.remove(remove);
            availableTimeRange.remove("0" + remove);
        }
        return availableTimeRange;
    }

    private List<String> getUnavailableTimeRange(String identifier, String date) {
        List<Reservations> booked = getSpotBookings(identifier);
        List<String> unavailableTimeRange = new ArrayList<>();
        for (Reservations reservation : booked) {
            if (reservation.getDate().equals(date)) {
                Map<TimeRange, String> bookings = reservation.getTimeAndUserMap();
                for (TimeRange timeRange : bookings.keySet()) {
                    List<String> timeRangeList = timeRange.getStartToEndTime();
                    for (String time: timeRangeList) {
                        unavailableTimeRange.add(time);
                    }
                }
                break;
            }
        }
        return unavailableTimeRange;
    }


    private List<Reservations> getSpotBookings(String identifier) {
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.getIdentifier().equals(identifier)) {
                return parkingSpot.getReservationsList();
            }
        }
        return null;
    }

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


    //TODO: Delete
    public static void main(String[] args) {
        GsonReservationParser parser = new GsonReservationParser();
        //parser.makeReservation("C69", "10/10/10", "7:00", "1", "rithik");
        //String identifier, int duration, String date, String startTime, String endTime) {
        System.out.println(parser.spotTimeAvailable("C1", 2, "03/15/24", "7:00", "16:00"));
    }
}
