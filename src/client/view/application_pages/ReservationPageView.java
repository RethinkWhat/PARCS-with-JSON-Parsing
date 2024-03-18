package client.view.application_pages;

import client.model.application_pages.CarMotorButton;
import utilities.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Template for ReservationPageView object.
 * The ReservationPage is the home page of the client application.
 */
public class ReservationPageView extends JPanel {
    /**
     * The panel that holds different component panels.
     */
    private JPanel pnlCards;
    /**
     * The button that shows the current number of available car slots.
     */
    private JButton btnAvailCar;
    /**
     * The button that shows the current number of available motorcycle slots.
     */
    private JButton btnAvailMotor;
    /**
     * The button that shows the current number of total bookings.
     */
    private JButton btnTotalBookings;
    /**
     * The search bar.,
     */
    private JTextField txtSearchbar;
    /**
     * The first name of the current user.
     */
    private JLabel lblName;
    /**
     * The current date.
     */
    private JLabel lblDate;
    /**
     * The stylesheet.
     */
    private Resources res = new Resources();
    /**
     * Instance variable of GridBagConstraints used for JPanels using GridBagLayout.
     */
    private GridBagConstraints gbc;
    /**
     * Variable to hold full name of user. Ramon is the placeholder.
     */
    private JLabel userFullName = new JLabel("Ramon");
    /**
     * The CardLayout that controls the components of the MainTopPanel.
     */
    private CardLayout topCardLayout = new CardLayout();
    /**
     * The panel of MainBottomPanel.
     */
    private MainBottomPanel mainBottomPanel;
    /**
     * The panel for the parking slots buttons.
     */
    private ParkingSlotButtonsView parkingSlotButtonsView;
    /**
     * The dialog for successful booking.
     */
    private ReserveSlotConfirmationView reserveSlotConfirmationView;
    /**
     * The dialog for unsuccessful booking.
     */
    private ErrorMessageView errorMessageView;
    /**
     * The icon for a taken car parking spot.
     */
    private Icon takenCar = res.iconTakenCar;
    /**
     * The icon for an available car parking spot.
     */
    private Icon availCar = res.iconAvailableCar;
    /**
     * The icon for a taken motor parking spot.
     */
    private Icon takenMotor = res.iconTakenMotor;
    /**
     * The icon for an available motor parking spot.
     */
    private Icon availMotor = res.iconAvailableMotor;
    /**
     * The panel for the MainTopPanel.
     */
    private MainTopPanel mainTopPanel;

    /**
     * Retrieves the current taken car parking spot icon.
     *
     * @return The current takenCar icon.
     */
    public Icon getTakenCar() {
        return takenCar;
    }

    /**
     * Retrieves the current available car parking spot icon.
     *
     * @return The current availCar icon.
     */
    public Icon getAvailCar() {
        return availCar;
    }

    /**
     * Constructs a panel of ReservationPageView.
     */
    public ReservationPageView() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(25, 25, 25, 25));

        pnlCards = new JPanel(topCardLayout);
        add(pnlCards, BorderLayout.NORTH);

        parkingSlotButtonsView = new ParkingSlotButtonsView();

        mainTopPanel = new MainTopPanel();
        // Top Panel
        pnlCards.add(mainTopPanel, "dashboard");
        pnlCards.add(parkingSlotButtonsView, "buttons");
        topCardLayout.show(pnlCards, "dashboard");

        mainBottomPanel = new MainBottomPanel();

        // Bottom Panel
        add(mainBottomPanel, BorderLayout.SOUTH);

        this.setPreferredSize(new Dimension(1100, 700));
    }

    /**
     * The panel that contains the parking slots.
     */
    public class MainBottomPanel extends JPanel {
        /**
         * The rounded panel.
         */
        private JPanel container;
        /**
         * The panel that holds the parking slots.
         */
        private ParkingSlotsPanel parkingSlotsPanel;

        /**
         * Constructs a panel of MainBottomPanel.
         */
        public MainBottomPanel() {
            setLayout(new BorderLayout());

            container = res.createPnlRounded(1300, 510, res.white, res.lightGray);
            container.setLayout(new BorderLayout());

            JLabel lblParkingTitle = res.createLblH1("SLU Maryheights Parking", res.eerieBlack);
            lblParkingTitle.setBorder(new EmptyBorder(20, 40, 0, 0));
            container.add(lblParkingTitle, BorderLayout.NORTH);

            parkingSlotsPanel = new ParkingSlotsPanel();
            parkingSlotsPanel.setPreferredSize(new Dimension(1100, 350));
            parkingSlotsPanel.setOpaque(false);
            container.add(parkingSlotsPanel, BorderLayout.CENTER);

            add(container, BorderLayout.CENTER);

            setPreferredSize(new Dimension(1300, 510));
        }

        /**
         * Retrieves the current ParkingSlotsPanel of parkingSlotsPanel
         *
         * @return The current parkingSlotsPanel.
         */
        public ParkingSlotsPanel getParkingSlotsPanel() {
            return parkingSlotsPanel;
        }

        /**
         * The panel that contains the parking slots.
         */
        public class ParkingSlotsPanel extends JPanel {
            /**
             * The number of car slots.
             */
            private static final int NUM_CAR_SLOTS = 5;
            /**
             * The number of motor slots.
             */
            private static final int NUM_MOTOR_SLOTS = 2;
            /**
             * The array of car buttons.
             */
            private CarMotorButton[] carButtons;
            /**
             * The array of motor buttons.
             */
            private CarMotorButton[] motorButtons;

            /**
             * Constructs a panel of ParkingSlots.
             */
            public ParkingSlotsPanel() {
                initializeButtons();
            }

            /**
             * Constructs the buttons.
             */
            private void initializeButtons() {
                carButtons = new CarMotorButton[NUM_CAR_SLOTS * 2];
                motorButtons = new CarMotorButton[NUM_MOTOR_SLOTS * 2];

                // Initialize buttons for the top row
                for (int i = 0; i < NUM_CAR_SLOTS; i++) {
                    String carLabel = "C" + (i + 1);
                    carButtons[i] = new CarMotorButton(carLabel);
                    carButtons[i].setIdentifier(carLabel);

                    //for buttons with image
                    carButtons[i] = new CarMotorButton(carLabel, res.iconAvailableCar);
                    carButtons[i].setOpaque(false);
                    carButtons[i].setContentAreaFilled(false);
                    carButtons[i].setBorderPainted(false);
                }

                for (int i = 0; i < NUM_MOTOR_SLOTS; i++) {
                    String motorLabel = "M" + (i + 1);
                    motorButtons[i] = new CarMotorButton(motorLabel);
                    motorButtons[i].setIdentifier(motorLabel);

                    //for buttons with image
                    motorButtons[i] = new CarMotorButton(motorLabel, res.iconAvailableMotor);
                    motorButtons[i].setOpaque(false);
                    motorButtons[i].setContentAreaFilled(false);
                    motorButtons[i].setBorderPainted(false);
                }

                // Initialize buttons for the bottom row
                for (int i = 0; i < NUM_CAR_SLOTS; i++) {
                    String carLabel = "C" + (i + 6);
                    carButtons[i + NUM_CAR_SLOTS] = new CarMotorButton(carLabel);

                    //for buttons with image
                    carButtons[i + NUM_CAR_SLOTS] = new CarMotorButton(carLabel, res.iconAvailableCar);
                    carButtons[i + NUM_CAR_SLOTS].setOpaque(false);
                    carButtons[i + NUM_CAR_SLOTS].setContentAreaFilled(false);
                    carButtons[i + NUM_CAR_SLOTS].setBorderPainted(false);
                    carButtons[i + NUM_CAR_SLOTS].setFocusPainted(false);
                }

                for (int i = 0; i < NUM_MOTOR_SLOTS; i++) {
                    String motorLabel = "M" + (i + 3);
                    motorButtons[i + NUM_MOTOR_SLOTS] = new CarMotorButton(motorLabel);

                    //for buttons with image
                    motorButtons[i + NUM_MOTOR_SLOTS] = new CarMotorButton(motorLabel, res.iconAvailableMotor);
                    motorButtons[i + NUM_MOTOR_SLOTS].setOpaque(false);
                    motorButtons[i + NUM_MOTOR_SLOTS].setContentAreaFilled(false);
                    motorButtons[i + NUM_MOTOR_SLOTS].setBorderPainted(false);
                }
            }

            /**
             * Paints the parking slots outline.
             *
             * @param g the <code>Graphics</code> object to protect
             */
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                int slotWidth = 1000 / (NUM_CAR_SLOTS + NUM_MOTOR_SLOTS);
                int slotHeight = 300 / 2;

                int motorSlotWidth = 90; // int motorSlotWidth = slotWidth / 2;

                int xOffset = (panelWidth - 1000) / 2;
                int yOffset = (panelHeight - 300) / 2;

                g.setColor(Color.black);

                // Draw Car Slots (Top Row)
                for (int i = 0; i < NUM_CAR_SLOTS; i++) {
                    int x = xOffset + i * slotWidth;
                    int y = yOffset;

                    // Draw rectangles to represent car slots
                    g.drawRect(x, y, slotWidth, slotHeight);

                    // Buttons for the car slots (added bounds for positioning)
                    carButtons[i].setBounds(x + slotWidth / 8, y + slotHeight / 8, slotWidth * 3 / 4, slotHeight * 3 / 4);
                    add(carButtons[i]);
                }

                // Draw Motor Slots (Top Row)
                for (int i = 0; i < NUM_MOTOR_SLOTS; i++) {
                    int x = xOffset + (NUM_CAR_SLOTS + i) * slotWidth;
                    int y = yOffset;

                    // Draw rectangles to represent motor slots
                    g.drawRect(x, y, motorSlotWidth, slotHeight);

                    // Buttons for the motor slot
                    motorButtons[i].setBounds(x + motorSlotWidth / 8, y + slotHeight / 8, motorSlotWidth * 3 / 4, slotHeight * 3 / 4);
                    add(motorButtons[i]);
                }

                // Draw Car Slots (Bottom Row)
                for (int i = 0; i < NUM_CAR_SLOTS; i++) {
                    int x = xOffset + i * slotWidth;
                    int y = yOffset + slotHeight;

                    // Draw rectangles to represent car slots
                    g.drawRect(x, y, slotWidth, slotHeight);

                    // Buttons for the car slots (added bounds for positioning)
                    carButtons[i + NUM_CAR_SLOTS].setBounds(x + slotWidth / 8, y + slotHeight / 8, slotWidth * 3 / 4, slotHeight * 3 / 4);
                    add(carButtons[i + NUM_CAR_SLOTS]);
                }

                // Draw Motor Slots (Bottom Row)
                for (int i = 0; i < NUM_MOTOR_SLOTS; i++) {
                    int x = xOffset + (NUM_CAR_SLOTS + i) * slotWidth;
                    int y = yOffset + slotHeight;

                    // Draw rectangles to represent motor slots
                    g.drawRect(x, y, motorSlotWidth, slotHeight);

                    // Buttons for the motor slot
                    motorButtons[i + NUM_MOTOR_SLOTS].setBounds(x + motorSlotWidth / 8, y + slotHeight / 8, motorSlotWidth * 3 / 4, slotHeight * 3 / 4);
                    add(motorButtons[i + NUM_MOTOR_SLOTS]);
                }
            }

            /**
             * Sets a specified action listener for the parking spots.
             *
             * @param listener The specified action listener.
             */
            public void setCarButtonsListener(ActionListener listener) {
                for (JButton button : carButtons) {
                    button.addActionListener(listener);
                }
                for (JButton button : motorButtons) {
                    button.addActionListener(listener);
                }
            }

            /**
             * Sets a specified icon for the parking spots.
             *
             * @param isCar   Specified spot if car or motorcycle.
             * @param number  The index of the parking spot.
             * @param isTaken Specified spot if taken or not.
             */
            public void setCarMotorButtonsIcon(boolean isCar, int number, boolean isTaken) {
                if (isCar) {
                    if (isTaken) {
                        carButtons[number].setIcon(takenCar);
                    } else
                        carButtons[number].setIcon(availCar);
                } else {
                    if (isTaken) {
                        motorButtons[number].setIcon(takenMotor);
                    } else {
                        motorButtons[number].setIcon(availMotor);
                    }
                }
            }

            /**
             * Retrieves the current size of the button.
             *
             * @return The current button size.
             */
            public int getCarButtonsSize() {
                return carButtons.length;
            }

            /**
             * Retrieves the current size of the button.
             *
             * @return The current button size.
             */
            public int getMotorButtonsSize() {
                return motorButtons.length;
            }
        }
    }

    /**
     * The panel that contains multiple objects of the ButtonsPanel.
     */
    public class MainTopPanel extends JPanel {
        /**
         * The panel for available cars.
         */
        ButtonPanel pnlAvailCar;
        /**
         * The panel for available motors.
         */
        ButtonPanel pnlAvailMotor;
        /**
         * The panel for all the bookings.
         */
        ButtonPanel pnlTotalBookings;

        /**
         * Constructs a panel of MainTopPanel.
         */
        public MainTopPanel() {
            setBackground(res.lightGray);
            setLayout(new BorderLayout());

            JPanel pnlInformation = new JPanel(new GridBagLayout());
            pnlInformation.setPreferredSize(new Dimension(1300, 50));
            add(pnlInformation, BorderLayout.NORTH);
            pnlInformation.setBackground(res.lightGray);

            gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.ipadx = 475;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            lblName = res.createLblH2("Hello, " + "Ramon Jasmin" + "!", res.eerieBlack);
            pnlInformation.add(lblName, gbc);

            gbc.gridy = 1;
            gbc.gridwidth = 3;
            String date = "";
            lblDate = res.createLblH4(date, res.eerieBlack);
            pnlInformation.add(lblDate, gbc);

            gbc.gridy = 0;
            gbc.gridx = 1;
            gbc.gridwidth = 5;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.fill = GridBagConstraints.BOTH;
            txtSearchbar = res.createTxtRounded("Search date (MM/DD/YY)", res.white, res.gray, 30);
            pnlInformation.add(txtSearchbar, gbc);

            GridLayout gridLayout = new GridLayout(0, 3);
            gridLayout.setHgap(10);

            JPanel pnlButtons = new JPanel(gridLayout);
            pnlButtons.setPreferredSize(new Dimension(1300, 100));
            pnlButtons.setBorder(new EmptyBorder(10, 0, 10, 0));
            pnlButtons.setBackground(res.lightGray);
            add(pnlButtons, BorderLayout.SOUTH);


            // Update parameters as models from controller when backend has commenced.
            pnlAvailCar = new ButtonPanel(
                    btnAvailCar = res.createBtnIconOnly(res.iconSolidCar, 50, 50),
                    res.createLblH1("13", res.eerieBlack),
                    res.createLblP("<html>Available<br> Car Slots</html>", res.eerieBlack)
            );
            pnlButtons.add(pnlAvailCar);

            pnlAvailMotor = new ButtonPanel(
                    btnAvailMotor = res.createBtnIconOnly(res.iconSolidMotor, 50, 50),
                    res.createLblH1("10", res.eerieBlack),
                    res.createLblP("<html>Available<br> Motor Slots</html>", res.eerieBlack)
            );
            pnlButtons.add(pnlAvailMotor);

            pnlTotalBookings = new ButtonPanel(
                    btnTotalBookings = res.createBtnIconOnly(res.iconSolidTicket, 50, 50),
                    res.createLblH1("3", res.eerieBlack),
                    res.createLblP("<html>Your Total<br> Bookings</html>", res.eerieBlack)
            );
            pnlButtons.add(pnlTotalBookings);

            this.setPreferredSize(new Dimension(1300, 150));
        }

        /**
         * Sets a specified panel for available cars.
         *
         * @param availCar The number of available car spots.
         */
        public void setPnlAvailCar(String availCar) {
            pnlAvailCar.setText(availCar);
        }

        /**
         * Sets a specified panel for available motors.
         *
         * @param availMotor The number of available motor spots.
         */
        public void setPnlAvailMotor(String availMotor) {
            pnlAvailMotor.setText(availMotor);
        }

        /**
         * Sets a specified panel for total bookings of the user.
         *
         * @param totalBookings The number of total user bookings.
         */
        public void setPnlTotalBookings(String totalBookings) {
            pnlTotalBookings.setText(totalBookings);
        }

        /**
         * Sets a specified action listener for the txtSearchBar.
         *
         * @param listener The specified action listener.
         */
        public void setTxtSearchBarListener(ActionListener listener) {
            txtSearchbar.addActionListener(listener);
        }

        /**
         * Retrieves the current JTextField of txtSearchbar.
         *
         * @return The current txtSearchbar.
         */
        public String getTxtSearchbar() {
            return txtSearchbar.getText();
        }


    }

    /**
     * The panel that creates the useful buttons of the home page.
     */
    class ButtonPanel extends JPanel {
        /**
         * The rounded panel.
         */
        private JPanel container;
        /**
         * The label for number.
         */
        private JLabel number;

        /**
         * Constructs a panel with a specified button, number label, and title label.
         */
        public ButtonPanel(JButton button, JLabel number, JLabel title) {
            setLayout(new BorderLayout());

            container = res.createPnlRounded(100, 80, res.white, res.lightGray);
            container.setLayout(new GridLayout(0, 3));
            add(container, BorderLayout.NORTH);

            this.number = number;
            container.add(button);
            container.add(this.number);
            container.add(title);

            this.setPreferredSize(new Dimension(100, 100));
        }

        /**
         * Mutates the new text of the number label.
         *
         * @param number The new number.
         */
        public void setText(String number) {
            this.number.setText(number);
        }
    }

    /**
     * Panel for the buttons when selecting a parking spot.
     */
    public class ParkingSlotButtonsView extends JPanel {
        /**
         * The button for close.
         */
        private JButton btnClose;
        /**
         * The panel that holds the components.
         */
        private JPanel pnlContainer;
        /**
         * The label for the parking spot/slot number.
         */
        private JLabel lblSlotNumber;
        /**
         * The label for vehicle type.
         */
        private JLabel lblType;
        /**
         * The label for parking status.
         */
        private JLabel lblStatus;
        /**
         * The label for reservation date.
         */
        private JLabel lblDate;
        /**
         * The button for reserve.
         */
        private JButton btnReserve;
        /**
         * The combo box for user vehicles.
         */
        private JComboBox<String> cmbVehicle;
        /**
         * The combo box for time.
         */
        private JComboBox<String> cmbTime;
        /**
         * The combo box for duration.
         */
        private JComboBox<String> cmbDuration;
        /**
         * The combo box for date.
         */
        private JComboBox<String> cmbDate;

        /**
         * The stylesheet.
         */
        private Resources res = new Resources();

        /**
         * Constructs a panel of ParkingSlotButtonsView
         */
        public ParkingSlotButtonsView() { // TODO: Replace constructors
            this.setLayout(new BorderLayout());

            pnlContainer = res.createPnlRounded(1300, 130, res.white, res.lightGray);
            pnlContainer.setLayout(new GridLayout(3, 1)); // Divided into 3 rows, 1 column
            add(pnlContainer, BorderLayout.CENTER);

            // pnlSlotNumber
            JPanel pnlSlotNumber = new JPanel(new GridBagLayout());
            pnlSlotNumber.setPreferredSize(new Dimension(1300, 50));
            pnlSlotNumber.setBackground(res.white);

            GridBagConstraints gbcSlotNumber = new GridBagConstraints();
            gbcSlotNumber.anchor = GridBagConstraints.WEST;
            gbcSlotNumber.insets = new Insets(0, 10, 0, 0);

            // Button for closing the panel
            btnClose = res.createBtnIconOnly(res.iconClose, 20, 20);
            pnlSlotNumber.add(btnClose, gbcSlotNumber);

            // Label for the slot number
            lblSlotNumber = res.createLblH1("SLOT A 01", res.eerieBlack);
            gbcSlotNumber.gridx = 1;
            gbcSlotNumber.weightx = 1.0;
            gbcSlotNumber.anchor = GridBagConstraints.CENTER;
            gbcSlotNumber.insets = new Insets(0, 10, 0, 0);
            pnlSlotNumber.add(lblSlotNumber, gbcSlotNumber);

            pnlContainer.add(pnlSlotNumber);

            // pnlSlotInformation
            JPanel pnlSlotInformation = new JPanel(new GridBagLayout());
            pnlSlotInformation.setPreferredSize(new Dimension(1300, 50));
            pnlSlotInformation.setBackground(Color.white);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 10, 0, 10);
            gbc.anchor = GridBagConstraints.CENTER;

            // Labels for Type
            JLabel lblTypeName = res.createLblH3("Type:", res.eerieBlack); // Used createLblH3 for distinction
            gbc.gridx = 0;
            pnlSlotInformation.add(lblTypeName, gbc);

            lblType = res.createLblP("Car", res.eerieBlack);
            gbc.gridx = 1;
            pnlSlotInformation.add(lblType, gbc);

            // Labels for Status
            JLabel lblStatName = res.createLblH3("Status:", res.eerieBlack); // Used createLblH3 for distinction
            gbc.gridx = 2;
            pnlSlotInformation.add(lblStatName, gbc);

            lblStatus = res.createLblP("Unavailable", res.eerieBlack);
            gbc.gridx = 3;
            pnlSlotInformation.add(lblStatus, gbc);

            // Labels for Date
            JLabel lblDateName = res.createLblH3("Date:", res.eerieBlack); // Used createLblH3 distinction
            gbc.gridx = 4;
            pnlSlotInformation.add(lblDateName, gbc);

            lblDate = res.createLblP("January 24, 2024", res.eerieBlack);
            gbc.gridx = 5;
            pnlSlotInformation.add(lblDate, gbc);

            pnlContainer.add(pnlSlotInformation);

            // pnlReserve
            JPanel pnlReserve = new JPanel(new GridBagLayout());
            pnlReserve.setPreferredSize(new Dimension(1300, 100));
            pnlReserve.setBackground(Color.white);

            gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.anchor = GridBagConstraints.CENTER;

            // Dropdown for Select Vehicle
            cmbVehicle = new JComboBox<>(new String[]{"Select Vehicle:", "Honda Civic", "Toyota Raize", "Ford Everest"});
            cmbVehicle.setPreferredSize(new Dimension(200, 40));
            cmbVehicle.setFont(new Font("Arial", Font.BOLD, 16));
            pnlReserve.add(cmbVehicle, gbc);

            // Dropdown for Select Date
            cmbDate = new JComboBox<>(new String[]{"Select Date:", "02/14/24", "02/16/24", "02/17/24"});
            cmbDate.setPreferredSize(new Dimension(200, 40));
            cmbDate.setFont(new Font("Arial", Font.BOLD, 16));
            gbc.gridx = 1;
            pnlReserve.add(cmbDate, gbc);

            // Dropdown for Duration
            cmbDuration = new JComboBox<>(new String[]{"Duration:", "1hr", "2hr", "3hr", "4hr"});
            cmbDuration.setPreferredSize(new Dimension(200, 40));
            cmbDuration.setFont(new Font("Arial", Font.BOLD, 16));
            gbc.gridx = 2;
            pnlReserve.add(cmbDuration, gbc);

            // Dropdown for Select Time
            cmbTime = new JComboBox<>(new String[]{"Select Time:", "6:00", "7:00", "8:00", "9:00",
                    "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"});
            cmbTime.setPreferredSize(new Dimension(200, 40));
            cmbTime.setFont(new Font("Arial", Font.BOLD, 16));
            gbc.gridx = 3;
            pnlReserve.add(cmbTime, gbc);

            // Reserve Slot Button
            btnReserve = res.createBtnRounded("Reserve Slot", res.celadon, res.eerieBlack, 15);
            gbc.gridx = 4;
            gbc.gridwidth = 2;
            btnReserve.setPreferredSize(new Dimension(140, 40));
            btnReserve.setFont(new Font("Arial", Font.BOLD, 16));
            pnlReserve.add(btnReserve, gbc);

            pnlContainer.add(pnlReserve);

            this.setPreferredSize(new Dimension(1300, 130));
        }

        /**
         * Retrieves the current JButton of btnClose.
         * @return The current btnClose.
         */
        public JButton getBtnClose() {
            return btnClose;
        }

        /**
         * Retrieves the current JButton of btnReserve.
         * @return The current btnReserve.
         */
        public JButton getBtnReserve() {
            return btnReserve;
        }

        /**
         * Retrieves the current JComboBox of cmbVehicle.
         * @return The current cmbVehicle.
         */
        public JComboBox<String> getCmbVehicle() {
            return cmbVehicle;
        }

        /**
         * Retrieves the current JComboBox of cmbTime.
         * @return The current cmbTime.
         */
        public JComboBox<String> getCmbTime() {
            return cmbTime;
        }

        /**
         * Retrieves the current JComboBox of cmbDuration.
         * @return The current cmbDuration.
         */
        public JComboBox<String> getCmbDuration() {
            return cmbDuration;
        }

        /**
         * Retrieves the current JComboBox of cmbDate.
         * @return The current cmbDate.
         */
        public JComboBox<String> getCmbDate() {
            return cmbDate;
        }

        /**
         * Enables the dropdown box.
         * @param enabled True if activated, false if otherwise.
         */
        public void setDropdownsEnabled(boolean enabled) {
            cmbVehicle.setEnabled(enabled);
            cmbDate.setEnabled(enabled);
            cmbTime.setEnabled(enabled);
            cmbDuration.setEnabled(enabled);
        }

        /**
         * To be implemented in version 2.0.
         */
        public void disableDropdowns() {
            setDropdownsEnabled(false);
        }

        /**
         * To be implemented in version 2.0.
         */
        public void enableDropdowns() {
            setDropdownsEnabled(true);
        }

        /**
         * Mutates the label of the parking spot.
         * @param label The new label.
         */
        public void setLblSlotNumber(String label) {
            lblSlotNumber.setText("SPOT " + label);
        }

        /**
         * Sets a specified action listener for btnClose.
         * @param listener The specified action listener.
         */
        public void setBtnCloseListener(ActionListener listener) {
            btnClose.addActionListener(listener);
        }

        /**
         * Mutates the date.
         * @param date The new date.
         */
        public void setLblDate(String date) {
            lblDate.setText(date);
        }

        /**
         * Sets a specified action listener for btnReserve.
         * @param listener The specified action listener.
         */
        public void setReserveSlotListener(ActionListener listener) {
            btnReserve.addActionListener(listener);
        }

        /**
         * Mutates the type.
         * @param type The new type.
         */
        public void setLblType(String type) {
            lblType.setText(type);
        }

        /**
         * Mutates the status.
         * @param status The new status.
         */
        public void setLblStatus(String status) {
            lblStatus.setText(status);
        }

        /**
         * Sets a specified model of cmbVehicle.
         * @param vehicles The specified model.
         */
        public void setVehiclesList(String[] vehicles) {
            cmbVehicle.setModel(new DefaultComboBoxModel<>(vehicles));
        }

        /**
         * Sets a specified model of cmbTime.
         * @param timeList The specified model.
         */
        public void setTimeList(String[] timeList) {
            cmbTime.setModel(new DefaultComboBoxModel<>(timeList));
        }

        /**
         * Sets a specified model of cmbDate.
         * @param dateList The specified model.
         */
        public void setDateList(String[] dateList) {
            cmbDate.setModel(new DefaultComboBoxModel<>(dateList));
        }

        /**
         * Retrieves the current JComboBox of cmbTime.
         * @return The current cmbTime.
         */
        public String getStartTime() {
            return cmbTime.getItemAt(cmbTime.getSelectedIndex());
        }

        /**
         * Retrieves the current JComboBox of cmbDate.
         * @return The current cmbDate.
         */
        public String getDateChosen() {
            return cmbDate.getItemAt(cmbDate.getSelectedIndex());
        }

        /**
         * Retrieves the current JComboBox of cmbDuration.
         * @return The current cmbDuration.
         */
        public String getDurationChosen() {
            return String.valueOf(cmbDuration.getSelectedIndex());
        }

        /**
         * Resets the duration to the first index.
         */
        public void resetDuration() {
            cmbDuration.setSelectedIndex(0);
        }

        /**
         * Resets the time to the first index.
         */
        public void resetTime() {
            try {
                cmbTime.setSelectedIndex(0);
            } catch (IllegalArgumentException e) {
                String[] empty = {"Unavailable"};
                cmbTime.setModel(new DefaultComboBoxModel<>(empty));
            }
        }

        /**
         * Resets the date to the first index.
         */
        public void resetDate() {
            cmbDate.setSelectedIndex(0);
        }

        /**
         * Sets a specified action listener for cmbDuration.
         * @param listener The specified action listener.
         */
        public void setDurationListener(ActionListener listener) {
            cmbDuration.addActionListener(listener);
        }

        /**
         * Sets a specified action listener for cmbDate.
         * @param listener The specified action listener.
         */
        public void setDateListener(ActionListener listener) {
            cmbDate.addActionListener(listener);
        }

        /**
         * Sets a specified model for the cmbDuration.
         * @param durationList The specified model.
         */
        public void setDurationList(String[] durationList) {
            cmbDuration = new JComboBox<>(durationList);
        }
    }

    /**
     * The dialog for successful bookings.
     */
    public class ReserveSlotConfirmationView extends JDialog {
        /**
         * The stylesheet.
         */
        private Resources res = new Resources();
        /**
         * The label for reserved.
         */
        private JLabel lblReserved;
        /**
         * The label for confirmation message.
         */
        private JLabel lblConfirmationMsg;
        /**
         * The button of closing the dialog.
         */
        private JButton btnCloseConfirmation;

        /**
         * Constructs a dialog of ReserveSlotConfirmationView.
         * @param success True if successful, false if otherwise.
         */
        public ReserveSlotConfirmationView(boolean success) {

            this.setTitle("PARCS");
            this.setModal(true);
            this.setLayout(new GridLayout(3, 1));

            // Create pnlIcon panel
            JPanel pnlIcon = new JPanel();
            pnlIcon.setLayout(new BorderLayout());
            pnlIcon.setPreferredSize(new Dimension(600, 200));

            // Create and set ImageIcon for pnlIcon
            ImageIcon iconAvailableCar;
            if (success)
                iconAvailableCar = res.iconSuccess; //change icon
            else {
                iconAvailableCar = res.iconError;
            }
            pnlIcon.add(new JLabel(iconAvailableCar), BorderLayout.CENTER);

            // Create PnlConfirmation panel
            JPanel pnlConfirmation = new JPanel(new GridBagLayout());
            pnlConfirmation.setPreferredSize(new Dimension(600, 150));

            if (success) {
                // Create label for PnlConfirmation with font based on resources class
                lblReserved = res.createLblH1("RESERVED!", res.celadon);
                lblConfirmationMsg = res.createLblP("You successfully created your reservation. Thank you!", res.eerieBlack);
            } else {
                lblReserved = res.createLblH1("FAILED!", res.red);
                lblConfirmationMsg = res.createLblP("No booking two parking spots at the same time. Thank you!", res.eerieBlack);
            }

            // Add labels to PnlConfirmation panel
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            pnlConfirmation.add(lblReserved, gbc);

            gbc.gridy = 1;
            pnlConfirmation.add(lblConfirmationMsg, gbc);

            // Create pnlCloseBtn panel
            JPanel pnlCloseBtn = new JPanel(new FlowLayout());
            pnlCloseBtn.setPreferredSize(new Dimension(600, 50));

            // Close button
            btnCloseConfirmation = res.createBtnRounded("CLOSE", res.celadon, res.eerieBlack, 10);

            pnlCloseBtn.add(btnCloseConfirmation);

            this.add(pnlIcon);
            this.add(pnlConfirmation);
            this.add(pnlCloseBtn);

            this.setPreferredSize(new Dimension(500, 300));
            this.setResizable(false);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }

        /**
         * Sets a specified action listener for btnCloseConfirmation.
         * @param listener The specified action listener.
         */
        public void setBtnCloseConfirmationListener(ActionListener listener) {
            btnCloseConfirmation.addActionListener(listener);
        }
    }

    /**
     * The dialog for errors.
     */
    public class ErrorMessageView extends JDialog {
        /**
         * The stylesheet.
         */
        private Resources res = new Resources();
        /**
         * The label for error.
         */
        private JLabel lblError;
        /**
         * The label for specified error.
         */
        private JLabel lblSpecificError;
        /**
         * The button for okay.
         */
        private JButton btnOkay;

        /**
         * Constructs a dialog of ErrorMessageView.
         */
        public ErrorMessageView() {
            this.setTitle("PARCS Error Message");
            this.setModal(true);
            this.setLayout(new GridLayout(3, 1));

            // Create pnlIcon panel
            JPanel pnlIcon = new JPanel();
            pnlIcon.setLayout(new BorderLayout());
            pnlIcon.setPreferredSize(new Dimension(600, 200));

            // Create and set ImageIcon for pnlIcon
            ImageIcon iconAvailableCar = res.iconError;
            pnlIcon.add(new JLabel(iconAvailableCar), BorderLayout.CENTER);

            // Create pnlErrorMessage panel
            JPanel pnlErrorMessage = new JPanel(new GridBagLayout());
            pnlErrorMessage.setPreferredSize(new Dimension(600, 150));

            // Labels for pnlErrorMessage
            lblError = res.createLblH1("ERROR", res.red);
            lblSpecificError = res.createLblP("Please complete selection before reserving.", res.eerieBlack);

            // Add labels to pnlErrorMessage panel
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            pnlErrorMessage.add(lblError, gbc);

            gbc.gridy = 1;
            pnlErrorMessage.add(lblSpecificError, gbc);

            // Create pnlButton panel
            JPanel pnlButton = new JPanel(new FlowLayout());
            pnlButton.setPreferredSize(new Dimension(600, 50));

            // Okay button
            btnOkay = res.createBtnRounded("OKAY", res.celadon, res.eerieBlack, 10);

            pnlButton.add(btnOkay);

            this.add(pnlIcon);
            this.add(pnlErrorMessage);
            this.add(pnlButton);

            this.setPreferredSize(new Dimension(500, 300));
            this.setResizable(false);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }

        /**
         * Mutates the error message.
         * @param errorMessage The new message.
         */
        public void setErrorMessage(String errorMessage) {
            lblSpecificError.setText(errorMessage);
        }
    }

    /**
     * Retrieves the current panel of parkingSlotButtonsView.
     * @return The current parkingSlotButtonsView.
     */
    public ParkingSlotButtonsView getParkingSlotButtonsView() {
        return parkingSlotButtonsView;
    }

    /**
     * Retrieves the current panel of reserveSlotConfirmationView.
     * @param booked True if booked, false if otherwise.
     * @return The current reserveSlotConfirmationView.
     */
    public ReserveSlotConfirmationView getReserveSlotConfirmationView(boolean booked) {
        reserveSlotConfirmationView = new ReserveSlotConfirmationView(booked);
        return reserveSlotConfirmationView;
    }

    /**
     * Retrieves the current dialog of error message.
     * @return The current errorMessageView.
     */
    public ErrorMessageView getErrorMessageView() {
        if (errorMessageView == null) {
            errorMessageView = new ErrorMessageView();
        }
        return errorMessageView;
    }

    /**
     * Retrieves the current panel that holds different components.
     * @return The current pnlCards.
     */
    public JPanel getPnlCards() {
        return pnlCards;
    }

    /**
     * Retrieves the current JButton of btnAvailCar.
     * @return The current btnAvailCar.
     */
    public JButton getBtnAvailCar() {
        return btnAvailCar;
    }

    /**
     * Retrieves the current JButton of btnAvailMotor.
     * @return The current btnAvailMotor.
     */
    public JButton getBtnAvailMotor() {
        return btnAvailMotor;
    }

    /**
     * Retrieves the current JButton of btnTotalBookings.
     * @return The current btnTotalBookings.
     */
    public JButton getBtnTotalBookings() {
        return btnTotalBookings;
    }

    /**
     * Retrieves the current JTextField of txtSearchBar.
     * @return The current txtSearchBar.
     */
    public JTextField getTxtSearchbar() {
        return txtSearchbar;
    }

    /**
     * Retrieves the current JLabel of lblName.
     * @return The current lblName.
     */
    public JLabel getLblName() {
        return lblName;
    }

    /**
     * Mutates the full name of the user.
     * @param fullName The new full name.
     */
    public void setUserFullName(String fullName) {
        lblName.setText("Hello, " + fullName + "!");
    }

    /**
     * Retrieves the current JLabel of lblDate.
     * @return The current lblDate.
     */
    public JLabel getLblDate() {
        return lblDate;
    }

    /**
     * Retrieves the current top card layout that controls the components of mainTopPanel.
     * @return The current topCardLayout.
     */
    public CardLayout getTopCardLayout() {
        return topCardLayout;
    }

    /**
     * Retrieves the current MainBottomPanel of mainBottomPanel.
     * @return The current mainBottomPanel.
     */
    public MainBottomPanel getMainBottomPanel() {
        return mainBottomPanel;
    }

    /**
     * Retrieves the current MainTopPanel of mainTopPanel.
     * @return The current mainTopPanel.
     */
    public MainTopPanel getMainTopPanel() {
        return mainTopPanel;
    }
}