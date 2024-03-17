package server.view;

import utilities.Resources;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Holds the server status.
 */
public class ServerStatusView extends JPanel {
    /**
     * The button of switching the server on or off.
     */
    private JButton btnServerSwitch;
    /**
     * The label of server status.
     */
    private JLabel lblServerPrompt;
    /**
     * The label of server status information.
     */
    private JLabel lblServerStatus;
    /**
     * Instance variable of GridBagConstraints used for JPanels using GridBagLayout.
     */
    private GridBagConstraints gbc;
    /**
     * The stylesheet.
     */
    private Resources res = new Resources();

    /**
     * Constructs a panel of ServerStatusView.
     */
    public ServerStatusView(){
        setLayout(new BorderLayout());
        setBackground(res.lightGray);
        setBorder(new EmptyBorder(25,25,25,25));

        MainPanel pnlMain = new MainPanel();
        add(pnlMain, BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(1100,700));
        this.setVisible(true);
    }

    /**
     * The panel that contains the buttons.
     */
    public class MainPanel extends JPanel{
        /**
         * Construct a panel of MainPanel.
         */
        public MainPanel(){
            setLayout(new BorderLayout());

            JPanel container = res.createPnlRounded(1300,650,res.white, res.lightGray);
            container.setLayout(new GridBagLayout());
            add(container, BorderLayout.CENTER);

            gbc = new GridBagConstraints();

            gbc.gridy = 0;
            lblServerPrompt = res.createLblH1("SERVER STATUS", res.eerieBlack);
            lblServerPrompt.setHorizontalAlignment(SwingConstants.CENTER);
            container.add(lblServerPrompt, gbc);

            //To be dynamically changed in the controller
            gbc.gridy = 1;
            lblServerStatus = res.createLblH1("OFFLINE",res.red);
            lblServerStatus.setHorizontalAlignment(SwingConstants.CENTER);
            container.add(lblServerStatus,gbc);

            gbc.gridy = 2;
            gbc.ipady = 10;
            btnServerSwitch = res.createBtnRounded("Start Server", res.white, res.celadon, 15);
            btnServerSwitch.setPreferredSize(new Dimension(200,50));
            container.add(btnServerSwitch,gbc);

            setPreferredSize(new Dimension(1300,650));
        }
    }

    /**
     * Retrieves the current JButton of btnServerSwitch.
     * @return The current btnServerSwitch.
     */
    public JButton getServerSwitch() {
        return btnServerSwitch;
    }

    /**
     * Retrieves the current JLabel of lblServerPrompt.
     * @return The current lblServerPrompt.
     */
    public JLabel getServerPrompt() {
        return lblServerPrompt;
    }

    /**
     * Retrieves the current JLabel of lblServerStatus.
     * @return The current lblServerStatus.
     */
    public JLabel getServerStatus() {
        return lblServerStatus;
    }

    /**
     * Sets a specified action listener for btnServerSwitch.
     * @param actionListener The specified action listener.
     */
    public void setServerListener(ActionListener actionListener){
        btnServerSwitch.addActionListener(actionListener);
    }

    /**
     * Changes the labels when the server is online.
     */
    public void setOnline(){
        lblServerStatus.setText("ONLINE");
        lblServerStatus.setForeground(res.celadon);

        btnServerSwitch.setText("Terminate Server");
        btnServerSwitch.setBackground(res.white);
        btnServerSwitch.setForeground(res.red);
    }

    /**
     * Changes the labels when the server is offline.
     */
    public void setOffline(){
        lblServerStatus.setText("OFFLINE");
        lblServerStatus.setForeground(res.red);
        btnServerSwitch.setText("Start Server");
        btnServerSwitch.setBackground(res.white);
        btnServerSwitch.setForeground(res.celadon);
    }
}

