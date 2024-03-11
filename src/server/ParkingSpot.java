package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingSpot {

    /**
     * The identifier and means to reference the parking spot
     */
    private String identifier;

    /** A list to hold the different dates of reservations */
    private List<Reservations> reservationsList;

    /**
     * Constructs a ParkingSpot object with a null identifier and an empty list of reservations.
     */
    public ParkingSpot(){
        this.identifier = null;
        this.reservationsList = new ArrayList<>();
    }

    public ParkingSpot(String identifier, List<Reservations> reservationsList) {
        this.identifier = identifier;
        this.reservationsList = reservationsList;
    }

    public ParkingSpot(String identifier) {
        this.identifier = identifier;
        this.reservationsList = new ArrayList<>();
    }

    /**
     * Method to get the identifier of a parking spot
     * @return String
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Method to set the identifier of a parking spot
     * @param identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Method to get the reservationsList
     * @return List<Reservations
     */
    public List<Reservations> getReservationsList() {
        return reservationsList;
    }

    /**
     * Method to set the reservations List
     * @param reservationsList : reservationsList
     */
    public void setReservationsList(List<Reservations> reservationsList) {
        this.reservationsList = reservationsList;
    }

    /**
     * Method to add reservation to reservationsList
     */
    public void addReservation(Reservations reservations) {
        reservationsList.add(reservations);
    }

    /**
     * It retrieves reservations for a specific date.
     * Searches through the list of reservations and returns a HashMap
     * containing time ranges mapped to corresponding user information
     * for the given date.
     *
     * @param date The date for which reservations are to be retrieved.
     * @return A HashMap containing time ranges mapped to corresponding user information,
     *         or null if no reservations are found for the specified date.
     */
    public HashMap<TimeRange, String> getReservationsOnDate(String date) {
        for (Reservations reservation : reservationsList) {
            if (reservation.getDate().equals(date)) {
                return reservation.getTimeAndUserMap();
            }
        }
        return null;
    }

    public void addReservationsList(List<Reservations> reservations) {
        reservationsList = reservations;
    }

    @Override
    public String toString() {
        return identifier + ": " + reservationsList;
    }
}
