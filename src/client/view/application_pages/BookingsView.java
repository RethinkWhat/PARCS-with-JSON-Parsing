package client.view.application_pages;

import utilities.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class represents the main panel for displaying bookings.
 * It contains a card layout to switch between different views, such as filtering and displaying bookings.
 */
public class BookingsView extends JPanel {
    private JPanel pnlCards;
    private JPanel container;
    private Resources res = new Resources();
    private CardLayout cardLayout;
    private BookingPanel bookingPanel;

    /**
     * Constructs the BookingsView panel with a card layout to switch between different views.
     */
    public BookingsView() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(25, 25, 25, 25));
        setBackground(res.lightGray);

        container = res.createPnlRounded(950, 700, res.white, res.lightGray);
        container.setBorder(new EmptyBorder(10, 25, 10, 25));
        container.setLayout(new BorderLayout());
        add(container, BorderLayout.CENTER);

        cardLayout = new CardLayout();

        pnlCards = new JPanel(cardLayout);
        pnlCards.setBackground(res.red);
        pnlCards.setPreferredSize(new Dimension(950, 700));
        container.add(pnlCards, BorderLayout.CENTER);

        bookingPanel = new BookingPanel();
        pnlCards.add(bookingPanel);

        this.setPreferredSize(new Dimension(950, 700));
    }

    /**
     * Retrieves the BookingPanel instance contained within this BookingsView.
     *
     * @return The BookingPanel instance.
     */
    public BookingPanel bookingPanel() {
        return bookingPanel;
    }

    /**
     * The main method to launch the BookingsView application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookingsView bookingsView = new BookingsView();

            JFrame frame = new JFrame("Bookings View Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(bookingsView);

            frame.setSize(950, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /**
     * Represents the panel for filtering and displaying bookings.
     */
    public class BookingPanel extends JPanel {
        private FilterPanel filterPanel;
        private TablePanel tablePanel;

        /**
         * Constructs the BookingPanel containing filter and table panels.
         */
        public BookingPanel() {
            setLayout(new BorderLayout());
            setBackground(res.white);

            filterPanel = new FilterPanel();
            add(filterPanel, BorderLayout.NORTH);

            tablePanel = new TablePanel();
            add(tablePanel, BorderLayout.SOUTH);

            setPreferredSize(new Dimension(850, 700));
            setVisible(true);
        }

        /**
         * Retrieves the FilterPanel instance contained within this BookingPanel.
         *
         * @return The FilterPanel instance.
         */
        public FilterPanel getFilterPanel() {
            return filterPanel;
        }

        /**
         * Retrieves the TablePanel instance contained within this BookingPanel.
         *
         * @return The TablePanel instance.
         */
        public TablePanel getTablePanel() {
            return tablePanel;
        }
    }

    /**
     * Represents the panel for filtering bookings.
     */
    public class FilterPanel extends JPanel {
        private JTextField txtDate;
        private JComboBox<String> cmbStatus;
        private JComboBox<String> cmbVehicleType;
        private JButton btnApply;
        private JButton btnClear;

        /**
         * Constructs the FilterPanel with a BorderLayout containing top and bottom filter panels.
         */
        public FilterPanel() {
            setLayout(new BorderLayout());
            setBackground(res.white);

            // Top Filter Panel
            JPanel topFilterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            topFilterPanel.setBorder(new EmptyBorder(15, 20, 0, 20));
            topFilterPanel.setBackground(res.white);

            txtDate = res.createTxtRounded("Search Date (MM/DD/YY)", res.lightGray, res.gray, 20);
            topFilterPanel.add(txtDate);

            cmbStatus = new JComboBox<>(new String[]{"Select Status:", "All", "Completed", "Current", "Future"});
            cmbStatus.setPreferredSize(new Dimension(200, 30));
            cmbStatus.setFont(new Font("Arial", Font.BOLD, 16));
            topFilterPanel.add(cmbStatus);

            cmbVehicleType = new JComboBox<>(new String[]{"Select Vehicle Type:", "All", "Car", "Motorcycle"});
            cmbVehicleType.setPreferredSize(new Dimension(200, 30));
            cmbVehicleType.setFont(new Font("Arial", Font.BOLD, 16));
            topFilterPanel.add(cmbVehicleType);

            add(topFilterPanel, BorderLayout.NORTH);

            // Bottom Filter Panel
            JPanel bottomFilterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            bottomFilterPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            bottomFilterPanel.setBackground(res.white);

            btnApply = res.createBtnRounded("Apply Filters", res.celadon, res.eerieBlack, 10);
            bottomFilterPanel.add(btnApply);

            btnClear = res.createBtnRounded("Clear Filters", res.lightGray, res.eerieBlack, 10);
            bottomFilterPanel.add(btnClear);

            add(bottomFilterPanel, BorderLayout.CENTER);

            this.setPreferredSize(new Dimension(800, 130));
        }

        /**
         * Retrieves the JTextField instance for entering search date.
         *
         * @return The JTextField instance.
         */
        public JTextField getTxtDate() {
            return txtDate;
        }

        /**
         * Retrieves the JComboBox instance for selecting status.
         *
         * @return The JComboBox instance.
         */
        public JComboBox<String> getCmbStatus() {
            return cmbStatus;
        }

        /**
         * Retrieves the JComboBox instance for selecting vehicle type.
         *
         * @return The JComboBox instance.
         */
        public JComboBox<String> getCmbVehicleType() {
            return cmbVehicleType;
        }

        /**
         * Retrieves the JButton instance for applying filters.
         *
         * @return The JButton instance.
         */
        public JButton getBtnApply() {
            return btnApply;
        }

        /**
         * Retrieves the JButton instance for clearing filters.
         *
         * @return The JButton instance.
         */
        public JButton getBtnClear() {
            return btnClear;
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
     * Represents the panel for displaying bookings in a table.
     */
    public class TablePanel extends JPanel {
        private JTable bookingTable;
        private JScrollPane scrollPane;

        /**
         * Constructs the TablePanel with a BorderLayout containing a table for displaying bookings.
         */
        public TablePanel() {
            setLayout(new BorderLayout());
            setBackground(res.white);


            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Vehicle Type");
            model.addColumn("Spot");
            model.addColumn("Date");
            model.addColumn("Check-in Time");
            model.addColumn("Check-out Time");

            bookingTable = new JTable(model);
            bookingTable.getTableHeader().setResizingAllowed(false);
            bookingTable.getTableHeader().setReorderingAllowed(false);
            bookingTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
            bookingTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            bookingTable.setDragEnabled(false);
            bookingTable.setOpaque(false);
            bookingTable.setFillsViewportHeight(true);
            bookingTable.setPreferredSize(new Dimension(800, 650));

            bookingTable.getColumnModel().getColumn(0).setPreferredWidth(150);
            bookingTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            bookingTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            bookingTable.getColumnModel().getColumn(3).setPreferredWidth(200);
            bookingTable.getColumnModel().getColumn(4).setPreferredWidth(200);

            scrollPane = new JScrollPane(bookingTable);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            add(scrollPane, BorderLayout.CENTER);

            this.setPreferredSize(new Dimension(800, 350));
        }

        /**
         * Retrieves the JTable instance for displaying bookings.
         *
         * @return The JTable instance.
         */
        public JTable getBookingTable() {
            return bookingTable;
        }

        /**
         * Retrieves the current JScrollPane of scrollPane.
         * @return The current scrollPane.
         */
        public JScrollPane getScrollPane() {
            return scrollPane;
        }

        /**
         * Retrieves the DefaultTableModel instance associated with the booking table.
         *
         * @return The DefaultTableModel instance.
         */
        public DefaultTableModel getTableModel() {
            return (DefaultTableModel) bookingTable.getModel();
        }
    }
}