package client.model.application_pages;

import client.model.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model for the Timer functionality, including parking reservation details and timing.
 * This class provides methods for retrieving reservation information, managing timer settings,
 * and accessing details such as parking type, slot, date, duration, check-in, and check-out times.
 */
public class TimerModel {
    /**
     * The connection of the client to the server.
     */
    private Client client;
    /**
     * The location of the parking area.
     */
    private String parkingArea = "SLU Maryheights Campus";
    /**
     * The type of parking (motorcycle or car).
     */
    private String parkingType;
    /**
     * The specified parked vehicle of the user.
     */
    private String vehicle;
    /**
     * The chosen parking slot of the user.
     */
    private String parkingSlot;
    /**
     * The date of parking.
     */
    private String date;
    /**
     * The duration of parking in hours.
     */
    private int duration;
    /**
     * The check-in time of the user in the parking slot.
     */
    private String timeIn;
    /**
     * The check-out time of the user in the parking slot.
     */
    private String timeOut;

    private List<String> reservationInfo;

    private boolean startTimer;

    private boolean isTimeStarted = false;

    /**
     * Constructs a model of timer with a specified client.
     * @param client The specified client.
     */
    public TimerModel(Client client) {
        this.client = client;
        getReservationInfo();
    }

    /**
     * Retrieves the reservation information for the user's parking session.
     */
    public void getReservationInfo() {
        //TODO: RMI Implementation
    }

    /**
     * Retrieves the type of parking (motorcycle or car) associated with the reservation.
     *
     * @return The type of parking.
     */
    public String getParkingType() {
        return parkingType;
    }

    /**
     * Sets the type of parking for the reservation.
     *
     * @param parkingType The type of parking to be set.
     */
    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    /**
     * Retrieves the chosen parking slot for the reservation.
     *
     * @return The chosen parking slot.
     */
    public String getParkingSlot() {
        return parkingSlot;
    }

    /**
     * Sets the chosen parking slot for the reservation.
     *
     * @param parkingSlot The parking slot to be set.
     */
    public void setParkingSlot(String parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    /**
     * Retrieves the date of the parking reservation.
     *
     * @return The date of the reservation.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the parking reservation.
     *
     * @param date The date to be set.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Retrieves the duration of the parking reservation in hours.
     *
     * @return The duration of the reservation.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the parking reservation in hours.
     *
     * @param duration The duration to be set.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Retrieves the check-in time of the user in the parking slot.
     *
     * @return The check-in time of the reservation.
     */
    public String getTimeIn() {
        return timeIn;
    }

    /**
     * Sets the check-in time of the user in the parking slot.
     *
     * @param timeIn The check-in time to be set.
     */
    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    /**
     * Retrieves the check-out time of the user in the parking slot.
     *
     * @return The check-out time of the reservation.
     */
    public String getTimeOut() {
        return timeOut;
    }

    /**
     * Sets the check-out time of the user in the parking slot.
     *
     * @param timeOut The check-out time to be set.
     */
    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * Checks if the timer for the reservation should be started.
     *
     * @return True if the timer should be started, false otherwise.
     */
    public boolean isStartTimer() {
        return startTimer;
    }

    /**
     * Sets whether the timer for the reservation should be started.
     *
     * @param startTimer True if the timer should be started, false otherwise.
     */
    public void setStartTimer(boolean startTimer) {
        this.startTimer = startTimer;
    }

    /**
     * Checks if the timer for the reservation has been started.
     *
     * @return True if the timer has been started, false otherwise.
     */
    public boolean isTimeStarted() {
        return isTimeStarted;
    }

    /**
     * Sets whether the timer for the reservation has been started.
     *
     * @param timeStarted True if the timer has been started, false otherwise.
     */
    public void setTimeStarted(boolean timeStarted) {
        isTimeStarted = timeStarted;
    }

    /**
     * Retrieves the client associated with the TimerModel.
     *
     * @return The client.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the client associated with the TimerModel.
     *
     * @param client The client to be set.
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
