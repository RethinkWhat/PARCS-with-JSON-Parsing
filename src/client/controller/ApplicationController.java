package client.controller;

import client.controller.application_pages.ReservationPageController;
import client.controller.application_pages.TimerController;
import client.controller.application_pages.UserProfileController;
import client.model.ApplicationModel;
import client.view.ApplicationView;

/**
 * Template for ApplicationController object.
 * The ApplicationController processes the user requests. Based on the user request, the ApplicationController
 * defines methods and invokes methods in the View and Model to accomplish the requested action.
 */
public class ApplicationController {
    /**
     * The view ApplicationView object.
     */
    private ApplicationView view;
    /**
     * The model ApplicationModel model.
     */
    private ApplicationModel model;

    /**
     * Constructs an ApplicationController with a specified view and model.
     * @param view The specified ApplicationView.
     * @param model The specified ApplicationModel.
     */
    public ApplicationController(ApplicationView view, ApplicationModel model) {
        this.view = view;
        this.model = model;
       // view.setDefaultCloseOperation(model.getClient().logoutAndExit());

        // constants / variables
        view.getLblLocation().setText("Home");
        new ReservationPageController(view.getReservationPageView(), model.getReservationPageModel());
        /**
         * TODO: Implement ff code. Exceptions occur that's why they are commented out
         */
        new UserProfileController(view.getUserProfileView(), model.getUserProfileModel(), this.view);
        TimerController controller = new TimerController(view.getTimerView(), model.getTimerModel());
        controller.startTimer();

        // action listeners
        view.setNavHomeListener(e -> {
            view.getLblLocation().setText("Home");
            view.getMainCardLayout().show(view.getPnlCards(), "home");
        });
        view.setNavTicketListener(e -> {
            view.getLblLocation().setText("Ticket");
            view.getMainCardLayout().show(view.getPnlCards(), "ticket");
            controller.startTimer();
        });
        view.setNavAccountListener(e -> {
            view.getLblLocation().setText("Account");
            view.getMainCardLayout().show(view.getPnlCards(), "account");
        });
        view.setNavLogoutListener(e -> {
            view.dispose();
            //TODO: update
            model.getClient().logout(model.getClient().getUsername());
        });


        // mouse listeners

        // focus listeners
    }
}
