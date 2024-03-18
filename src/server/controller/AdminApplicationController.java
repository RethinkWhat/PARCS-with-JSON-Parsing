package server.controller;

import server.model.ServerImplementation;
import server.view.AdminApplicationView;
import server.view.DashboardView;
import shared.ServerMessage;
import utilities.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Processes the server initialization and viewing of pertinent activities in the server.
 */
public class AdminApplicationController {
    /**
     * The object of server.
     */
    private ServerImplementation server;
    /**
     * The view
     */
    private AdminApplicationView view;
    /**
     * The status of the server.
     * True if online, false if offline.
     */
    boolean serverStatus = false;
    /**
     * The default port of the server.
     */
    final int address = 2000;
    /**
     * Instance variable of reservation parser.
     */
    /**
     * List of lists of car bookings.
     */
    private volatile List<List<String>> carBookings = new ArrayList<>();
    /**
     * List of lists of motor bookings.
     */
    private volatile List<List<String>> motorBookings = new ArrayList<>();

    /**
     * Constructs a ServerController with a specified AdminApplicationView.
     * @param view The specified AdminApplicationView.
     */
    public AdminApplicationController(AdminApplicationView view){
        try {
            this.server = new ServerImplementation(address);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.view = view;

        // constants / variables
        refreshBookings();

        // action listeners

        // server status page
        view.getServerStatusView().setServerListener(new ServerListener());
        view.setNavStatusListener(e -> {
            view.getMainCardLayout().show(view.getPnlCards(), "status");
            view.getLblLocation().setText("Server Status");
        });
        view.setNavDashboardListener(e -> {
            view.getMainCardLayout().show(view.getPnlCards(), "dashboard");
            view.getLblLocation().setText("Dashboard");
        });

        // dashboard page
        view.getDashboardView().setRefreshListener(e -> refreshBookings());
        view.getDashboardView().getPnlMainBottom().setApplyListener(new ApplyFiltersListener());
        view.getDashboardView().getPnlMainBottom().setClearListener(e -> {
            view.getDashboardView().getPnlMainBottom().getTblModel().setRowCount(0);
            view.getDashboardView().getPnlMainBottom().getScrollPane().getVerticalScrollBar().setValue(0);
            view.getDashboardView().getPnlMainBottom().getTxtDate().setText("Search Date (MM/DD/YY or All)");
            view.getDashboardView().getPnlMainBottom().getCmbStatus().setSelectedIndex(0);
            view.getDashboardView().getPnlMainBottom().getCmbVehicleType().setSelectedIndex(0);
        });

        // mouse listeners
        view.getBtnNavDashboard().addMouseListener(new Resources.CursorChanger(view.getBtnNavDashboard()));
        view.getBtnNavStatus().addMouseListener(new Resources.CursorChanger(view.getBtnNavStatus()));

        // server status page
        view.getServerStatusView().getServerSwitch().addMouseListener(
                new Resources.CursorChanger(view.getServerStatusView().getServerSwitch()));

        // dashboard page
        view.getDashboardView().getBtnRefresh().addMouseListener(new Resources.CursorChanger(view.getDashboardView().getBtnRefresh()));

        view.getDashboardView().getPnlMainBottom().getBtnApply().addMouseListener(new Resources.CursorChanger(
                view.getDashboardView().getPnlMainBottom().getBtnApply()));
        view.getDashboardView().getPnlMainBottom().getBtnClear().addMouseListener(new Resources.CursorChanger(
                view.getDashboardView().getPnlMainBottom().getBtnClear()));

        // focus listeners
        view.getDashboardView().getPnlMainBottom().getTxtDate().addFocusListener(new Resources.TextFieldFocus(
                view.getDashboardView().getPnlMainBottom().getTxtDate(), "Search Date (MM/DD/YY or All)"));
        view.repaint();
        view.revalidate();
    }

    /**
     * Processes the initializing and disabling the server.
     */
    class ServerListener implements ActionListener {
        /**
         * Turns the server on and/or off.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!serverStatus) {
                view.getServerStatusView().setOnline();
                serverStatus = true;

                try {
                    server.getReg().rebind("server", server);
                    System.out.println("Server reopened");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                view.getServerStatusView().setOffline();
                serverStatus = false;

                // Stop the server logic
                try {
                    server.getReg().unbind("server");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                } catch (NotBoundException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Server closed.");
            }
        }
    }

    /**
     * Populates the list of car bookings and motor bookings.
     */
    private void refreshBookings() {
        // clears the lists
        carBookings.clear();
        motorBookings.clear();

        // populate / repopulate lists
        try {
            carBookings = server.getAllCarBookings();
            motorBookings = server.getAllMotorBookings();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        view.getDashboardView().getPnlMainTop().getLblCarCount().setText(String.valueOf(carBookings.size()));
        view.getDashboardView().getPnlMainTop().getLblMotorCount().setText(String.valueOf(motorBookings.size()));
        view.getDashboardView().getPnlMainTop().getLblTotalCount().
                setText(String.valueOf(carBookings.size() + motorBookings.size()));

        System.out.println(carBookings);
        System.out.println(motorBookings);
    }

    /**
     * Displays the appropriate data in the table according to user-defined filters.
     */
    class ApplyFiltersListener implements ActionListener {
        /**
         * Displays the data given a set of filters.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // clears the table before populating it
            view.getDashboardView().getPnlMainBottom().getTblModel().setRowCount(0);
            view.getDashboardView().getPnlMainBottom().getScrollPane().getVerticalScrollBar().setValue(0);

            LocalDateTime dateNow = LocalDateTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

            String dateInput = String.valueOf(view.getDashboardView().getPnlMainBottom().getTxtDate().getText());
            String status = String.valueOf(view.getDashboardView().getPnlMainBottom().getCmbStatus().getSelectedItem());
            String type = String.valueOf(view.getDashboardView().getPnlMainBottom().getCmbVehicleType().getSelectedItem());

            List<List<String>> filteredCarBookings = new ArrayList<>();
            List<List<String>> filteredMotorBookings = new ArrayList<>();

            if (status.equals("All")) {
                if (dateInput.equals("All")) {
                    filteredCarBookings = carBookings.stream()
                            .toList();
                    filteredMotorBookings = motorBookings.stream()
                            .toList();
                } else {
                    filteredCarBookings = carBookings.stream()
                            .filter(c -> c.get(2).equals(dateInput))
                            .toList();
                    filteredMotorBookings = motorBookings.stream()
                            .filter(m -> m.get(2).equals(dateInput))
                            .toList();
                }
            } else if (status.equals("Current")) {
                filteredCarBookings = carBookings.stream()
                        .filter(c -> c.get(2).equals(dateNow.format(timeFormatter)))
                        .toList();
                filteredMotorBookings = motorBookings.stream()
                        .filter(m -> m.get(2).equals(dateNow.format(timeFormatter)))
                        .toList();
            } else if (status.equals("Future")) {
                filteredCarBookings = carBookings.stream()
                        .filter(c -> LocalDateTime.parse(c.get(2) + " " + c.get(3),
                                DateTimeFormatter.ofPattern("MM/dd/yy H:mm")).isAfter(dateNow))
                        .toList();
                filteredMotorBookings = motorBookings.stream()
                        .filter(m -> LocalDateTime.parse(m.get(2) + " " + m.get(3),
                                DateTimeFormatter.ofPattern("MM/dd/yy H:mm")).isAfter(dateNow))
                        .toList();
            } else if (status.equals("Completed")) {
                filteredCarBookings = carBookings.stream()
                        .filter(c -> LocalDateTime.parse(c.get(2) + " " + c.get(3),
                                DateTimeFormatter.ofPattern("MM/dd/yy H:mm")).isBefore(dateNow))
                        .toList();
                filteredMotorBookings = motorBookings.stream()
                        .filter(m -> LocalDateTime.parse(m.get(2) + " " + m.get(3),
                                DateTimeFormatter.ofPattern("MM/dd/yy H:mm")).isBefore(dateNow))
                        .toList();
            }

            switch (type) {
                case "All" -> {
                    for (List<String> mBooking : filteredMotorBookings) {
                        String[] token = mBooking.toString().replace("[", "").replace("]", "").split(",");
                        String username = token[0];
                        String spot = token[1];
                        String date = token[2];
                        String timeIn = token[3];
                        String timeOut = token[4];
                        String duration = token[5];

                        view.getDashboardView().getPnlMainBottom().getTblModel().addRow(new Object[]{
                                date, username, spot, "Motorcycle", timeIn, timeOut, duration + "hour/s"
                        });
                    }
                    for (List<String> cBooking : filteredCarBookings) {
                        String[] token = cBooking.toString().replace("[", "").replace("]", "").split(",");
                        String username = token[0];
                        String spot = token[1];
                        String date = token[2];
                        String timeIn = token[3];
                        String timeOut = token[4];
                        String duration = token[5];

                        view.getDashboardView().getPnlMainBottom().getTblModel().addRow(new Object[]{
                                date, username, spot, "Car", timeIn, timeOut, duration + "hour/s"
                        });

                    }
                } // end of case -> all

                case "Car" -> {
                    for (List<String> cBooking : filteredCarBookings) {
                        String[] token = cBooking.toString().replace("[", "").replace("]", "").split(",");
                        String username = token[0];
                        String spot = token[1];
                        String date = token[2];
                        String timeIn = token[3];
                        String timeOut = token[4];
                        String duration = token[5];
                        view.getDashboardView().getPnlMainBottom().getTblModel().addRow(new Object[]{
                                date, username, spot, "Car", timeIn, timeOut, duration + "hour/s"
                        });
                    }
                } // end of case -> car

                case "Motorcycle" -> {
                    for (List<String> mBooking : filteredMotorBookings) {
                        String[] token = mBooking.toString().replace("[", "").replace("]", "").split(",");
                        String username = token[0];
                        String spot = token[1];
                        String date = token[2];
                        String timeIn = token[3];
                        String timeOut = token[4];
                        String duration = token[5];
                        view.getDashboardView().getPnlMainBottom().getTblModel().addRow(new Object[]{
                                date, username, spot, "Motorcycle", timeIn, timeOut, duration + "hour/s"
                        });
                    }
                } // end of case -> motorcycle
            }
        }
    }
}

