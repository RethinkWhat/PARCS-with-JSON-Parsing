package client.view;

import utilities.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * TODO: Documentation
 */
public class LoginView extends JFrame {
    /**
     * The panel that holds different component panels.
     */
    private JPanel pnlCards;

    /**
     * The text field for the username in the login page.
     */
    private JTextField txtUsername;
    /**
     * The password field for the user's password in the login page.
     */
    private JPasswordField txtPassword;
    /**
     * The checkbox to show password in the login page.
     */
    private JCheckBox chkShowPassword;

    /**
     * The button to log the user in the system.
     */
    private JButton btnLogin;
    /**
     * The button to go to the signup page.
     */
    private JButton btnSignup;

    /**
     * The error message displayed in the login page.
     */
    private JLabel lblLoginErrorMessage;
    /**
     * Instance variable of resources to invoke stylesheet and UI resources.
     */
    private final Resources res = new Resources();
    /**
     * The GridBagConstraints to position components using GridBagLayout.
     */
    private GridBagConstraints gbc;
    /**
     * Control components of the Container by switching panels.
     */
    private CardLayout cardLayout = new CardLayout();

    /**
     * Constructs a RegisterView frame.
     */
    public LoginView() {
        super("PARCS");

        // Body panel acting as a container to hold all UI components
        Container contentArea = new JPanel(new BorderLayout());
        pnlCards = new JPanel(cardLayout);

        JPanel pnlLogin = new LoginPanel();
        pnlCards.add(pnlLogin, "login");

        contentArea.add(pnlCards, BorderLayout.CENTER);
        cardLayout.show(pnlCards, "login");

        this.setIconImage(res.logoParcs.getImage());
        this.setContentPane(contentArea);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setSize(950,560);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Contains the UI components for the login page.
     */
    class LoginPanel extends JPanel {
        /**
         * Constructs a LoginPanel panel.
         */
        public LoginPanel() {
            // Main Panel
            setLayout(new GridLayout(0,2));

            // GridBagConstraints to position components using the GB layout
            gbc = new GridBagConstraints();

            // Left Panel
            JPanel pnlLeft = new JPanel(new GridBagLayout());
            pnlLeft.setBackground(res.feldgrau);
            add(pnlLeft);

            gbc.gridy = 0;
            gbc.gridwidth = 1;
            JLabel lblLogo = new JLabel("");
            lblLogo.setIcon(res.logoParcs);
            pnlLeft.add(lblLogo, gbc);

            gbc.gridy = 1;
            JLabel lblTagline = res.createLblH2("Parking made easy.", res.white);
            pnlLeft.add(lblTagline, gbc);

            // Right Panel
            JPanel pnlRight = new JPanel(new GridBagLayout());
            pnlRight.setBackground(res.white);
            add(pnlRight);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.ipady = 40;
            gbc.insets = new Insets(5,0,5,0);
            JLabel lblTitle = res.createLblH1("Log In", res.eerieBlack);
            pnlRight.add(lblTitle, gbc);

            gbc.gridy = 3;
            gbc.ipady = 3;
            txtUsername = res.createTxtRounded("Username", res.lightGray, res.gray,20);
            pnlRight.add(txtUsername, gbc);

            gbc.gridy = 4;
            txtPassword = res.createPwdRounded(res.lightGray, res.gray, 20);
            txtPassword.setEchoChar((char)0);
            pnlRight.add(txtPassword, gbc);

            gbc.gridy = 7;
            lblLoginErrorMessage = res.createLblP("", res.red);
            pnlRight.add(lblLoginErrorMessage, gbc);

            gbc.gridy = 6;
            gbc.gridx = 0;
            chkShowPassword = new JCheckBox("Show Password");
            chkShowPassword.setFont(new Font("Arial", Font.PLAIN, 14));
            chkShowPassword.setHorizontalAlignment(SwingConstants.LEFT);
            chkShowPassword.setBackground(res.white);
            pnlRight.add(chkShowPassword, gbc);

            gbc.gridy = 8;
            gbc.ipady = 40;
            JPanel pnlButtons = new JPanel(new FlowLayout());
            pnlButtons.setBackground(res.white);
            pnlRight.add(pnlButtons, gbc);

            // !!! Buttons Panel components
            btnSignup = res.createBtnRounded("Sign Up", res.gray, res.eerieBlack, 10);
            pnlButtons.add(btnSignup);

            btnLogin = res.createBtnRounded("Log In", res.celadon, res.eerieBlack, 10);
            pnlButtons.add(btnLogin);

            this.setPreferredSize(new Dimension(950,560));
        }
    }


    /**
     * Sets a specified action listener for btnLogin.
     * @param loginListener The specified action listener.
     */
    public void setLoginListener(ActionListener loginListener) {
        btnLogin.addActionListener(loginListener);
    }

    /**
     * Sets a specified action listener for btnSignup.
     * @param signupListener The specified action listener.
     */
    public void setSignupListener(ActionListener signupListener) {
        btnSignup.addActionListener(signupListener);
    }


    /**
     * Sets a specified error message for lblLoginErrorMessage.
     * @param message The specified error.
     */
    public void displayLoginErrorMessage(String message) {
        lblLoginErrorMessage.setText(message);
    }




    /**
     * Retrieves the current username input in the txtUsername of the login page.
     * @return The current username input.
     */
    public String getUsername() {
        return txtUsername.getText();
    }

    /**
     * Retrieves the current password input in the txtPassword of the login page.
     * @return The current password input.
     */
    public String getPassword() {
        return txtPassword.getText();
    }

    /**
     * Retrieves the current card layout panel containing the login and signup panels.
     * @return The current card layout panel.
     */
    public JPanel getPnlCards() {
        return pnlCards;
    }


    /**
     * Retrieves the current JTextField object of txtUsername in the login page.
     * @return The current txtUsername.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
    }


    /**
     * Retrieves the current JTextField object of txtPassword in the login page.
     * @return The current txtPassword.
     */
    public JPasswordField getTxtPassword() {
        return txtPassword;
    }


    /**
     * Retrieves the current JCheckBox object of chkShowPassword in the login page.
     * @return The current chkShowPassword.
     */
    public JCheckBox getChkShowPassword() {
        return chkShowPassword;
    }


    /**
     * Retrieves the current JButton object of btnLogin in the login page.
     * @return The current btnLogin.
     */
    public JButton getBtnLogin() {
        return btnLogin;
    }

    /**
     * Retrieves the current JButton object of btnSignup in the login page.
     * @return The current btnSignup.
     */
    public JButton getBtnSignup() {
        return btnSignup;
    }


    /**
     * Retrieves the current card layout used in pnlCards.
     * @return The current card layout of pnlCards.
     */
    public CardLayout getCardLayout() {
        return cardLayout;
    }

}
