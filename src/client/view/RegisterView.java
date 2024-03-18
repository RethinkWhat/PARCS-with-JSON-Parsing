package client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import utilities.Resources;
public class RegisterView extends JFrame {
    /**
     * The panel that holds different component panels.
     */
    private JPanel pnlCards;
    /**
     * The text field for the user's first name in the signup page.
     */
    private JTextField txtFirstName;
    /**
     * The text field for the user's last name in the signup page.
     */
    private JTextField txtLastName;

    /**
     * The text field for the username in the signup page.
     */
    private JTextField txtSignupUsername;
    /**
     * The text field for the user's phone number in the signup page.
     */
    private JTextField txtPhoneNo;

    /**
     * The password field for the user's password in the signup page.
     */
    private JPasswordField txtSignupPassword;
    /**
     * The password field for the user's password confirmation in the signup page.
     */
    private JPasswordField txtConfirmPassword;

    /**
     * The checkbox to show password in the signup page.
     */
    private JCheckBox chkShowSignupPassword;
    /**
     * The checkbox to show password confirmation in the signup page.
     */
    private JCheckBox chkShowConfirmPassword;
    /**
     * The button to return to the login page from the signup page.
     */
    private JButton btnBack;

    /**
     * The button to create an account.
     */
    private JButton btnCreateAccount;

    /**
     * The error message displayed in the signup page.
     */
    private JLabel lblSignupErrorMessage;

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
    public RegisterView() {
        super("PARCS");

        // Body panel acting as a container to hold all UI components
        Container contentArea = new JPanel(new BorderLayout());
        pnlCards = new JPanel(cardLayout);

        JPanel pnlSignup = new SignupPanel();
        pnlCards.add(pnlSignup, "signup");

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
     * Contains the different signup UI components.
     */
    class SignupPanel extends JPanel {
        /**
         * Constructs a SignupPanel panel.
         */
        public SignupPanel() {
            setLayout(new GridBagLayout());
            setBackground(res.white);

            gbc = new GridBagConstraints();

            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(5,5,5,5);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            btnBack = res.createBtnIconOnly(res.iconLeftArrow,30,30);
            add(btnBack, gbc);

            gbc.gridy = 1;
            gbc.ipady = 40;
            gbc.gridwidth = 3;
            JLabel lblTitle = res.createLblH1("Sign Up", res.eerieBlack);
            lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
            add(lblTitle, gbc);

            gbc.gridy = 3;
            gbc.ipadx = 5;
            gbc.ipady = 3;
            gbc.gridwidth = 2;
            txtFirstName = res.createTxtRounded("First Name", res.lightGray, res.gray, 20);
            add(txtFirstName, gbc);

            gbc.gridx = 2;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            txtLastName = res.createTxtRounded("Last Name", res.lightGray, res.gray, 15);
            add(txtLastName, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            txtSignupUsername = res.createTxtRounded("Username", res.lightGray, res.gray, 20);
            add(txtSignupUsername, gbc);

            gbc.gridx = 2;
            gbc.gridwidth = 1;
            txtPhoneNo = res.createTxtRounded("Phone Number", res.lightGray, res.gray, 15);
            add(txtPhoneNo, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 100;
            txtSignupPassword = res.createPwdRounded(res.lightGray, res.gray, 35);
            txtSignupPassword.setEchoChar((char)0);
            add(txtSignupPassword, gbc);

            gbc.gridy = 6;
            chkShowSignupPassword = new JCheckBox("Show Password");
            chkShowSignupPassword.setFont(new Font("Arial", Font.PLAIN, 14));
            chkShowSignupPassword.setHorizontalAlignment(SwingConstants.LEFT);
            chkShowSignupPassword.setBackground(res.white);
            add(chkShowSignupPassword, gbc);

            gbc.gridx = 0;
            gbc.gridy = 7;
            txtConfirmPassword = res.createPwdRounded(res.lightGray, res.gray, 35);
            txtConfirmPassword.setEchoChar((char)0);
            add(txtConfirmPassword, gbc);

            gbc.gridy = 8;
            chkShowConfirmPassword = new JCheckBox("Show Password");
            chkShowConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
            chkShowConfirmPassword.setHorizontalAlignment(SwingConstants.LEFT);
            chkShowConfirmPassword.setBackground(res.white);
            add(chkShowConfirmPassword, gbc);

            gbc.gridy = 9;
            gbc.fill = GridBagConstraints.NONE;
            lblSignupErrorMessage = res.createLblP("", res.red);
            add(lblSignupErrorMessage, gbc);

            gbc.gridy = 10;
            btnCreateAccount = res.createBtnRounded("Create Account", res.celadon, res.eerieBlack, 10);
            add(btnCreateAccount, gbc);

            this.setPreferredSize(new Dimension(950,560));
        }
    }


    /**
     * Sets a specified action listener for btnCreateAccount.
     * @param createAccountListener The specified action listener.
     */
    public void setCreateAccountListener(ActionListener createAccountListener) {
        btnCreateAccount.addActionListener(createAccountListener);
    }

    /**
     * Sets a specified action listener for btnBack.
     * @param backListener The specified action listener.
     */
    public void setBackListener(ActionListener backListener) {
        btnBack.addActionListener(backListener);
    }



    /**
     * Sets a specified error message for lblSignupErrorMessage.
     * @param message The specified error.
     */
    public void displaySignupErrorMessage(String message) {
        lblSignupErrorMessage.setText(message);
    }



    /**
     * Retrieves the current first name input in the txtFirstName of the signup page.
     * @return The current first name input.
     */
    public String getSignupFirstName() {
        return txtFirstName.getText();
    }

    /**
     * Retrieves the current last name input in the txtLastName of the signup page.
     * @return The current last name input.
     */
    public String getSignupLastName() {
        return txtLastName.getText();
    }

    /**
     * Retrieves the current username input in the txtSignupUsername of the signup page.
     * @return The current username input.
     */
    public String getSignupUsername() {
        return txtSignupUsername.getText();
    }

    /**
     * Retrieves the current phone number input in the txtPhoneNo of the signup page.
     * @return The current phone number input.
     */
    public String getSignupPhoneNo() {
        return txtPhoneNo.getText();
    }

    /**
     * Retrieves the current password input in the txtSignupPassword of the signup page.
     * @return The current password input.
     */
    public String getSignupPassword() {
        return String.valueOf(txtSignupPassword.getPassword());
    }

    /**
     * Retrieves the current password input in the txtConfirmPassword of the signup page.
     * @return The current password input.
     */
    public String getConfirmPassword() {
        return String.valueOf(txtConfirmPassword.getPassword());
    }

    /**
     * Retrieves the current card layout panel containing the login and signup panels.
     * @return The current card layout panel.
     */
    public JPanel getPnlCards() {
        return pnlCards;
    }

    /**
     * Retrieves the current JTextField object of txtSignupUsername in the signup page.
     * @return The current txtSignupUsername.
     */
    public JTextField getTxtSignupUsername() {
        return txtSignupUsername;
    }

    /**
     * Retrieves the current JTextField object of txtFirstName in the signup page.
     * @return The current txtFirstName.
     */
    public JTextField getTxtFirstName() {
        return txtFirstName;
    }

    /**
     * Retrieves the current JTextField object of txtLastName in the signup page.
     * @return The current txtLastName.
     */
    public JTextField getTxtLastName() {
        return txtLastName;
    }


    /**
     * Retrieves the current JTextField object of txtPhoneNo in the signup page.
     * @return The current txtPhoneNo.
     */
    public JTextField getTxtPhoneNo() {
        return txtPhoneNo;
    }


    /**
     * Retrieves the current JPasswordField object of txtSignupPassword in the signup page.
     * @return The current txtSignupPassword.
     */
    public JPasswordField getTxtSignupPassword() {
        return txtSignupPassword;
    }

    /**
     * Retrieves the current JPasswordField object of txtConfirmPassword in the signup page.
     * @return The current txtConfirmPassword.
     */
    public JPasswordField getTxtConfirmPassword() {
        return txtConfirmPassword;
    }

    /**
     * Retrieves the current JButton object of btnBack in the signup page.
     * @return The current btnBack.
     */
    public JButton getBtnBack() {
        return btnBack;
    }


    /**
     * Retrieves the current JButton object of btnCreateAccount in the signup page.
     * @return The current btnCreateAccount.
     */
    public JButton getBtnCreateAccount() {
        return btnCreateAccount;
    }

    /**
     * Retrieves the current card layout used in pnlCards.
     * @return The current card layout of pnlCards.
     */
    public CardLayout getCardLayout() {
        return cardLayout;
    }

    /**
     * Retrieves the current JCheckBox object of chkShowSignupPassword.
     * @return The current chkShowSignupPassword.
     */
    public JCheckBox getChkShowSignupPassword() {
        return chkShowSignupPassword;
    }

    /**
     * Retrieves the current JCheckbox object of chkShowConfirmPassword.
     * @return The current chkShowConfirmPassword.
     */
    public JCheckBox getChkShowConfirmPassword() {
        return chkShowConfirmPassword;
    }
}
