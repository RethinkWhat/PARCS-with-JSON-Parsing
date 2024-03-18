package client.view;

import utilities.Resources;

import javax.swing.*;
import java.awt.*;

/**
 * An error message that displays when the server is offline.
 */
public class ServerOfflineDialog extends JFrame {
    /**
     * Constructs a JDialog of ServerOfflineDialog.
     */
    public ServerOfflineDialog() {
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
        ImageIcon iconAvailableCar = res.iconOffline;
        pnlIcon.add(new JLabel(iconAvailableCar), BorderLayout.CENTER);

        // Create pnlServerClosed panel
        JPanel pnlServerClosed = new JPanel(new GridBagLayout());
        pnlServerClosed.setPreferredSize(new Dimension(600, 170));

        // Labels for the pnlServerClosed
        JLabel lblServerMsg = res.createLblH1("SERVER IS CLOSED", res.red);
        JLabel lblClosedMsg = res.createLblP("Unable to connect to server. Please try again later.", res.eerieBlack);

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
        JButton btnExit = res.createBtnRounded("EXIT", res.red, res.eerieBlack, 10);

        btnExit.addActionListener(e -> {
            System.exit(0);
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
}
