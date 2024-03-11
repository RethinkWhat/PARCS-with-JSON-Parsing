package client.model;

import client.model.application_pages.ReservationPageModel;
import client.model.application_pages.TimerModel;
import client.model.application_pages.UserProfileModel;

/**
 * Template for object ApplicationModel.
 * The application model contains the different data to be displayed in the client application domain.
 */
public class ApplicationModel {
    /**
     * The connection of the client to the server.
     */
    private Client client;
    /**
     * The model for the Reservation Page.
     */
    private ReservationPageModel reservationPageModel;
    /**
     * The model for the UserProfile page.
     */
    private UserProfileModel userProfileModel;
    /**
     * The model for the TImer page.
     */
    private TimerModel timerModel;

    /**
     * The model for the application that creates models for the reservation, ticket, and user profile pages.
     * The model is constructed with a specified client.
     * @param client The specified client.
     */
    public ApplicationModel(Client client) {
        this.client = client;
        reservationPageModel = new ReservationPageModel(this.client);
        userProfileModel = new UserProfileModel(this.client);
        timerModel = new TimerModel(this.client);
    }

    /**
     * Retrieves the current model of the reservation page.
     * @return The current model of the reservation page.
     */
    public ReservationPageModel getReservationPageModel() {
        return reservationPageModel;
    }

    /**
     * Retrieves the current model of the user profile page.
     * @return The current model of the user profile page.
     */
    public UserProfileModel getUserProfileModel() {
        return userProfileModel;
    }

    /**
     * Retrieves the current model of the ticket page.
     * @return The current ticket of the ticket page.
     */
    public TimerModel getTimerModel() {
        return timerModel;
    }

    /**
     * Retrieves the current client.
     * @return The current client.
     */
    public Client getClient() {
        return client;
    }
}
