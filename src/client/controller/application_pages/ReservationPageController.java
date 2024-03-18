package client.controller.application_pages;

import client.model.application_pages.CarMotorButton;
import client.model.application_pages.ReservationPageModel;
import client.view.application_pages.ReservationPageView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private String btnID;

    private String btnDuration;

    private String date;

    private String[] timeAvailable;

    private String dateChosen;

    private String[] dateList;

    private int carsNumber;

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


        carsNumber = view.getMainBottomPanel().getParkingSlotsPanel().getCarButtonsSize();
        motorNumber = view.getMainBottomPanel().getParkingSlotsPanel().getMotorButtonsSize();

        int availableCarCount = 0;
        for (int x = 0; x < carsNumber; x++) {

            boolean isTaken = true;
            if (model.getAvailableTime(("C" + (x + 1)), "1", date).length > 1) {
                isTaken = false;
                availableCarCount++;
            }
            view.getMainBottomPanel().getParkingSlotsPanel().setCarMotorButtonsIcon(true, x, isTaken);
        }

        int availableMotorCount = 0;

        for (int x = 0; x < motorNumber; x++) {
            boolean isTaken = true;

            if (model.getAvailableTime(("M" + (x + 1)), "1", date).length > 1) {
                isTaken = false;
                availableMotorCount++;
            }

            view.getMainBottomPanel().getParkingSlotsPanel().setCarMotorButtonsIcon(false, x, isTaken);
        }





        view.getMainBottomPanel().getParkingSlotsPanel().setCarButtonsListener(new CarMotorListener());
        view.getParkingSlotButtonsView().setBtnCloseListener(new ExitListener());
        view.getParkingSlotButtonsView().setReserveSlotListener(new ReserveSlotListener());
        view.getParkingSlotButtonsView().setDateListener(new DateListener());
        view.getParkingSlotButtonsView().setDurationListener(new DurationListener());
        view.getMainTopPanel().setTxtSearchBarListener(new SearchListener());

        view.getMainTopPanel().setPnlAvailCar(String.valueOf(availableCarCount));
        view.getMainTopPanel().setPnlAvailMotor(String.valueOf(availableMotorCount));
        view.getMainTopPanel().setPnlTotalBookings(model.getTotalBookings());


        view.getParkingSlotButtonsView().setDateList(dateList);

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

    class DateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tempDate = view.getParkingSlotButtonsView().getDateChosen();
            if (!tempDate.equals("Select Date:")) {
                date = tempDate;
                view.getParkingSlotButtonsView().setLblDate(date);
            }
        }
    }

    class CarMotorListener implements ActionListener {

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

    class DurationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getParkingSlotButtonsView().setLblDate(date);
            String duration = view.getParkingSlotButtonsView().getDurationChosen();
            if (!duration.equals("Duration:")) {
                String[] time = model.getAvailableTime(btnID, duration, date);

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

    class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getTopCardLayout().show(view.getPnlCards(), "dashboard");
        }
    }

    ReservationPageView.ReserveSlotConfirmationView confirmationView;

    class ReserveSlotListener implements ActionListener {
        String attemptBooking = null;
        @Override
        public void actionPerformed(ActionEvent e) {
            String startTime = view.getParkingSlotButtonsView().getStartTime();
            String duration = view.getParkingSlotButtonsView().getDurationChosen();
                if (startTime != null && duration != null) {
                    attemptBooking = String.valueOf(model.attemptBooking(btnID, date, startTime, duration));
                    if (model.checkIfTakenForDay(btnID)) {
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
    class CloseConfirmationListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            confirmationView.dispose();
        }
    }

    class SearchListener implements ActionListener {
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

