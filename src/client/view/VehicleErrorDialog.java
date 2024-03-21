package client.view;

import utilities.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Error message when a vehicle already exists (via plate number).
 */
public class VehicleErrorDialog extends JFrame {
    /**
     * Exit button
     */
    private JButton btnExit;
    /**
     * Constructs a VehicleErrorDialog
     */
    public VehicleErrorDialog() {
        JFrame mainFrame = new JFrame();
        JDialog dialog = new JDialog(mainFrame, "PARCS", true);
        dialog.setTitle("PARCS");
        dialog.setLayout(new GridLayout(3, 1));
        dialog.setSize(500, 300);

        // Create pnlIcon panel
        JPanel pnlIcon = new JPanel();
        pnlIcon.setLayout(new BorderLayout());
        pnlIcon.setPreferredSize(new Dimension(600, 200));

        // Create and set ImageIcon for pnlIcon
        Resources res = new Resources();
        ImageIcon iconAvailableCar = res.iconTakenCar;
        pnlIcon.add(new JLabel(iconAvailableCar), BorderLayout.CENTER);

        // Create pnlServerClosed panel
        JPanel pnlServerClosed = new JPanel(new GridBagLayout());
        pnlServerClosed.setPreferredSize(new Dimension(600, 170));

        // Labels for the pnlServerClosed
        JLabel lblServerMsg = res.createLblH1("VEHICLE EXISTS", res.red);
        JLabel lblClosedMsg = res.createLblP("Cannot add vehicle because plate number exists.", res.eerieBlack);

        // Add labels to pnlServerClosed panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlServerClosed.add(lblServerMsg, gbc);

        gbc.gridy = 1;
        pnlServerClosed.add(lblClosedMsg, gbc);

        // Create pnlExit panel
        JPanel pnlExit = new JPanel(new FlowLayout());
        pnlExit.setPreferredSize(new Dimension(600, 30));

        // Create the exit button
        btnExit = res.createBtnRounded("EXIT", res.red, res.eerieBlack, 10);

        btnExit.addActionListener(e -> {
            dialog.dispose();
            dialog.setVisible(false);
        });

        pnlExit.add(btnExit);

        // Add panels to the dialog
        dialog.add(pnlIcon);
        dialog.add(pnlServerClosed);
        dialog.add(pnlExit);

        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    /**
     * Sets a specified action listener for btnExit
     * @param actionListener The specified action listener.
     */
    public void setExitListener(ActionListener actionListener) {
        btnExit.addActionListener(actionListener);
    }
}
