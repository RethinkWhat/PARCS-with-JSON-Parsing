package client.controller.application_pages;

import client.controller.VehicleAdderController;
import client.model.RegisterModel;
import client.model.VehicleAdderModel;
import client.model.application_pages.UserProfileModel;
import client.view.ApplicationView;
import client.view.VehicleAdderView;
import client.view.application_pages.UserProfileView;
import shared.Vehicle;
import utilities.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The UserProfileController processes the user requests for editing user account information, vehicle information,
 * editing user's password, viewing the booking history of the history, and logging out.
 * Based on the user request, the UserProfileController defines methods and invokes methods in the View and Model
 * to accomplish the requested action.
 */
public class UserProfileController {

    /**
     * The view UserRegisterView object.
     */
    private UserProfileView view;
    /**
     * The model UserProfile object.
     */
    private UserProfileModel model;
    /**
     * The array for Cars Panel holding the user's cars.
     */
    private UserProfileView.EditCars.CarsPanel[] pnlsCars;
    /**
     * Initial index of the user's car in navigation.
     */
    private int carIndex = 0;
    ;
    /**
     * Max value of the page.
     */
    private int max;
    /**
     * List of cars.
     */
    private ArrayList<String> plateNumbers;

    /**
     * Constructs a controller of the UserProfile page with a specified view and model.
     *
     * @param view  The specified view.
     * @param model The specified model.
     */
    public UserProfileController(UserProfileView view, UserProfileModel model, ApplicationView parent) {
        this.view = view;
        this.model = model;

        // constants / variables
        populateFields(); // populate fields of the edit profile text fields.

        pnlsCars = new UserProfileView.EditCars.CarsPanel[model.getVehicleList().size()];

        max = model.getVehicleList().size();
        plateNumbers = new ArrayList<>();

        // populate cars panel inside edit cars page.
        for (int i = 0; i < max; i++) {
            Vehicle token = model.getVehicleList().get(i);
            plateNumbers.add(token.getPlateNumber());


            UserProfileView.EditCars.CarsPanel carPanel = new UserProfileView.EditCars.CarsPanel(token.getPlateNumber(), token.getType(), token.getModel());
            pnlsCars[i] = carPanel;

            view.getPnlEditCars().getPnlCards().add(carPanel, String.valueOf(i));
            view.getPnlEditCars().getCardLayout().show(view.getPnlEditCars().getPnlCards(), String.valueOf(i));
        }

        // action listeners

        // navigation
        view.setNavEditProfileListener(e -> view.getCardLayout().show(view.getPnlCards(), "profile"));
        view.setNavEditCarsListener(e -> view.getCardLayout().show(view.getPnlCards(), "vehicles"));
        view.setNavHistoryListener(e -> {
            view.getCardLayout().show(view.getPnlCards(), "history");
            model.viewHistory();
        });
        view.setNavSecurityListener(e -> view.getCardLayout().show(view.getPnlCards(), "security"));
        view.setDeleteListener(new DeleteListener(view));
        view.setNavExitListener(new LogOutListener());

        // edit profile page
        view.getPnlEditProfile().setContinueListener(new ProfileEditListener());
        view.getPnlEditProfile().setCancelListener(new ProfileCancelListener());

        // edit cars page
        view.getPnlEditCars().setContinueListener(new CarContinueListener());
        view.getPnlEditCars().setCancelListener(new CarCancelListener());
        view.getPnlEditCars().setNextListener(new NextListener());
        view.getPnlEditCars().setPrevListener(new PreviousListener());

        for (UserProfileView.EditCars.CarsPanel panel : pnlsCars) {
            panel.setEditListener(e -> {
                panel.getTxtPlateNumber().setEditable(true);
                panel.getTxtPlateNumber().setFocusable(true);
                panel.getTxtModel().setEditable(true);
                panel.getTxtModel().setFocusable(true);
                view.getPnlEditCars().getBtnContinue().setVisible(true);
                view.getPnlEditCars().getBtnCancel().setVisible(true);
            });
        }

        // history page
        view.getPnlBookingsView().bookingPanel().getFilterPanel().setApplyListener(new ApplyFiltersListener());
        view.getPnlBookingsView().bookingPanel().getFilterPanel().setClearListener(e -> {
            view.getPnlBookingsView().bookingPanel().getTablePanel().getTableModel().setRowCount(0);
            view.getPnlBookingsView().bookingPanel().getFilterPanel().getTxtDate().setText("Search Date (MM/DD/YY or All)");
            view.getPnlBookingsView().bookingPanel().getFilterPanel().getCmbStatus().setSelectedIndex(0);
            view.getPnlBookingsView().bookingPanel().getFilterPanel().getCmbVehicleType().setSelectedIndex(0);
        });

        // security page
        view.getPnlSecurityPage().setConfirmListener(new SecurityConfirmListener());

        // mouse listeners

        // navigation
        view.getBtnNavEditProfile().addMouseListener(new Resources.CursorChanger(view.getBtnNavEditProfile()));
        view.getBtnNavEditCars().addMouseListener(new Resources.CursorChanger(view.getBtnNavEditCars()));
        view.getBtnNavHistory().addMouseListener(new Resources.CursorChanger(view.getBtnNavHistory()));
        view.getBtnNavSecurity().addMouseListener(new Resources.CursorChanger(view.getBtnNavSecurity()));
        view.getBtnNavExit().addMouseListener(new Resources.CursorChanger(view.getBtnNavExit()));
        view.getBtnNavDelete().addMouseListener(new Resources.CursorChanger(view.getBtnNavDelete()));

        // edit profile page
        view.getPnlEditProfile().getBtnContinue().
                addMouseListener(new Resources.CursorChanger(view.getPnlEditProfile().getBtnContinue()));
        view.getPnlEditProfile().getBtnCancel().
                addMouseListener(new Resources.CursorChanger(view.getPnlEditProfile().getBtnCancel()));

        // edit cars page
        view.getPnlEditCars().getBtnPrev().addMouseListener(new Resources.CursorChanger(view.getPnlEditCars().getBtnPrev()));
        view.getPnlEditCars().getBtnNext().addMouseListener(new Resources.CursorChanger(view.getPnlEditCars().getBtnNext()));
        view.getPnlEditCars().getBtnContinue().addMouseListener(new Resources.CursorChanger(view.getPnlEditCars().getBtnContinue()));
        view.getPnlEditCars().getBtnCancel().addMouseListener(new Resources.CursorChanger(view.getPnlEditCars().getBtnCancel()));
        view.getPnlEditCars().getBtnAddVehicle().addMouseListener(new Resources.CursorChanger(view.getPnlEditCars().getBtnAddVehicle()));
        view.getPnlEditCars().setAddVehicleListener(new AddVehicleListener());

        for (UserProfileView.EditCars.CarsPanel panel : pnlsCars) {
            panel.getBtnEdit().addMouseListener(new Resources.CursorChanger(panel.getBtnEdit()));
        }

        // history page
        view.getPnlBookingsView().bookingPanel().getFilterPanel().getBtnApply().addMouseListener(new Resources.CursorChanger(
                view.getPnlBookingsView().bookingPanel().getFilterPanel().getBtnApply()));
        view.getPnlBookingsView().bookingPanel().getFilterPanel().getBtnClear().addMouseListener(new Resources.CursorChanger(
                view.getPnlBookingsView().bookingPanel().getFilterPanel().getBtnClear()));

        // security page
        view.getPnlSecurityPage().getBtnConfirm().addMouseListener(
                new Resources.CursorChanger(view.getPnlSecurityPage().getBtnConfirm()));

        // focus listeners

        // edit profile page
        view.getPnlEditProfile().getTxtLastName().addFocusListener(
                new Resources.TextFieldFocus(view.getPnlEditProfile().getTxtLastName(), model.getLastName()));
        view.getPnlEditProfile().getTxtFirstName().addFocusListener(
                new Resources.TextFieldFocus(view.getPnlEditProfile().getTxtFirstName(), model.getFirstName()));
        view.getPnlEditProfile().getTxtLastName().addFocusListener(
                new Resources.TextFieldFocus(view.getPnlEditProfile().getTxtLastName(), model.getLastName()));
        view.getPnlEditProfile().getTxtContact().addFocusListener(
                new Resources.TextFieldFocus(view.getPnlEditProfile().getTxtContact(), model.getContactNo()));

        // edit cars page
        for (UserProfileView.EditCars.CarsPanel panel : pnlsCars) {
            String initialPlateNumber = panel.getTxtPlateNumber().getText();
            String initialModel = panel.getTxtPlateNumber().getText();

            panel.getTxtPlateNumber().addFocusListener(new Resources.TextFieldFocus(
                    panel.getTxtPlateNumber(), initialPlateNumber));
            panel.getTxtModel().addFocusListener(new Resources.TextFieldFocus(
                    panel.getTxtModel(), initialModel));
        }

        // security page
        view.getPnlSecurityPage().getTxtCurrentPassword().
                addFocusListener(new Resources.PasswordFocus(
                        view.getPnlSecurityPage().getTxtCurrentPassword(), "Current Password"
                ));
        view.getPnlSecurityPage().getTxtNewPassword().
                addFocusListener(new Resources.PasswordFocus(
                        view.getPnlSecurityPage().getTxtNewPassword(), "New Password"
                ));
        view.getPnlSecurityPage().getTxtConfirmNewPassword().
                addFocusListener(new Resources.PasswordFocus(
                        view.getPnlSecurityPage().getTxtConfirmNewPassword(), "Confirm New Password"
                ));

        // history page
        view.getPnlBookingsView().bookingPanel().getFilterPanel().getTxtDate().addFocusListener(new Resources.TextFieldFocus(
                view.getPnlBookingsView().bookingPanel().getFilterPanel().getTxtDate(), "Search Date (MM/DD/YY) or All"));

        view.revalidate();
        view.repaint();
    }

    /**
     * Populates the respective fields of the user.
     */
    public void populateFields() {
        // constants / variable
        model.getCredentials();

        // Name
        view.getPnlEditProfile().getTxtFirstName().setText(model.getFirstName());
        view.getPnlEditProfile().getTxtLastName().setText(model.getLastName());

        // Username
        view.getPnlEditProfile().getTxtUsername().setText(model.getUsername());
        view.getPnlEditProfile().getTxtUsername().setEditable(false);

        // Contact Number
        view.getPnlEditProfile().getTxtContact().setText(model.getContactNo());
    }

    /**
     * Opens the vehicle adder frame.
     */
    class AddVehicleListener implements ActionListener {
        /**
         * Instantiates a new frame of VehicleAdder.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            VehicleAdderModel vehicleAdderModel = new VehicleAdderModel(model.getClient());
            VehicleAdderView vehicleAdderView = new VehicleAdderView();
            VehicleAdderController vehicleAdderController = new VehicleAdderController(vehicleAdderView, vehicleAdderModel);
            vehicleAdderView.setVisible(true);
        }
    }


    /**
     * Logouts the user's account
     */
    class LogOutListener implements ActionListener {
        UserProfileView userProfileView;

        @Override
        public void actionPerformed(ActionEvent e) {
            int option = JOptionPane.showConfirmDialog(userProfileView, "Are you sure you want to Logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                model.getClient().logout(model.getClient().getUsername());
                System.exit(0);
            } else {

            }
        }
    }

    /**
     * Deletes the user's account
     */
    class DeleteListener implements ActionListener {
        private final UserProfileView userProfileView;

        public DeleteListener(UserProfileView userProfileView) {
            this.userProfileView = userProfileView;
        }

        public void actionPerformed(ActionEvent e) {
            int option = JOptionPane.showConfirmDialog(userProfileView, "Are you sure you want to delete your account?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                model.deleteAccount();
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(userProfileView, "Deletion canceled.");
            }
        }
    }

    /**
     * Reverts and discards the information of the user.
     */
    class ProfileCancelListener implements ActionListener {
        /**
         * Cancels the user changes by reverting the text fields to its default text.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getPnlEditProfile().getTxtFirstName().setText(model.getFirstName());
            view.getPnlEditProfile().getTxtLastName().setText(model.getLastName());
            view.getPnlEditProfile().getTxtContact().setText(model.getContactNo());
            populateFields(); // updates the information
        }
    }

    /**
     * Edits the information of the user.
     */
    public class ProfileEditListener implements ActionListener {
        /**
         * Processes the user changes.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            model.editUserInformation(
                    view.getPnlEditProfile().getFirstName(),
                    view.getPnlEditProfile().getLastName(),
                    view.getPnlEditProfile().getContact()
            );
            populateFields(); // updates the information
            //TODO: Display message indicating edit was successful
        }
    }

    /**
     * Cancels the editing of the car information.
     */
    class CarCancelListener implements ActionListener {
        /**
         * Processes the user request.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getPnlEditCars().getBtnCancel().setVisible(false);
            view.getPnlEditCars().getBtnContinue().setVisible(false);
            for (UserProfileView.EditCars.CarsPanel panel : pnlsCars) {
                panel.getTxtPlateNumber().setEditable(false);
                panel.getTxtPlateNumber().setFocusable(false);
                panel.getTxtModel().setEditable(false);
                panel.getTxtModel().setFocusable(false);
            }
        }
    }

    /**
     * Sends the information of the edited car information.
     */
    class CarContinueListener implements ActionListener {
        /**
         * Processes the user request.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String newType = pnlsCars[carIndex].getTxtVehicleType().getText();
            String newPlate = pnlsCars[carIndex].getTxtPlateNumber().getText();
            String newModel = pnlsCars[carIndex].getTxtModel().getText();
            model.editVehicleInformation(plateNumbers.get(carIndex), newPlate, newModel, newType);
        }
    }

    /**
     * Updates the user's password.
     */
    class SecurityConfirmListener implements ActionListener {
        /**
         * 1. Verifies if the input equates to the original password.
         * 2. Verifies if the new password and the confirmed new password matches.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!RegisterModel.encryptPassword(view.getPnlSecurityPage().getCurrentPassword()).equals(model.getPassword())) {
                view.getPnlSecurityPage().getLblMessage().setText("Current password does not match. Try again.");
                view.getPnlSecurityPage().getLblMessage().setForeground(Color.RED);
            } else {
                if (!RegisterModel.verifySignupPassword(
                        view.getPnlSecurityPage().getNewPassword(), view.getPnlSecurityPage().getConfirmNewPassword())) {
                    view.getPnlSecurityPage().getLblMessage().setText("New passwords do not match. Try again.");
                    view.getPnlSecurityPage().getLblMessage().setForeground(Color.RED);
                } else {
                    model.editPassword(RegisterModel.encryptPassword(view.getPnlSecurityPage().getNewPassword()));
                    view.getPnlSecurityPage().getLblMessage().setText("Your password has been successful changed.");
                    view.getPnlSecurityPage().getLblMessage().setForeground(Color.green);
                    model.editPassword(RegisterModel.encryptPassword(view.getPnlSecurityPage().getNewPassword()));
                }
            }
        }
    }

    /**
     * Applies the filters and displays the data in the table with the given filters.
     */
    class ApplyFiltersListener implements ActionListener {
        /**
         * Populates the tables with the given filters.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getPnlBookingsView().bookingPanel().getTablePanel().getTableModel().setRowCount(0);
            view.getPnlBookingsView().bookingPanel().getTablePanel().getScrollPane().getVerticalScrollBar().setValue(0);
            model.viewHistory();


            LocalDateTime dateNow = LocalDateTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

            String dateInput = String.valueOf(view.getPnlBookingsView().bookingPanel().getFilterPanel().getTxtDate().getText());
            String status = String.valueOf(view.getPnlBookingsView().bookingPanel().getFilterPanel().getCmbStatus().getSelectedItem());
            String type = String.valueOf(view.getPnlBookingsView().bookingPanel().getFilterPanel().getCmbVehicleType().getSelectedItem());

            List<List<String>> filteredBookings = new ArrayList<>();

            if (status.equals("All")) {
                if (dateInput.equals("All")) {
                    filteredBookings = model.getBookings().stream()
                            .toList();
                } else {
                    filteredBookings = model.getBookings().stream()
                            .filter(c -> c.get(2).equals(dateInput))
                            .toList();
                }
            } else if (status.equals("Current")) {
                filteredBookings = model.getBookings().stream()
                        .filter(c -> c.get(2).equals(dateNow.format(timeFormatter)))
                        .toList();
            } else if (status.equals("Future")) {
                filteredBookings = model.getBookings().stream()
                        .filter(c -> LocalDateTime.parse(c.get(2) + " " + c.get(3),
                                DateTimeFormatter.ofPattern("MM/dd/yy H:mm")).isAfter(dateNow))
                        .toList();
            } else if (status.equals("Completed")) {
                filteredBookings = model.getBookings().stream()
                        .filter(c -> LocalDateTime.parse(c.get(2) + " " + c.get(3),
                                DateTimeFormatter.ofPattern("MM/dd/yy H:mm")).isBefore(dateNow))
                        .toList();
            }

            switch (type) {
                case "All" -> {
                    for (List<String> booking : filteredBookings) {
                        String[] token = booking.toString().replace("[", "").replace("]", "").split(",");
                        String vehicleType = token[0];
                        String spot = token[1];
                        String date = token[2];
                        String timeIn = token[3];
                        String timeOut = token[4];

                        view.getPnlBookingsView().bookingPanel().getTablePanel().getTableModel().addRow(new Object[]{
                                vehicleType, spot, date, timeIn, timeOut
                        });
                    }
                } // end of case -> all
                case "Car" -> {
                    for (List<String> booking : filteredBookings) {
                        String[] token = booking.toString().replace("[", "").replace("]", "").split(",");
                        String vehicleType = token[0];
                        String spot = token[1];
                        String date = token[2];
                        String timeIn = token[3];
                        String timeOut = token[4];

                        if (vehicleType.equals("Car")) {
                            view.getPnlBookingsView().bookingPanel().getTablePanel().getTableModel().addRow(new Object[]{
                                    vehicleType, spot, date, timeIn, timeOut
                            });
                        }
                    }
                } // end of case -> car
                case "Motor" -> {
                    for (List<String> booking : filteredBookings) {
                        String[] token = booking.toString().replace("[", "").replace("]", "").split(",");
                        String vehicleType = token[0];
                        String spot = token[1];
                        String date = token[2];
                        String timeIn = token[3];
                        String timeOut = token[4];

                        if (vehicleType.equals("Motor")) {
                            view.getPnlBookingsView().bookingPanel().getTablePanel().getTableModel().addRow(new Object[]{
                                    vehicleType, spot, date, timeIn, timeOut
                            });
                        }
                    }
                } // end of case -> car
            }
        }
    }

    /**
     * Navigates to the next vehicle.
     */
    class NextListener implements ActionListener {
        /**
         * Switches the panel.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getPnlEditCars().getCardLayout().next(view.getPnlEditCars().getPnlCards());
            if (carIndex != max) {
                carIndex += 1;
            }
        }
    }

    /**
     * Navigates to the previous vehicle.
     */
    class PreviousListener implements ActionListener {
        /**
         * Switches the panel.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getPnlEditCars().getCardLayout().previous(view.getPnlEditCars().getPnlCards());
            if (carIndex != 0) {
                carIndex -= 1;
            }
        }
    }
}

