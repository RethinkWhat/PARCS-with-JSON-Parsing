package client.controller;

import client.model.LoginModel;
import client.model.RegisterModel;
import client.model.VehicleAdderModel;
import client.view.LoginView;
import client.view.RegisterView;
import client.view.VehicleAdderView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Template for RegisterController.
 * The RegisterController processes the user requests for creating an account
 * Based on the user request, the RegisterController defines methods and invokes methods in the View and Model
 * to accomplish the requested action.
 */
public class RegisterController {
    /**
     * The Register view object.
     */
    private final RegisterView view;
    /**
     * The model RegisterModel object.
     */
    private final RegisterModel model;

    /**
     * Constructs a RegisterController with a specified view and model.
     *
     * @param view  The specified RegisterView.
     * @param model The specified RegisterModel.
     */
    public RegisterController(RegisterView view, RegisterModel model) {
        this.view = view;
        this.model = model;

        // action listeners
        view.getChkShowSignupPassword().addActionListener(new ShowPasswordListener(view.getChkShowSignupPassword(),
                view.getTxtSignupPassword()));
        view.getChkShowConfirmPassword().addActionListener(new ShowPasswordListener(view.getChkShowConfirmPassword(),
                view.getTxtConfirmPassword()));
        view.setCreateAccountListener(new CreateAccountListener());
        view.setBackListener(new LoginListener());

        // mouse listeners
        view.getTxtFirstName().addFocusListener(new LoginTextFieldFocus(view.getTxtFirstName(), "First Name"));
        view.getTxtLastName().addFocusListener(new LoginTextFieldFocus(view.getTxtLastName(), "Last Name"));
        view.getTxtSignupUsername().addFocusListener(new LoginTextFieldFocus(view.getTxtSignupUsername(), "Username"));
        view.getTxtPhoneNo().addFocusListener(new LoginTextFieldFocus(view.getTxtPhoneNo(), "Phone Number"));
        view.getTxtSignupPassword().addFocusListener(new PasswordFocus(view.getTxtSignupPassword(),
                view.getChkShowSignupPassword(), "Password"));
        view.getTxtConfirmPassword().addFocusListener(new PasswordFocus(view.getTxtConfirmPassword(),
                view.getChkShowConfirmPassword(), "Confirm Password"));

        view.repaint();
        view.revalidate();
    }

    /**
     * Creates an object that will be sent to the server for processing.
     */
    class CreateAccountListener implements ActionListener {
        /**
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!model.verifySignupPassword(view.getSignupPassword(), view.getConfirmPassword())) {
                view.displaySignupErrorMessage("Passwords do not match. Try again.");
            } else {
                model.encryptPassword(view.getSignupPassword());

                String username = view.getSignupUsername();
                if (model.createAccount(view.getSignupFirstName(), view.getSignupLastName(), username, view.getSignupPhoneNo(), view.getSignupPassword()))  {
                    //TODO: update
                    model.getClient().setUsername(username);
                    new VehicleAdderController(new VehicleAdderView(), new VehicleAdderModel(model.getClient()));
                    view.dispose();
                } else {
                    view.displaySignupErrorMessage("Username already exists.");
                }
            }
        }
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            LoginView loginView = new LoginView();
            LoginModel loginModel = new LoginModel(model.getClient());
            LoginController controller = new LoginController(loginView,loginModel);
        }
    }

    /**
     * Shows the password of a specified JPasswordField.
     */
    class ShowPasswordListener implements ActionListener {
        /**
         * The specified checkbox of show password.
         */
        private JCheckBox checkBox;
        /**
         * The specified password field.
         */
        private JPasswordField passwordField;

        /**
         * Constructs an object of ShowPasswordField listener with a specified JCheckBox and JPasswordField.
         * @param checkBox The specified "show password" checkbox.
         * @param passwordField The specified password field.
         */
        public ShowPasswordListener(JCheckBox checkBox, JPasswordField passwordField) {
            this.checkBox = checkBox;
            this.passwordField = passwordField;
        }

        /**
         * Processes the user request.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkBox.isSelected()) {
                passwordField.setEchoChar((char) 0); // shows password in characters
            } else if (!checkBox.isSelected()) {
                passwordField.setEchoChar('●');
            }
        }
    }

    /**
     * Clears the text in a specified JTextField when it is focused, and inserts a specified placeholder
     * text when unfocused.
     */
    class LoginTextFieldFocus implements FocusListener {
        /**
         * The specified text field.
         */
        private JTextField textField;
        /**
         * The specified placeholder text.
         */
        private String placeholder;

        /**
         * Constructs an object of TextFieldFocus with a specified text field and placeholder text.
         * @param textField The specified text field.
         * @param placeholder The specified placeholder text.
         */
        public LoginTextFieldFocus(JTextField textField, String placeholder) {
            this.textField = textField;
            this.placeholder = placeholder;
        }

        /**
         * Processes the event when focused. The text field contents are cleared to accommodate user input.
         * @param e the event to be processed
         */
        @Override
        public void focusGained(FocusEvent e) {
            view.displaySignupErrorMessage(""); // resets the signup error message
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
            }
        }

        /**
         * Processes the event when unfocused. A placeholder text is inserted in the text field.
         * @param e the event to be processed
         */
        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
            }
        }
    }

    /**
     * Clears the text in a specified JPasswordField when it is focused, and inserts a specified placeholder
     * text when unfocused.
     */
    class PasswordFocus implements FocusListener {
        /**
         * The specified password field.
         */
        private JPasswordField passwordField;
        /**
         * The specified show password checkbox.
         */
        private JCheckBox chkShowPassword;
        /**
         * The specified placeholder text.
         */
        private String placeholder;

        /**
         * Constructs an object of PasswordFocus with a specified password field, show password text box and
         * placeholder text.
         * @param passwordField The specified password field.
         * @param chkShowPassword The specified show password checkbox.
         * @param placeholder The specified placeholder text.
         */
        public PasswordFocus(JPasswordField passwordField, JCheckBox chkShowPassword, String placeholder) {
            this.passwordField = passwordField;
            this.placeholder = placeholder;
            this.passwordField.setText(placeholder);
            this.chkShowPassword = chkShowPassword;
        }

        /**
         * Processes the event when focused. The checkbox is overridden and hides the password input, and clears
         * the password field of its placeholder text.
         * @param e the event to be processed
         */
        @Override
        public void focusGained(FocusEvent e) {
            view.displaySignupErrorMessage(""); // resets the signup error message
            if (!chkShowPassword.isSelected()) {
                passwordField.setEchoChar('●');
            }
            if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                passwordField.setText("");
            }
        }

        /**
         * Processes the event when focused. The checkbox is overridden and displays the password in plain text, and
         * adds a placeholder text.
         * @param e the event to be processed
         */
        @Override
        public void focusLost(FocusEvent e) {
            if (!chkShowPassword.isSelected()) {
                passwordField.setEchoChar('●');
            }
            if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                passwordField.setText(placeholder);
                passwordField.setEchoChar((char) 0);
            }
        }
    }
}

