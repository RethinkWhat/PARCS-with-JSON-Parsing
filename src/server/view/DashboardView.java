package server.view;

import utilities.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The panel of DashboardView.
 * Holds all the information of the car and motor bookings and number of each booking.
 */
public class DashboardView extends JPanel {
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
     * The full name of the current admin.
     */
    private JLabel lblName;
    /**
     * The button to refresh the lists of car bookings and motor bookings.
     */
    private JButton btnRefresh;
    /**
     * The stylesheet.
     */
    private static Resources res = new Resources();
    /**
     * Instance variable of GridBagConstraints used for JPanels using GridBagLayout.
     */
    private GridBagConstraints gbc;
    /**
     * The CardLayout that controls the components of the MainTopPanel.
     */
    private CardLayout topCardLayout = new CardLayout();
    /**
     * The top panel of the main page.
     */
    private MainTopPanel pnlMainTop;
    /**
     * The bottom panel of the main page.
     */
    private MainBottomPanel pnlMainBottom;

    /**
     * Constructs a panel of DashboardView.
     */
    public DashboardView() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(25,25,25,25));

        // Main Panel
        add(pnlMainTop = new MainTopPanel(), BorderLayout.NORTH);
        add(pnlMainBottom = new MainBottomPanel(), BorderLayout.SOUTH);

        this.setPreferredSize(new Dimension(1100,700));
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
     * Retrieves the current btnTot
     * @return
     */
    public JButton getBtnTotalBookings() {
        return btnTotalBookings;
    }

    public MainTopPanel getPnlMainTop() {
        return pnlMainTop;
    }

    public MainBottomPanel getPnlMainBottom() {
        return pnlMainBottom;
    }

    /**
     * Retrieves the current JButton of btnRefresh.
     * @return The current btnRefresh.
     */
    public JButton getBtnRefresh() {
        return btnRefresh;
    }

    /**
     * Sets a specified action listener for btnRefresh.
     * @param actionListener The specified action listener.
     */
    public void setRefreshListener(ActionListener actionListener) {
        btnRefresh.addActionListener(actionListener);
    }

    /**
     * The panel that contains the parking slots.
     */
    public class MainBottomPanel extends JPanel {
        /**
         * The panel that holds the different components.
         */
        private JPanel container;
        /**
         * The text field for the booking date.
         */
        private JTextField txtDate;
        /**
         * The combo box for the status of the booking.
         * All, completed, and future.
         */
        private JComboBox<String> cmbStatus;
        /**
         * The combo box for the type of vehicle of the booking.
         * All, Car, Motorcycle.
         */
        private JComboBox<String> cmbVehicleType;
        /**
         * The button to apply the filters.
         */
        private JButton btnApply;
        /**
         * The button to reset the filters.
         */
        private JButton btnClear;
        /**
         * The table to display the bookings
         */
        private JTable tblBookings;
        /**
         * The table model to hold the columns of tblBookings.
         */
        private DefaultTableModel tblModel;
        /**
         * The scroll pane where the table will be instantiated.
         */
        private JScrollPane scrollPane;

        /**
         * Constructs a panel of MainBottomPanel.
         */
        public MainBottomPanel() {
            setLayout(new BorderLayout());
            setBackground(res.lightGray);

            container = res.createPnlRounded(1300,510,res.white, res.lightGray);
            container.setBorder(new EmptyBorder(10,10,10,10));
            container.setPreferredSize(new Dimension(1300,510));
            container.setBackground(res.lightGray);
            add(container, BorderLayout.CENTER);

            ButtonsPanel pnlButtons = new ButtonsPanel();
            container.add(pnlButtons, BorderLayout.NORTH);

            TablePanel pnlTable = new TablePanel();
            container.add(pnlTable, BorderLayout.SOUTH);

            setPreferredSize(new Dimension(1300, 510));
            setVisible(true);
        }

        /**
         * Panel to hold buttons, text field, and combo boxes.
         */
        class ButtonsPanel extends JPanel {
            /**
             * Constructs a panel of ButtonsPanel.
             */
            public ButtonsPanel() {
                setLayout(new FlowLayout(FlowLayout.LEFT));
                setBorder(new EmptyBorder(10,20,10,20));
                setBackground(res.white);

                txtDate = res.createTxtRounded("Search Date (MM/DD/YY)", res.lightGray, res.gray, 25);
                add(txtDate);

                cmbStatus = new JComboBox<>(new String[]{"Select Status:", "All", "Completed", "Current", "Future"});
                cmbStatus.setPreferredSize(new Dimension(200,40));
                cmbStatus.setFont(new Font("Arial", Font.BOLD, 16));
                add(cmbStatus);

                cmbVehicleType = new JComboBox<>(new String[]{"Select Vehicle Type:", "All", "Car", "Motorcycle"});
                cmbVehicleType.setPreferredSize(new Dimension(280,40));
                cmbVehicleType.setFont(new Font("Arial", Font.BOLD, 16));
                add(cmbVehicleType);

                btnApply = res.createBtnRounded("Apply Filters", res.celadon, res.eerieBlack, 10);
                add(btnApply);

                btnClear = res.createBtnRounded("Clear Filters", res.lightGray, res.eerieBlack, 10);
                add(btnClear);

                this.setPreferredSize(new Dimension(1200,100));
            }
        }

        /**
         * Panel to hold the table containing the data of bookings.
         */
        class TablePanel extends JPanel {
            /**
             * Constructs a panel of TablePanel.
             */
            public TablePanel() {
                setLayout(new BorderLayout());
                setBackground(res.white);

                tblModel = new DefaultTableModel();
                tblModel.addColumn("Date");
                tblModel.addColumn("Username");
                tblModel.addColumn("Spot");
                tblModel.addColumn("Vehicle Type");
                tblModel.addColumn("Check-in Time");
                tblModel.addColumn("Check-out Time");
                tblModel.addColumn("Duration");

                tblBookings = new JTable(tblModel);
                tblBookings.getTableHeader().setResizingAllowed(false);
                tblBookings.getTableHeader().setReorderingAllowed(false);
                tblBookings.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
                tblBookings.setAutoResizeMode(0);
                tblBookings.setDragEnabled(false);
                tblBookings.setOpaque(false);
                tblBookings.setFillsViewportHeight(true);
                tblBookings.setPreferredSize(new Dimension(1100,1000));

                tblBookings.getColumnModel().getColumn(0).setPreferredWidth(100);
                tblBookings.getColumnModel().getColumn(1).setPreferredWidth(300);
                tblBookings.getColumnModel().getColumn(2).setPreferredWidth(150);
                tblBookings.getColumnModel().getColumn(3).setPreferredWidth(200);
                tblBookings.getColumnModel().getColumn(4).setPreferredWidth(150);
                tblBookings.getColumnModel().getColumn(5).setPreferredWidth(150);
                tblBookings.getColumnModel().getColumn(6).setPreferredWidth(100);
                tblBookings.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                scrollPane = new JScrollPane(tblBookings);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                add(scrollPane, BorderLayout.CENTER);

                this.setPreferredSize(new Dimension(1150, 370));
            }
        }

        /**
         * Retrieves the current JTextField of txtDate.
         * @return The current txtDate.
         */
        public JTextField getTxtDate() {
            return txtDate;
        }

        /**
         * Retrieves the current JComboBox of cmbStatus.
         * @return The current cmbStatus.
         */
        public JComboBox<?> getCmbStatus() {
            return cmbStatus;
        }

        /**
         * Retrieves the current JComboBox of cmbVehicleType.
         * @return The current cmbVehicleType.
         */
        public JComboBox<?> getCmbVehicleType() {
            return cmbVehicleType;
        }

        /**
         * Retrieves the current JButton of btnApply.
         * @return The current btnApply.
         */
        public JButton getBtnApply() {
            return btnApply;
        }

        /**
         * Retrieves the current JButton of btnClear.
         * @return The current btnClear.
         */
        public JButton getBtnClear() {
            return btnClear;
        }

        /**
         * Retrieves the current JTable of tblBookings.
         * @return The current tblBookings.
         */
        public JTable getTblBookings() {
            return tblBookings;
        }

        /**
         * Retrieves the current DefaultTableModel of tblModel.
         * @return The current tblModel.
         */
        public DefaultTableModel getTblModel() {
            return tblModel;
        }

        /**
         * Retrieves the current JScrollPane of scrollPane.
         * @return The current scrollPane.
         */
        public JScrollPane getScrollPane() {
            return scrollPane;
        }

        /**
         * Sets a specified action listener for btnApply.
         * @param actionListener The specified action listener.
         */
        public void setApplyListener(ActionListener actionListener) {
            btnApply.addActionListener(actionListener);
        }

        /**
         * Sets a specified action listener for btnClear.
         * @param actionListener The specified action listener.
         */
        public void setClearListener(ActionListener actionListener) {
            btnClear.addActionListener(actionListener);
        }
    }

    /**
     * The panel that contains multiple objects of the ButtonsPanel.
     */
    public class MainTopPanel extends JPanel {
        /**
         * The label for the total car bookings.
         */
        private JLabel lblCarCount;
        /**
         * The label for car.
         */
        private JLabel lblAvailCar;
        /**
         * The label for the total motor bookings.
         */
        private JLabel lblMotorCount;
        /**
         * The label for motor.
         */
        private JLabel lblAvailMotor;
        /**
         * The label for the total bookings (car and motor).
         */
        private JLabel lblTotalCount;
        /**
         * The label for total
         */
        private JLabel lblTotal;
        /**
         * Constructs a panel of MainTopPanel.
         */
        public MainTopPanel() {
            setBackground(res.lightGray);
            setLayout(new BorderLayout());

            JPanel pnlInformation = new JPanel(new GridBagLayout());
            pnlInformation.setPreferredSize(new Dimension(1300,50));
            add(pnlInformation, BorderLayout.NORTH);
            pnlInformation.setBackground(res.lightGray);

            gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.ipadx = 900;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            lblName = res.createLblH2("Hello, " + "Admin"+ "!", res.eerieBlack);
            pnlInformation.add(lblName, gbc);

            gbc.gridx = 1;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.EAST;
            btnRefresh = res.createBtnIconOnly(res.iconRefresh, 30,30);
            pnlInformation.add(btnRefresh, gbc);


            GridLayout gridLayout = new GridLayout(0,3);
            gridLayout.setHgap(10);

            JPanel pnlButtons = new JPanel(gridLayout);
            pnlButtons.setPreferredSize(new Dimension(1300,100));
            pnlButtons.setBorder(new EmptyBorder(10,0,10,0));
            pnlButtons.setBackground(res.lightGray);
            add(pnlButtons, BorderLayout.SOUTH);


            // Update parameters as models from controller when backend has commenced.
            ButtonPanel pnlAvailCar = new ButtonPanel(
                    btnAvailCar = res.createBtnIconOnly(res.iconSolidCar, 50, 50),
                    lblCarCount = res.createLblH1("13", res.eerieBlack),
                    lblAvailCar = res.createLblP("<html>Total Car<br> Bookings</html>", res.eerieBlack)
            );
            pnlButtons.add(pnlAvailCar);

            ButtonPanel pnlAvailMotor = new ButtonPanel(
                    btnAvailMotor = res.createBtnIconOnly(res.iconSolidMotor, 50, 50),
                    lblMotorCount = res.createLblH1("10", res.eerieBlack),
                    lblAvailMotor = res.createLblP("<html>Total Motor<br> Bookings</html>", res.eerieBlack)
            );
            pnlButtons.add(pnlAvailMotor);

            ButtonPanel pnlTotalBookings = new ButtonPanel(
                    btnTotalBookings = res.createBtnIconOnly(res.iconSolidTicket, 50,50),
                    lblTotalCount = res.createLblH1("3", res.eerieBlack),
                    lblTotal = res.createLblP("<html>Total<br> Bookings</html>", res.eerieBlack)
            );
            pnlButtons.add(pnlTotalBookings);

            this.setPreferredSize(new Dimension(1300,150));
        }

        /**
         * Retrieves the current JLabel of lblCarCount.
         * @return The current lblCarCount.
         */
        public JLabel getLblCarCount() {
            return lblCarCount;
        }

        /**
         * Retrieves the current JLabel of lblMotorCount.
         * @return The current lblMotorCount.
         */
        public JLabel getLblMotorCount() {
            return lblMotorCount;
        }

        /**
         * Retrieves the current JLabel of lblTotalCount.
         * @return The current lblTotalCount.
         */
        public JLabel getLblTotalCount() {
            return lblTotalCount;
        }

        /**
         * Retrieves the current JLabel of lblAvailCar.
         * @return The current lblAvailCar.
         */
        public JLabel getLblAvailCar() {
            return lblAvailCar;
        }

        /**
         * Retrieves the current JLabel of lblTotal.
         * @return The current lblTotal.
         */
        public JLabel getLblTotal() {
            return lblTotal;
        }
    }

    /**
     * The panel that creates the useful buttons of the home page.
     */
    public class ButtonPanel extends JPanel {
        /**
         * The rounded panel.
         */
        private JPanel container;

        /**
         * Constructs a panel with a specified button, number label, and title label.
         */
        public ButtonPanel(JButton button, JLabel number, JLabel title) {
            setLayout(new BorderLayout());

            container = res.createPnlRounded(100, 80, res.white, res.lightGray);
            container.setLayout(new GridLayout(0, 3));
            add(container, BorderLayout.NORTH);

            container.add(button);
            container.add(number);
            container.add(title);

            this.setPreferredSize(new Dimension(100, 100));
        }
    }

    public static void main(String[] args) {
        new AdminApplicationView();
    }
}
