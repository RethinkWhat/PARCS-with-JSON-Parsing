package client.controller;

import client.controller.application_pages.ReservationPageController;
import client.controller.application_pages.TimerController;
import client.controller.application_pages.UserProfileController;
import client.model.ApplicationModel;
import client.model.Client;
import client.model.LoginModel;
import client.view.ApplicationView;
import client.view.LoginView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

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


        new UserProfileController(view.getUserProfileView(), model.getUserProfileModel(), this.view);
        TimerController controller = new TimerController(view.getTimerView(), model.getTimerModel());

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


        //when window is closed...
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //do logout
                model.getClient().logout(model.getClient().getUsername());
                relogin();

            }
        });
    }

    /**
     * A method that redirects users to Login View whenever they clicked logout button or Exit(X) button
     */
    public void relogin(){
        try {
            Client client = new Client();
            LoginModel model = new LoginModel(client);
            LoginView view = new LoginView();
            new LoginController(view, model);
        } catch (RemoteException | NotBoundException ee) {
            ee.printStackTrace();
        }
    }
}
