package client.view;

import utilities.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Template for object VehicleAdderView.
 * The VehicleAdderView is the page to add the user's vehicles of the client application.
 */
public class VehicleAdderView extends JFrame {
    /**
     * The text field for the vehicle type.
     */
    private JComboBox<?> cmbType;
    /**
     * The text field for the vehicle model.
     */
    private JTextField txtModel;
    /**
     * The text field for the vehicle plate number.
     */
    private JTextField txtPlateNumber;
    /**
     * The button to add and confirm the vehicle being added.
     */
    private JButton btnAddVehicle;
    /**
     * The button to cancel and close the frame.
     */
    private JButton btnCancel;
    /**
     * The stylesheet.
     */
    private Resources res = new Resources();

    /**
     * Constructs the VehicleAdderView frame.
     */
    public VehicleAdderView() {
        super("Add Vehicle");

        // Body panel acting as a container to hold all UI components
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());

        // GridBagConstraints to position components using the GB layout
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 40;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        JLabel lblTitle = res.createLblH1("Add Vehicle", res.eerieBlack);
        contentPane.add(lblTitle);

        gbc.gridy = 1;
        gbc.ipady = 3;
        cmbType = res.createCmbRounded(res.lightGray,res.eerieBlack,0);
        contentPane.add(cmbType, gbc);

        gbc.gridy = 2;
        gbc.ipady = 3;
        txtModel = res.createTxtRounded("Vehicle Model (e.g., Honda Civic)", res.white, res.gray, 30);
        contentPane.add(txtModel, gbc);

        gbc.gridy = 3;
        gbc.ipady = 3;
        txtPlateNumber = res.createTxtRounded("License Plate Number (e.g., NWA 991)", res.white,
                res.gray, 30);
        contentPane.add(txtPlateNumber, gbc);

        gbc.insets = new Insets(10,10,10,10);

        gbc.gridy = 4;
        JPanel pnlButtons = new JPanel(new FlowLayout());
        contentPane.add(pnlButtons, gbc);

        btnAddVehicle = res.createBtnRounded("Add Vehicle",res.celadon, res.eerieBlack,10);
        pnlButtons.add(btnAddVehicle);

        btnCancel = res.createBtnRounded("Cancel",res.gray, res.eerieBlack,10);
        pnlButtons.add(btnCancel);

        this.setContentPane(contentPane);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setSize(600,400);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Sets a specified action listener for btnAddVehicle.
     * @param actionListener The specified action listener.
     */
    public void setAddVehicleListener(ActionListener actionListener) {
        btnAddVehicle.addActionListener(actionListener);
    }

    /**
     * Sets a specified action listener for btnCancel.
     * @param actionListener The specified action listener.
     */
    public void setCancelListener(ActionListener actionListener) {
        btnCancel.addActionListener(actionListener);
    }

    /**
     * Retrieves the current JComboBox of cmbType.
     * @return The current cmbType.
     */
    public JComboBox<?> getCmbType() {
        return cmbType;
    }

    /**
     * Retrieves the current JTextField of txtModel.
     * @return The current txtModel.
     */
    public JTextField getTxtModel() {
        return txtModel;
    }

    /**
     * Retrieves the current JTextField of txtPlateNumber.
     * @return The current txtPlateNumber.
     */
    public JTextField getTxtPlateNumber() {
        return txtPlateNumber;
    }

    /**
     * Retrieves the current JComboBox of cmbType.
     * @return The current cmbType.
     */
    public String getVehicleType() {
        return cmbType.getSelectedItem().toString();
    }

    /**
     * Retrieves the current JTextField of txtModel.
     * @return The current txtModel.
     */
    public String getModel() {
        return txtModel.getText();
    }

    /**
     * Retrieves the current JTextField of txtPlateNumber.
     * @return The current txtPlateNumber.
     */
    public String getPlateNumber() {
        return txtPlateNumber.getText();
    }

    /**
     * Retrieves the current JButton of btnAddVehicle.
     * @return The current btnAddVehicle.
     */
    public JButton getBtnAddVehicle() {
        return btnAddVehicle;
    }

    /**
     * Retrieves the current JButton of btnCancel.
     * @return The current btnCancel.
     */
    public JButton getBtnCancel() {
        return btnCancel;
    }
}
