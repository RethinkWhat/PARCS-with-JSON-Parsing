package client.model.application_pages;

import client.model.Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Template for ReservationPageModel object.
 * The ReservationPageModel contains the different models presented in the reservation page.
 */
public class ReservationPageModel {
    /**
     * The connection of the client to the server.
     */
    private Client client;
    /**
     * The formatter for the current date and time.
     */
  //  private  LiveDateTime liveDateTime = new LiveDateTime();
    /**
     * The number of currently available car parking slots
     */
    private String availCarSlots;
    /**
     * The number of currently available motorcycle parking slots.
     */
    private String availMotorSlots;
    /**
     * The number of current bookings the user has.
     */

    private String totalBookings;
    /**
     * The cars of the user.
     */
    private String[] cars;
    /**
     * The motorcycles of the user.
     */
    private String[] motorcycles;

    private String[] reservationTime = {"Select Time:", "6:00", "7:00", "8:00", "9:00",
            "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
    /**
     * The full name of the user.
     */
    private String fullName;
    /**
     * Mapping of vehicles associated with the user.
     */
    private Map<String, List<String>> vehicles;


    /**
     * Constructs a ReservationPageModel with a specified client.
     * @param client The specified client.
     */
    public ReservationPageModel(Client client) {
        //TODO: RMI Implementation
    }


    /**
     * Retrieves the full name of the user to be displayed in the ReservationPage.
     * @return The full name of the user.
     */
    public String getFullName() {

        return fullName;
    }

    /**
     * Retrieves the array of reservation time slots.
     *
     * @return The array of reservation time slots.
     */
    public String[] getReservationTime() {
        return reservationTime;
    }

    /**
     * Retrieves the available time slots for a given parking spot, duration, and date.
     *
     * @param parkingIdentifier The identifier of the parking spot.
     * @param duration          The duration of the reservation.
     * @param date              The date of the reservation.
     * @return The array of available time slots.
     */
    public String[] getAvailableTime(String parkingIdentifier, String duration, String date) {
        //TODO: RMI Implementation
        return new String[]{};
    }

    /**
     * Attempts to book a parking spot for the user on a specified date and time.
     *
     * @param identifier The identifier of the parking spot.
     * @param date       The date of the reservation.
     * @param startTime  The starting time of the reservation.
     * @param duration   The duration of the reservation.
     * @return True if the booking is confirmed, false otherwise.
     */
    public boolean attemptBooking(String identifier, String date, String startTime, String duration) {
        //TODO: RMI Implementation
        return false;
    }

    /**
     * Finds an available parking slot on a specific date for a given number of car and motorcycle slots.
     *
     * @param date     The date for which to find an available slot.
     * @param carSize  The number of car slots.
     * @param motorSize The number of motorcycle slots.
     * @return The identifier of the available parking slot, or null if none is available.
     */
    public String findAvailableSlotOnDay(String date, int carSize, int motorSize) {
        String dates[] = getDateList();
        if (Arrays.stream(dates).toList().contains(date)) {

            for (int x =0; x< carSize; x++) {
                String time[] = getAvailableTime(("C" + (x+1)),"1",date);

                if (time.length > 1)
                    return ("C"+(x+1));
            }
            for (int x =0; x< motorSize; x++) {
                String time[] = getAvailableTime(("M" + (x+1)),"1",date);
                if (time.length >1)
                    return ("M"+(x+1));
            }
        }
        return null;
    }

    /**
     * Retrieves the number of available car parking slots.
     *
     * @return The number of available car parking slots.
     */
    public String getAvailCarSlots() {
        return availCarSlots;
    }

    /**
     * Retrieves the number of available motorcycle parking slots.
     *
     * @return The number of available motorcycle parking slots.
     */
    public String getAvailMotorSlots() {
        return availMotorSlots;
    }

    /**
     * Retrieves the total number of bookings the user has.
     *
     * @return The total number of bookings.
     */
    public String getTotalBookings() {
        return totalBookings;
    }

    /**
     * Retrieves the array of cars owned by the user.
     *
     * @return The array of cars.
     */
    public String[] getCars() {

        return cars;
    }

    /**
     * Retrieves the array of motorcycles owned by the user.
     *
     * @return The array of motorcycles.
     */
    public String[] getMotorcycles() {
        //get output from server
        return motorcycles;
    }

    /**
     * Retrieves the current date.
     *
     * @return The current date.
     */
    public String getDate() {
        //TODO: RMI Implementation
        return "09/10/23";
    }

    /**
     * Retrieves an array of dates including today, tomorrow, and the day after tomorrow.
     *
     * @return The array of dates.
     */
    public String[] getDateList() {
        String[] dates = new String[4];

        LocalDate dateToday = LocalDate.now();
        LocalDate tomorrow = dateToday.plusDays(1);
        LocalDate dayAfter = tomorrow.plusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        dates[0] = "Date:";
        dates[1] = dateToday.format(formatter);
        dates[2] = tomorrow.format(formatter);
        dates[3] = dayAfter.format(formatter);
        return dates;
    }

}
