package client.controller;

import client.model.ApplicationModel;
import client.model.LoginModel;
import client.model.RegisterModel;
import client.view.ApplicationView;
import client.view.LoginView;
import client.view.RegisterView;
import utilities.Resources;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.rmi.RemoteException;

/**
 * Template for LoginController.
 * The LoginController processes the user requests for creating an account and logging in.
 * Based on the user request, the LoginController defines methods and invokes methods in the View and Model
 * to accomplish the requested action.
 */
public class LoginController {
    /**
     * The view LoginView object.
     */
    private final LoginView view;
    /**
     * The model LoginModel object.
     */
    private final LoginModel model;

    /**
     * Constructs a LoginController with a specified view and model.
     *
     * @param view  The specified LoginView.
     * @param model The specified LoginModel.
     */
    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;

        // action listeners
        view.setLoginListener(new LoginListener());
        view.setSignupListener(new SignUpListener());
        view.getChkShowPassword().addActionListener(new ShowPasswordListener(view.getChkShowPassword(),
                view.getTxtPassword()));

        // mouse listeners
        view.getBtnSignup().addMouseListener(new Resources.CursorChanger((view.getBtnSignup())));
        view.getBtnLogin().addMouseListener(new Resources.CursorChanger((view.getBtnSignup())));


        // focus listeners
        view.getTxtUsername().addFocusListener(new LoginTextFieldFocus(view.getTxtUsername(), "Username"));
        view.getTxtPassword().addFocusListener(new PasswordFocus(view.getTxtPassword(),
                view.getChkShowPassword(), "Password"));


        view.repaint();
        view.revalidate();
    }

    /**
     * User inputs are sent to the server to verify whether the user's credentials are matching an existing account.
     */
    class LoginListener implements ActionListener {
        /**
         * The user input of username and password are sent to the server and validated if the account exists and if
         * the credentials are correct. An error message is displayed if the user input is wrong.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e)  {
            String username = view.getUsername();
            try {
                boolean loginAttempt = model.getClient().getRemote().login(username, model.encryptPassword(view.getPassword()));
                if (loginAttempt) {
                    if (!model.getClient().getRemote().isUserLoggedIn(username)) {
                        model.getClient().setUsername(username);
                        new ApplicationController(new ApplicationView(), new ApplicationModel(model.getClient()));
                        view.dispose();
                    } else {
                        view.displayLoginErrorMessage("Account already logged in elsewhere.");
                    }
                } else {
                    view.displayLoginErrorMessage("Account does not exist. Please try again.");
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class SignUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            RegisterView registerView = new RegisterView();
            RegisterModel registerModel = new RegisterModel(model.getClient());
            RegisterController controller = new RegisterController(registerView,registerModel);
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
            view.displayLoginErrorMessage(""); // resets the login error message
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
            view.displayLoginErrorMessage(""); // resets the login error message
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
}

