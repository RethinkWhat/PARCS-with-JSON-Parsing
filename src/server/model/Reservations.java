package server.model;

import java.util.HashMap;

public class Reservations {

    /** Holds the date of the reservations */
    private String date;

    /** StartTime-Endtime : Rickardo
     *    Map of start and finish of time to username
     */
    private HashMap<String, String> timeAndUserMap;

    public Reservations(String date) {
        this.date = date;
        timeAndUserMap = new HashMap<>();
    }

    public Reservations() {
        this.date = null;
        timeAndUserMap = new HashMap<>();
    }

    public Reservations(String date, HashMap<String,String> timeRangeStringHashMap) {
        this.date = date;
        timeAndUserMap = timeRangeStringHashMap;
    }

    public Reservations(String date, String startTime, String endTime,  String user) {
        this.date = date;
        timeAndUserMap = new HashMap<>();
        timeAndUserMap.put(startTime+"-"+endTime,user);
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
        HashMap<TimeRange,String> timeRangeUserMap = new HashMap<>();
        for (String time: timeAndUserMap.keySet()) {
            String[] timeRange =  time.split("-");
            timeRangeUserMap.put(new TimeRange(timeRange[0], timeRange[1]), timeAndUserMap.get(time));
        }
        return timeRangeUserMap;
    }

    public void addToTimeAndUserMap(String startTime, String endTime, String user) {
        timeAndUserMap.put(startTime+ "-" + endTime, user);
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder(date + ": ");
        for (String key :  timeAndUserMap.keySet()) {
            toReturn.append(key).append(" ");
        }
        return toReturn.toString() ;
    }
}
