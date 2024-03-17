package server.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateTime Object
 * Will be used as the object to store when storing date and time reservations.xml
 */
public class DateTime {
    /**
     * Format for time following the format: "hh:mm:ss"
     */
    private SimpleDateFormat timeFormat;


    /**
     * Format for date following the format: "mm/dd/yy"
     */
    private SimpleDateFormat dateFormat;


    /**
     * Default constructor for dateTime
     */
    public DateTime() {
        timeFormat = new SimpleDateFormat("hh:mm");
        dateFormat = new SimpleDateFormat("MM/dd/yy");
    }

    /**
     * Get the formatted date time
     * @return date : String
     */
    public String getDateTime() {
        Date currentTime = Calendar.getInstance().getTime();
        return dateFormat.format(currentTime);
    }

    /**
     * Method Get the formatted date
     * @return time : String
     */
    public String getTime() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat tempFormat = new SimpleDateFormat("HH:mm");
        return tempFormat.format(currentTime);
    }


    /**
     * Method to get the endTime given a start time and duration
     * @param time
     * @param duration
     * @return computed time
     */
    public String addDuration(String time, int duration) {
        // Assuming time is in "HH:mm" format
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        // Adjust hours and minutes
        hours +=duration;
        // Format the result
        return String.format("%02d:%02d", hours, minutes);
    }
}
