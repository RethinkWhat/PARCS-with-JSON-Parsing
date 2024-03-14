package server.model;

import java.util.HashMap;

public class Reservations {

    /** Holds the date of the reservations */
    private String date;

    /** StartTime,Endtime : Rickardo
     *    Map of start and finish of time to username
     */
    private HashMap<TimeRange, String> TimeAndUserMap;

    public Reservations(String date) {
        this.date = date;
        TimeAndUserMap = new HashMap<>();
    }

    public Reservations() {
        this.date = null;
        TimeAndUserMap = new HashMap<>();
    }

    public Reservations(String date, HashMap<TimeRange,String> timeRangeStringHashMap) {
        this.date = date;
        TimeAndUserMap = timeRangeStringHashMap;
    }

    /**
     * Getter for the date
     * @return DateTime
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter for the date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Method to get the start and end of a reservation and who reserved the spot
     * @return TimeAndUserMap
     */
    public HashMap<TimeRange, String> getTimeAndUserMap() {
        return TimeAndUserMap;
    }

}