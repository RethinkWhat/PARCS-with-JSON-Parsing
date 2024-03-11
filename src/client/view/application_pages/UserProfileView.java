package client.view.application_pages;

import utilities.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The UserProfileView is where the user views pertinent information regarding their account
 * and transaction in the client application.
 */
public class UserProfileView extends JPanel {
    /**
     * The stylesheet
     */
    static Resources res = new Resources();
    /**
     * Instance variable of grid bag constraints used in grid bag layout.
     */
    GridBagConstraints gbc = new GridBagConstraints();
    /**
     * The edit profile button.
     */
    private JButton btnNavEditProfile;
    /**
     * The edit cars button.
     */
    private JButton btnNavEditCars;
    /**
     * The security button.
     */
    private JButton btnNavSecurity;
    /**
     * The history button.
     */
    private JButton btnNavHistory;
    /**
     * The button to delete account.
     */
    private JButton btnNavDelete;
    /**
     * The exit/logout button.
     */
    private JButton btnNavExit;
    /**
     * The CardLayout that controls the components of pnlCards.
     */
    private CardLayout cardLayout;
    /**
     * The panel that holds different component panels.
     */
    private JPanel pnlCards;
    /**
     * The panel for Edit profile.
     */
    private EditProfile pnlEditProfile;
    /**
     * The panel for profile options (account navigation)
     */
    private ProfileOptionsPanel pnlProfileOptions;
    /**
     * The panel for Edit cars.
     */
    private EditCars pnlEditCars;
    /**
     * The panel for History.
     */
    private BookingsView pnlBookingsView;
    /**
     * The panel for Security.
     */
    private SecurityPage pnlSecurityPage;

    /**
     * Constructs a panel of UserProfileView.
     */
    public UserProfileView() {
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();

        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setPreferredSize(new Dimension(950, 700));
        add(pnlRight, BorderLayout.EAST);

        pnlCards = new JPanel(cardLayout);
        pnlCards.setPreferredSize(new Dimension(950, 560));
        // pnlCards.setBounds(220,0,750,560);
        pnlRight.add(pnlCards, BorderLayout.NORTH);

        pnlCards.add(pnlEditProfile = new EditProfile(), "profile");
        // edit cars page
        pnlCards.add(pnlEditCars = new EditCars(), "vehicles");
        // security page
        pnlCards.add(pnlSecurityPage = new SecurityPage(), "security");
        // history page
        pnlCards.add(pnlBookingsView = new BookingsView(), "history");
        //shows edit profile first
        cardLayout.show(pnlCards, "profile");

        // account navigation panel
        pnlProfileOptions = new ProfileOptionsPanel(cardLayout, pnlCards);
        add(pnlProfileOptions, BorderLayout.WEST);

        this.setPreferredSize(new Dimension(950, 700));
    }

    /**
     * The panel that contains the sidebar options.
     */
    class ProfileOptionsPanel extends JPanel {
        /**
         * Constructs a panel of ProfileOptions with a specified layout manager and main panel to hold the components.
         *
         * @param cardLayout The specified CardLayout layout manager.
         * @param pnlCards   The specified main panel.
         */
        public ProfileOptionsPanel(CardLayout cardLayout, JPanel pnlCards) {
            this.setLayout(new BorderLayout()); // Use BorderLayout for easier component placement
            this.setBackground(res.white);

            JPanel buttonsPanel = new JPanel(new GridLayout(6, 1, 0, 0));
            buttonsPanel.setPreferredSize(new Dimension(300,400));
            buttonsPanel.setBackground(res.white);

            Font buttonFont = new Font("Arial", Font.BOLD, 20);

            ImageIcon editProfile = new ImageIcon("res/drawable/icons/edit.png");
            btnNavEditProfile = res.createBtnTxtOnly(" Edit Profile", res.gray);
            btnNavEditProfile.setFont(buttonFont);
            btnNavEditProfile.setHorizontalAlignment(SwingConstants.LEFT);
            ImageIcon editProfileResized = new ImageIcon(editProfile.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
            btnNavEditProfile.setIcon(editProfileResized);
            buttonsPanel.add(btnNavEditProfile);

            ImageIcon editCars = new ImageIcon("res/drawable/icons/edit-cars.png");
            btnNavEditCars = res.createBtnTxtOnly(" Edit Vehicles", res.gray);
            btnNavEditCars.setFont(buttonFont);
            btnNavEditCars.setHorizontalAlignment(SwingConstants.LEFT);
            ImageIcon editCarsResized = new ImageIcon(editCars.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
            btnNavEditCars.setIcon(editCarsResized);
            buttonsPanel.add(btnNavEditCars);

            ImageIcon security = new ImageIcon("res/drawable/icons/security.png");
            btnNavSecurity = res.createBtnTxtOnly(" Security", res.gray);
            btnNavSecurity.setFont(buttonFont);
            btnNavSecurity.setHorizontalAlignment(SwingConstants.LEFT);
            ImageIcon securityResized = new ImageIcon(security.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
            btnNavSecurity.setIcon(securityResized);
            buttonsPanel.add(btnNavSecurity);

            ImageIcon historyIcon = new ImageIcon("res/drawable/icons/history.png");
            btnNavHistory = res.createBtnTxtOnly(" Bookings", res.gray);
            btnNavHistory.setFont(buttonFont);
            btnNavHistory.setHorizontalAlignment(SwingConstants.LEFT);
            ImageIcon helpResized = new ImageIcon(historyIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
            btnNavHistory.setIcon(helpResized);
            buttonsPanel.add(btnNavHistory);

            btnNavDelete = res.createBtnTxtOnly(" Delete Account", res.gray);
            btnNavDelete.setFont(buttonFont);
            btnNavDelete.setHorizontalAlignment(SwingConstants.LEFT);
            ImageIcon deleteResized = new ImageIcon(res.iconDelete.getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH));
            btnNavDelete.setIcon(deleteResized);
            buttonsPanel.add(btnNavDelete);


            ImageIcon exitIcon = new ImageIcon("res/drawable/icons/exit-grey-outline.png");
            btnNavExit = res.createBtnTxtOnly(" Logout", res.gray);
            btnNavExit.setFont(buttonFont);
            btnNavExit.setHorizontalAlignment(SwingConstants.LEFT);
            ImageIcon exitResized = new ImageIcon(exitIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
            btnNavExit.setIcon(exitResized);
            buttonsPanel.add(btnNavExit);

            // Add buttons panel to the center of the layout with some padding
            this.add(buttonsPanel, BorderLayout.NORTH);
            this.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding to the panel
            this.setPreferredSize(new Dimension(300, 400)); // Set preferred size
        }
    }

    /**
     * The panel that contains pertinent information of the user. The user can edit or simply view their information
     * on this page.
     */
    public class EditProfile extends JPanel {
        /**
         * The text field where the first name of the user can be viewed and edited.
         */
        private JTextField txtFirstName;
        /**
         * The text field where the last name of the user can be viewed and edited.
         */
        private JTextField txtLastName;
        /**
         * The text field where the username of the user can be viewed and edited.
         */
        private JTextField txtUsername;
        /**
         * The text field where the contact number of the user can be viewed and edited.
         */
        private JTextField txtContact;
        /**
         * The cancel button.
         */
        private JButton btnCancel;
        /**
         * The continue button.
         */
        private JButton btnContinue;

        /**
         * Constructs a panel of EditProfile.
         */
        public EditProfile() {
            // this.setPreferredSize(new Dimension(650,490));
            this.setLayout(new GridLayout(8, 1));
            this.setBorder(new EmptyBorder(10, 20, 10, 60));

            JLabel lblEditProfile = res.createLblH1("Edit Profile", res.eerieBlack);
            lblEditProfile.setBorder(new EmptyBorder(25, 0, 0, 0));
            lblEditProfile.setFont(new Font("Arial", Font.BOLD, 32));
            this.add(lblEditProfile);

            JPanel pnlNameLabels = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel lblFirstName = res.createLblH3("First Name", res.eerieBlack);
            lblFirstName.setBorder(new EmptyBorder(30, 0, 0, 0));

            pnlNameLabels.add(lblFirstName);

            // nameLabelsPanel.add(firstNameLabel);
            JLabel lastNameLabel = res.createLblH3("Last Name", res.eerieBlack);
            lastNameLabel.setBorder(new EmptyBorder(30, 180, 0, 0));
            pnlNameLabels.add(lastNameLabel);

            this.add(pnlNameLabels);

            JPanel pnlNameField = new JPanel(new FlowLayout(FlowLayout.LEFT));

            txtFirstName = res.createTxtRounded("First Name", res.white, res.gray, 20);
            txtFirstName.setPreferredSize(new Dimension(10, 50));

            txtLastName = res.createTxtRounded("Last Name", res.white, res.gray, 20);
            txtLastName.setPreferredSize(new Dimension(10, 50));

            pnlNameField.add(txtFirstName);
            pnlNameField.add(txtLastName);

            this.add(pnlNameField);

            JLabel lblUsername = res.createLblH3("Username", res.eerieBlack);
            lblUsername.setBorder(new EmptyBorder(30, 0, 0, 0));
            this.add(lblUsername);

            txtUsername = res.createTxtRounded("Username", res.lightGray, res.gray, 20);
            txtUsername.setSize(new Dimension(50, 50));
            txtUsername.setFocusable(false);
            this.add(txtUsername);

            JLabel lblContact = res.createLblH3("Contact", res.eerieBlack);
            lblContact.setBorder(new EmptyBorder(30, 0, 0, 0));
            this.add(lblContact);

            txtContact = res.createTxtRounded("#############", res.white, res.gray, 20);
            this.add(txtContact);

            JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEADING));
            pnlButtons.setBorder(new EmptyBorder(0, 0, 5, 0));

            btnCancel = res.createBtnRounded("Cancel", res.feldgrau, res.eerieBlack, 30);
            btnCancel.setPreferredSize(new Dimension(130, 40));
            pnlButtons.add(btnCancel);

            btnContinue = res.createBtnRounded("Continue", res.feldgrau, res.eerieBlack, 30);
            btnContinue.setPreferredSize(new Dimension(130, 40));
            pnlButtons.add(btnContinue);
            this.add(pnlButtons);
        }

        /**
         * Sets a specified action listener for btnContinue.
         *
         * @param actionListener The specified action listener.
         */
        public void setContinueListener(ActionListener actionListener) {
            btnContinue.addActionListener(actionListener);
        }

        /**
         * Sets a specified action listener for btnCancel.
         *
         * @param actionListener The specified action listener.
         */
        public void setCancelListener(ActionListener actionListener) {
            btnCancel.addActionListener(actionListener);
        }

        /**
         * Retrieves the current JTextField of txtFirstName.
         *
         * @return The current txtFirstName.
         */
        public JTextField getTxtFirstName() {
            return txtFirstName;
        }

        /**
         * Retrieves the current JTextField of txtLastName.
         *
         * @return The current txtLastName.
         */
        public JTextField getTxtLastName() {
            return txtLastName;
        }

        /**
         * Retrieves the current JTextField of txtUsername.
         *
         * @return The current txtUsername.
         */
        public JTextField getTxtUsername() {
            return txtUsername;
        }

        /**
         * Retrieves the current JTextField of txtContact.
         *
         * @return The current txtContact
         */
        public JTextField getTxtContact() {
            return txtContact;
        }

        /**
         * Retrieves the current value of txtFirstName.
         *
         * @return The current firstName.
         */
        public String getFirstName() {
            return txtFirstName.getText();
        }

        /**
         * Retrieves the current value of txtLastName.
         *
         * @return The current LastName.
         */
        public String getLastName() {
            return txtLastName.getText();
        }

        /**
         * Retrieves the current value of txtUsername.
         *
         * @return The current Username.
         */
        public String getUsername() {
            return txtUsername.getText();
        }

        /**
         * Retrieves the current value of txtContact.
         *
         * @return The current Contact
         */
        public String getContact() {
            return txtContact.getText();
        }

        /**
         * Retrieves the current JButton of btnCancel.
         * @return The current btnCancel.
         */
        public JButton getBtnCancel() {
            return btnCancel;
        }

        /**
         * Retrieves the current JButton of btnContinue.
         * @return The current btnContinue.
         */
        public JButton getBtnContinue() {
            return btnContinue;
        }
    }

    /**
     * The panel that contains the information on the vehicle of the users that can be edited or add new vehicles.
     */
    public class EditCars extends JPanel {
        /**
         * The panel to holds the different components.
         */
        private JPanel pnlCards;
        /**
         * The button for previous.
         */
        private JButton btnPrev;
        /**
         * The button for next.
         */
        private JButton btnNext;
        /**
         * The button for cancel.
         */
        private JButton btnCancel;
        /**
         * The button for continue.
         */
        private JButton btnContinue;
        /**
         * Button for Adding Vehicle
         */
        private JButton btnAddVehicle;

        /**
         * The Card Layout that controls multiple components.
         */
        private CardLayout cardLayout = new CardLayout();

        /**
         * Constructs a panel of EditCars.
         */
        public EditCars() {
            this.setLayout(new BorderLayout());
            this.setBorder(new EmptyBorder(40, 20, 20, 20));

            JLabel myCars = res.createLblH1("My Cars", res.eerieBlack);
            add(myCars, BorderLayout.NORTH);

            pnlCards  = new JPanel(cardLayout);
            pnlCards.setBackground(res.white);
            pnlCards.setPreferredSize(new Dimension(300,325));
            add(pnlCards, BorderLayout.CENTER);

            btnPrev = res.createBtnIconOnly(res.iconLeftArrow, 20,20);
            add(btnPrev, BorderLayout.WEST);

            btnNext = res.createBtnIconOnly(res.iconRightArrow, 20,20);
            add(btnNext, BorderLayout.EAST);

            JPanel pnlButtons = new JPanel(new FlowLayout());
            pnlButtons.setBackground(res.lightGray);
            add(pnlButtons, BorderLayout.SOUTH);

            btnCancel = res.createBtnRounded("Cancel", res.lightGray, res.eerieBlack, 10);
            btnCancel.setVisible(false);
            pnlButtons.add(btnCancel);

            btnAddVehicle = res.createBtnRounded("Add Vehicle", res.celadon, res.eerieBlack, 10);
            pnlButtons.add(btnAddVehicle);

            btnContinue = res.createBtnRounded("Continue", res.celadon, res.celadon, 10);
            btnContinue.setVisible(false);
            pnlButtons.add(btnContinue);

            this.setPreferredSize(new Dimension(750,700));
        }

        /**
         * Retrieves the current JButton of btnPrev.
         * @return The current btnPrev.
         */
        public JButton getBtnPrev() {
            return btnPrev;
        }

        /**
         * Retrieves the current JButton of btnNext.
         * @return The current btnNext.
         */
        public JButton getBtnNext() {
            return btnNext;
        }

        /**
         * Retrieves the current panel with the card layout.
         * @return Panel with the card layout.
         */
        public JPanel getPnlCards() {
            return pnlCards;
        }

        /**
         * Retrieves the current JButton of btnCancel.
         * @return The current btnCancel.
         */
        public JButton getBtnCancel() {
            return btnCancel;
        }

        /**
         * Retrieves the current JButton of btnContinue.
         * @return The current btnContinue.
         */
        public JButton getBtnContinue() {
            return btnContinue;
        }
        /**
         *
         */
        public JButton getBtnAddVehicle(){
            return btnAddVehicle;
        }

        /**
         * Retrieves the current card layout that controls the panels.
         * @return THe current card layout.
         */
        public CardLayout getCardLayout() {
            return cardLayout;
        }

        /**
         * Sets a specified action listener to btnNext.
         * @param actionListener The specified action listener.
         */
        public void setNextListener(ActionListener actionListener) {
            btnNext.addActionListener(actionListener);
        }

        /**
         * Sets a specified action listener to btnPrev.
         * @param actionListener The specified action listener.
         */
        public void setPrevListener(ActionListener actionListener) {
            btnPrev.addActionListener(actionListener);
        }

        /**
         * Sets a specified listener to btnCancel.
         * @param actionListener The specified action listener.
         */
        public void setCancelListener(ActionListener actionListener) {
            btnCancel.addActionListener(actionListener);
        }

        /**
         * Sets a specified listener to btnContinue.
         * @param actionListener The specified action listener.
         */
        public void setContinueListener(ActionListener actionListener) {
            btnContinue.addActionListener(actionListener);
        }

        public void setAddVehicleListener(ActionListener addVehicleListener) {
            btnAddVehicle.addActionListener((addVehicleListener));
        }

        /**
         * Panel containing a vehicle's pertinent information.
         */
        public static class CarsPanel extends JPanel {
            /**
             * The text field for the plate number.
             */
            private JTextField txtPlateNumber;
            /**
             * The text field for the model.
             */
            private JTextField txtModel;
            /**
             * The text field for the vehicle type.
             */
            private JTextField txtVehicleType;
            /**
             * The button for edit.
             */
            private JButton btnEdit;

            /**
             * Constructs a panel of CarsPanel.
             */
            public CarsPanel(String plateNumber, String type, String model) {
                setLayout(new BorderLayout());

                JPanel whitePanel = res.createPnlRounded(250,300,res.white, res.lightGray);
                whitePanel.setLayout(new GridBagLayout());
                whitePanel.setBackground(res.white);
                whitePanel.setPreferredSize(new Dimension(250, 300));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(5, 5, 5, 5);

                Font labelFontBold = new Font("Arial", Font.BOLD, 20);

                JLabel lblPlateNumber = new JLabel("Plate Number");
                lblPlateNumber.setFont(labelFontBold);
                whitePanel.add(lblPlateNumber, gbc);

                gbc.gridx = 1;
                gbc.anchor = GridBagConstraints.EAST;
                btnEdit = res.createBtnIconOnly(res.iconEdit, 30,30);
                whitePanel.add(btnEdit, gbc);

                gbc.gridx = 0;
                gbc.gridwidth = 2;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.gridy++; // Move to the next row
                gbc.anchor = GridBagConstraints.WEST;
                txtPlateNumber = res.createTxtRounded(plateNumber, res.white, res.gray, 20);
                txtPlateNumber.setEditable(false);
                txtPlateNumber.setFocusable(false);
                whitePanel.add(txtPlateNumber, gbc);

                gbc.gridy++; // Move to the next row
                JLabel lblModel = new JLabel("Model");
                lblModel.setFont(labelFontBold);
                whitePanel.add(lblModel, gbc);

                gbc.gridy++; // Move to the next row
                txtModel = res.createTxtRounded(model, res.white, res.gray, 20);
                txtModel.setEditable(false);
                txtModel.setFocusable(false);
                whitePanel.add(txtModel, gbc);

                gbc.gridy++; // Move to the next row
                JLabel vehicle = new JLabel("Vehicle Type");
                vehicle.setFont(labelFontBold);
                whitePanel.add(vehicle, gbc);

                gbc.gridy++; // Move to the next row
                txtVehicleType = res.createTxtRounded(type, res.white, res.gray, 20);
                txtVehicleType.setEditable(false);
                txtVehicleType.setEditable(false);
                whitePanel.add(txtVehicleType, gbc);

                this.add(whitePanel, BorderLayout.CENTER);
                this.setPreferredSize(new Dimension(new Dimension(300,325)));
                this.setVisible(true);
            }

            /**
             * Retrieves the current JTextField of txtPlateNumber.
             * @return The current txtPlateNumber.
             */
            public JTextField getTxtPlateNumber() {
                return txtPlateNumber;
            }

            /**
             * Retrieves the current JTextField of txtModel.
             * @return The current txtModel.
             */
            public JTextField getTxtModel() {
                return txtModel;
            }

            /**
             * Retrieves the current JTextField of txtVehicleType.
             * @return The current txtVehicleType.
             */
            public JTextField getTxtVehicleType() {
                return txtVehicleType;
            }

            /**
             * Retrieves the current JButton of btnEdit.
             * @return The current btnEdit.
             */
            public JButton getBtnEdit() {
                return btnEdit;
            }

            /**
             * Sets a specified action listener for btnEdit.
             * @param actionListener The specified action listener.
             */
            public void setEditListener(ActionListener actionListener) {
                btnEdit.addActionListener(actionListener);
            }
        }
    }

    /**
     * This class represents a panel for changing account passwords.
     * It provides fields for entering current password, new password, and confirming the new password.
     */
    public class SecurityPage extends JPanel {
        /**
         * The password field where the current password is inputted.
         */
        private JPasswordField txtCurrentPassword;
        /**
         * The password field where the new password is entered.
         */
        private JPasswordField txtNewPassword;
        /**
         * The password field where the new password is confirmed.
         */
        private JPasswordField txtConfirmNewPassword;
        /**
         * The continue button.
         */
        private JButton btnConfirm;
        /**
         * The success or error message.
         */
        private JLabel lblMessage;

        /**
         * This constructs a new SecurityPage panel with fields for changing account passwords.
         * Initializes layout and components such as labels, text fields, and buttons.
         */
        public SecurityPage() {
            //sets a layout and borders
            this.setLayout(new GridLayout(9, 2));
            this.setBorder(new EmptyBorder(10, 20, 10, 60));

            //creates and adds labels for the password change section
            JLabel lblChangePassword = res.createLblH1("Change Password", res.eerieBlack);
            lblChangePassword.setBorder(new EmptyBorder(25, 0, 0, 0));
            lblChangePassword.setFont(new Font("Arial", Font.BOLD, 32));
            this.add(lblChangePassword);

            //adds label and text field for entering the current password
            JPanel pnlNameLabels = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel currentPassword = res.createLblH3("Current Password", res.eerieBlack);
            currentPassword.setBorder(new EmptyBorder(30, 0, 0, 0));
            pnlNameLabels.add(currentPassword);
            this.add(pnlNameLabels);

            JPanel pnlCurrentPasswordField = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlCurrentPasswordField.setPreferredSize(new Dimension(750,50));
            this.add(pnlCurrentPasswordField);
            txtCurrentPassword = res.createPwdRounded(res.white, res.gray, 50);
            txtCurrentPassword.setPreferredSize(new Dimension(750,50));
            txtCurrentPassword.setEchoChar((char)0);
            pnlCurrentPasswordField.add(txtCurrentPassword);

            //adds a label and text field for entering the new password
            JLabel newPassword = res.createLblH3("New Password", res.eerieBlack);
            newPassword.setBorder(new EmptyBorder(30, 0, 0, 0));
            this.add(newPassword);
            JPanel pnlNewPasswordField = new JPanel(new FlowLayout(FlowLayout.LEFT));
            this.add(pnlNewPasswordField);
            txtNewPassword = res.createPwdRounded(res.white, res.gray, 50);
            txtNewPassword.setPreferredSize(new Dimension(750,50));
            txtNewPassword.setEchoChar((char)0);
            pnlNewPasswordField.add(txtNewPassword);

            //adds a label and text field for confirming the new password
            JLabel confirmNewPassword = res.createLblH3("Confirm New Password", res.eerieBlack);
            confirmNewPassword.setBorder(new EmptyBorder(30, 0, 0, 0));
            this.add(confirmNewPassword);
            JPanel pnlConfirmNewPasswordField = new JPanel(new FlowLayout(FlowLayout.LEFT));
            this.add(pnlConfirmNewPasswordField);
            txtConfirmNewPassword = res.createPwdRounded(res.white, res.gray, 50);
            txtConfirmNewPassword.setPreferredSize(new Dimension(750,50));
            txtConfirmNewPassword.setEchoChar((char)0);
            pnlConfirmNewPasswordField.add(txtConfirmNewPassword);

            //adds a button for confirming the password change
            JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
            pnlButtons.setBorder(new EmptyBorder(10, 0, 7, 0));
            btnConfirm = res.createBtnRounded("Change Password", res.celadon, res.eerieBlack, 40);
            btnConfirm.setPreferredSize(new Dimension(250,35 ));
            pnlButtons.add(btnConfirm);
            this.add(pnlButtons);

            lblMessage = res.createLblP("", res.red); // empty message
            this.add(lblMessage);
        }

        /**
         * Retrieves the current JPasswordField of txtCurrentPassword in the security page.
         * @return The current txtCurrentPassword.
         */
        public JPasswordField getTxtCurrentPassword() {
            return txtCurrentPassword;
        }

        /**
         * Retrieves the current JPasswordField of txtNewPassword in the security page.
         * @return The current txtNewPassword.
         */
        public JPasswordField getTxtNewPassword() {
            return txtNewPassword;
        }

        /**
         * Retrieves the current txtConfirmNewPassword in the security page.
         * @return The current txtConfirmPassword.
         */
        public JPasswordField getTxtConfirmNewPassword() {
            return txtConfirmNewPassword;
        }

        /**
         * Retrieves the current JButton of btnConfirm.
         * @return The current btnConfirm.
         */
        public JButton getBtnConfirm() {
            return btnConfirm;
        }

        /**
         * Retrieves the current JLabel of lblMessage.
         * @return The current lblMessage.
         */
        public JLabel getLblMessage() {
            return lblMessage;
        }

        /**
         * Retrieves the current value of txtCurrentPassword.
         * @return The current value of current password.
         */
        public String getCurrentPassword() {
            return String.valueOf(txtCurrentPassword.getPassword());
        }

        /**
         * Retrieves the current value of txtNewPassword.
         * @return The current new password.
         */
        public String getNewPassword() {
            return String.valueOf(txtNewPassword.getPassword());
        }

        /**
         * Retrieves the current value of txtConfirmNewPassword.
         * @return The current confirm new password input.
         */
        public String getConfirmNewPassword() {
            return String.valueOf(txtConfirmNewPassword.getPassword());
        }

        /**
         * Sets a specified action listener for btnConfirm.
         * @param actionListener The specified action listener.
         */
        public void setConfirmListener(ActionListener actionListener) {
            btnConfirm.addActionListener(actionListener);
        }
    }


    /**
     * Sets a specified action listener for btnNavEditProfile.
     * @param actionListener The specified action listener.
     */
    public void setNavEditProfileListener(ActionListener actionListener) {
        btnNavEditProfile.addActionListener(actionListener);
    }

    /**
     * Sets a specified action listener for btnNavEditCars.
     * @param actionListener The specified action listener.
     */
    public void setNavEditCarsListener(ActionListener actionListener) {
        btnNavEditCars.addActionListener(actionListener);
    }

    /**
     * Sets a specified action listener for btnNavHistory.
     * @param actionListener The specified action listener.
     */
    public void setNavHistoryListener(ActionListener actionListener) {
        btnNavHistory.addActionListener(actionListener);
    }

    /**
     * Sets a specified action listener for btnNavExit.
     * @param actionListener The specified action listener.
     */
    public void setNavExitListener(ActionListener actionListener) {
        btnNavExit.addActionListener(actionListener);
    }

    /**
     * Sets a specified action listener for btnNavDelete.
     * @param actionListener The specified action listener.
     */
    public void setDeleteListener(ActionListener actionListener) {
        btnNavDelete.addActionListener(actionListener);
    }

    /**
     * Sets a specified action listener for btnNavSecurity.
     * @param actionListener The specified action listener.
     */
    public void setNavSecurityListener(ActionListener actionListener) {
        btnNavSecurity.addActionListener(actionListener);
    }

    /**
     * Retrieves the current JButton of btnNavEditProfile.
     * @return The current btnNavEditProfile.
     */
    public JButton getBtnNavEditProfile() {
        return btnNavEditProfile;
    }

    /**
     * Retrieves the current JButton of btnNavEditCars.
     * @return The current btnNavEditCars.
     */
    public JButton getBtnNavEditCars() {
        return btnNavEditCars;
    }

    /**
     * Retrieves the current JButton of btnNavSecurity.
     * @return The current btnNavSecurity.
     */
    public JButton getBtnNavSecurity() {
        return btnNavSecurity;
    }

    /**
     * Retrieves the current JButton of btnNavHistory.
     * @return The current btnNavHistory.
     */
    public JButton getBtnNavHistory() {
        return btnNavHistory;
    }

    /**
     * Retrieves the current JButton of btnNavDelete.
     * @return The current btnNavDelete.
     */
    public JButton getBtnNavDelete() {
        return btnNavDelete;
    }

    /**
     * Retrieves the current JButton of btnNavExit.
     * @return The current btnNavExit.
     */
    public JButton getBtnNavExit() {
        return btnNavExit;
    }

    /**
     * Provides access to the CardLayout used for managing the panels.
     * @return The CardLayout object used for managing panels.
     */
    public CardLayout getCardLayout() {
        return cardLayout;
    }

    /**
     * Provides access to the main panel containing cards managed by the CardLayout.
     * @return The JPanel containing cards managed by the CardLayout.
     */
    public JPanel getPnlCards() {
        return pnlCards;
    }

    /**
     * Provides access to the panel for editing profile information.
     * @return The EditProfile panel for editing profile information.
     */
    public EditProfile getPnlEditProfile() {
        return pnlEditProfile;
    }

    /**
     * Provides access to the panel containing profile options.
     * @return The ProfileOptionsPanel containing profile options buttons.
     */
    public ProfileOptionsPanel getPnlProfileOptions() {
        return pnlProfileOptions;
    }

    /**
     * Provides access to the panel for editing cars information.
     * @return The EditCars panel for editing cars information.
     */
    public EditCars getPnlEditCars() {
        return pnlEditCars;
    }

    /**
     * Provides access to the panel for managing security settings.
     * @return The SecurityPage panel for managing security settings.
     */
    public SecurityPage getPnlSecurityPage(){
        return pnlSecurityPage;
    }

    /**
     * Retrieves the current panel of HistoryPage.
     * @return The current pnlBookingsView.
     */
    public BookingsView getPnlBookingsView() {
        return pnlBookingsView;
    }
}

