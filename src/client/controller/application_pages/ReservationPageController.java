package client.controller.application_pages;

import client.model.application_pages.CarMotorButton;
import client.model.application_pages.ReservationPageModel;
import client.view.application_pages.ReservationPageView;
import utilities.Resources;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * The ReservationPageController processes the user requests for reserving a parking slot.
 * Based on the user request, the ReservationPageController defines methods and invokes methods in the View and Model
 * to accomplish the requested action.
 */
public class ReservationPageController {
    /**
     * The view ReservationPageView.
     */
    private ReservationPageView view;
    /**
     * The model ReservationPageModel.
     */
    private ReservationPageModel model;
    /**
     * The timer to delay UI components.
     */
    private Timer timer;
    /**
     * The id or index of the parking spots.
     */
    private String btnID;
    /**
     * The date of the reservation.
     */
    private String date;
    /**
     * Array of the available time slots of each parking spot.
     * Initial values: 7:00 AM to 5:00 PM
     */
    private String[] timeAvailable;
    /**
     * The chosen date
     */
    private String dateChosen;
    /**
     * The list of dates.
     * The date range is from the running time application up to three days.
     */
    private String[] dateList;
    /**
     * The number of available car slots.
     */
    private int carsNumber;
    /**
     * The number of available motorcycle slots.
     */
    private int motorNumber;

    /**
     * Constructs a ReservationPageController with a specified view and model.
     * @param view The specified view.
     * @param model The specified model.
     */
    public ReservationPageController(ReservationPageView view, ReservationPageModel model) {
        this.view = view;
        this.model = model;
        date =model.getDate();
        dateList = model.getDateList();
        view.setUserFullName(model.getFullName());

        // constants/variables
        view.getLblDate().setText("");
        view.getParkingSlotButtonsView().getBtnReserve().setEnabled(false);
        view.getParkingSlotButtonsView().getCmbDuration().setEnabled(false);
        view.getParkingSlotButtonsView().getCmbTime().setEnabled(false);

        carsNumber = view.getMainBottomPanel().getParkingSlotsPanel().getCarButtonsSize();
        motorNumber = view.getMainBottomPanel().getParkingSlotsPanel().getMotorButtonsSize();

        int availableCarCount = 0;
        for (int x = 0; x < carsNumber; x++) {

            boolean isTaken = true;

            if (model.getAvailableTime(("C" + (x + 1)), "1", date)!= null) {
                isTaken = false;
                availableCarCount++;
            }
            view.getMainBottomPanel().getParkingSlotsPanel().setCarMotorButtonsIcon(true, x, isTaken);
        }

        int availableMotorCount = 0;

        for (int x = 0; x < motorNumber; x++) {
            boolean isTaken = true;

            if (model.getAvailableTime(("M" + (x + 1)), "1", date)!= null) {
                isTaken = false;
                availableMotorCount++;
            }
            view.getMainBottomPanel().getParkingSlotsPanel().setCarMotorButtonsIcon(false, x, isTaken);
        }

        // action listeners
        view.getMainBottomPanel().getParkingSlotsPanel().setCarButtonsListener(new CarMotorListener());
        view.getParkingSlotButtonsView().setBtnCloseListener(new ExitListener());
        view.getParkingSlotButtonsView().setReserveSlotListener(new ReserveSlotListener());
        view.getParkingSlotButtonsView().setDateListener(new DateListener());
        view.getParkingSlotButtonsView().setDurationListener(new DurationListener());
        view.getParkingSlotButtonsView().getCmbDuration().addActionListener(e -> {
            if (!Objects.equals(view.getParkingSlotButtonsView().getCmbDuration().getSelectedItem(), "Duration:")) {
                view.getParkingSlotButtonsView().getCmbTime().setEnabled(true);
            }
        });
        view.getParkingSlotButtonsView().getCmbTime().addActionListener(e -> {
            if (!Objects.equals(view.getParkingSlotButtonsView().getCmbTime().getSelectedItem(), "Select Time:")) {
                view.getParkingSlotButtonsView().getBtnReserve().setEnabled(true);
            }
        });
        view.getMainTopPanel().setTxtSearchBarListener(new SearchListener());

        view.getMainTopPanel().setPnlAvailCar(String.valueOf(availableCarCount));
        view.getMainTopPanel().setPnlAvailMotor(String.valueOf(availableMotorCount));
        view.getMainTopPanel().setPnlTotalBookings(model.getTotalBookings());

        view.getParkingSlotButtonsView().setDateList(dateList);

        // mouse listeners
        view.getParkingSlotButtonsView().getBtnReserve().addMouseListener(new Resources.CursorChanger
                (view.getParkingSlotButtonsView().getBtnReserve()));

        // focus listeners
        view.getTxtSearchbar().addFocusListener(new Resources.TextFieldFocus(
                view.getTxtSearchbar(), "Search date (MM/DD/YY)"
        ));
    }

    /**
     * Updates the time by refreshing the components of the UI.
     */
    private void updateTime() {
        SwingUtilities.invokeLater(() -> {
            //String time = model.getTime();
            //view.getLblDate().setText(time);
        });
    }

    /**
     * Retrieves and sets the input of date during reservation.
     */
    class DateListener implements ActionListener {
        /**
         * Sets a date of the reservation.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String tempDate = view.getParkingSlotButtonsView().getDateChosen();
            if (!tempDate.equals("Select Date:")) {
                date = tempDate;
                view.getParkingSlotButtonsView().setLblDate(date);
                view.getParkingSlotButtonsView().getCmbDuration().setEnabled(true);
            }
        }
    }

    /**
     * Identifies whether the parking spot is for cars or motorcycles.
     */
    class CarMotorListener implements ActionListener {
        /**
         * Sets the vehicle type in the parking spot information.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getTopCardLayout().show(view.getPnlCards(), "buttons");

            CarMotorButton buttonClicked = (CarMotorButton) e.getSource();
            btnID = buttonClicked.getIdentifier();

            view.getParkingSlotButtonsView().setLblDate(date);

            if (btnID.contains("C")) {
                view.getParkingSlotButtonsView().setVehiclesList(model.getCars());
                view.getParkingSlotButtonsView().setLblType("Car");
            } else {
                view.getParkingSlotButtonsView().setVehiclesList(model.getMotorcycles());
                view.getParkingSlotButtonsView().setLblType("Motor");
            }

            view.getParkingSlotButtonsView().setLblSlotNumber(btnID);
            view.getParkingSlotButtonsView().setLblDate(date);
            view.getParkingSlotButtonsView().resetDate();
            view.getParkingSlotButtonsView().resetTime();
            view.getParkingSlotButtonsView().resetDuration();
        }
    }

    /**
     * Retrieves and sets the duration of the reservation booking.
     */
    class DurationListener implements ActionListener {
        /**
         * Sets the duration of the parking reservation booking.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getParkingSlotButtonsView().setLblDate(date);
            String duration = view.getParkingSlotButtonsView().getDurationChosen();
            if (!duration.equals("Duration:")) {
                String[] time = model.getAvailableTime(btnID, duration, date);
                view.getParkingSlotButtonsView().getBtnReserve().setEnabled(true);

                if (time != null) {
                    view.getParkingSlotButtonsView().setLblStatus("Available");
                    view.getParkingSlotButtonsView().setTimeList(time);
                } else {
                    view.getParkingSlotButtonsView().setLblStatus("Unavailable");
                    String[] unavailable = {"Unavailable"};
                    view.getParkingSlotButtonsView().setTimeList(unavailable);
                }
            }

        }
    }

    /**
     * Exits the button panel.
     */
    class ExitListener implements ActionListener {
        /**
         * Closes the panel and return the default top panel.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getTopCardLayout().show(view.getPnlCards(), "dashboard");
        }
    }

    ReservationPageView.ReserveSlotConfirmationView confirmationView;

    /**
     * Reserves the booking with the given information.
     */
    class ReserveSlotListener implements ActionListener {
        /**
         * The attempted booking.
         */
        String attemptBooking = null;

        /**
         * Processes the booking with the given information.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String startTime = view.getParkingSlotButtonsView().getStartTime();
            String duration = view.getParkingSlotButtonsView().getDurationChosen();
                if (startTime != null && duration != null) {
                    attemptBooking = String.valueOf(model.attemptBooking(btnID, date, startTime, duration));
                    if (!model.checkIfTakenForDay(btnID)) {
                        String id = "";
                        for (int x =1 ; x < btnID.length(); x ++) {
                            id+= btnID.charAt(x);
                        }
                        view.getMainBottomPanel()
                                .getParkingSlotsPanel()
                                .setCarMotorButtonsIcon(btnID.contains("C"), Integer.parseInt(id)-1, true);
                    }
                    view.getParkingSlotButtonsView().resetDuration();
                    timeAvailable = model.getAvailableTime(btnID, duration, date);
                    if (timeAvailable != null) {
                        view.getParkingSlotButtonsView().setLblStatus("Available");
                        view.getParkingSlotButtonsView().setTimeList(timeAvailable);
                    } else {
                        String[] unavailable = {"Unavailable"};
                        view.getParkingSlotButtonsView().setTimeList(unavailable);}
                }

                if (attemptBooking.equals("true"))
                    confirmationView = view.getReserveSlotConfirmationView(true);
                else
                    confirmationView = view.getReserveSlotConfirmationView(false);
                confirmationView.setBtnCloseConfirmationListener(new CloseConfirmationListener());
        }
    }

    /**
     * Closes the dialog of the booking confirmation message.
     */
    class CloseConfirmationListener implements ActionListener {
        /**
         * Closes the dialog.
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {

            confirmationView.dispose();
        }
    }

    /**
     * Processes the search based on the input.
     */
    class SearchListener implements ActionListener {
        /**
         * Processes the user request.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String search = ((JTextField) e.getSource()).getText();
            String identifier = model.findAvailableSlotOnDay(search,carsNumber,motorNumber);
            if (identifier != null) {
                view.getTopCardLayout().show(view.getPnlCards(), "buttons");

                if (identifier.contains("C")) {
                    view.getParkingSlotButtonsView().setVehiclesList(model.getCars());
                    view.getParkingSlotButtonsView().setLblType("Car");
                } else {
                    view.getParkingSlotButtonsView().setVehiclesList(model.getMotorcycles());
                    view.getParkingSlotButtonsView().setLblType("Motor");
                }
                view.getParkingSlotButtonsView().setLblSlotNumber(identifier);
            }
        }
    }
}

