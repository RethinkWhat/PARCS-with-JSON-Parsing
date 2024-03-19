package client.model;

import java.nio.charset.StandardCharsets;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Represents the model for user registration functionality.
 * This class provides methods for validating user credentials, encrypting passwords,
 * and handling user account creation
 */
public class RegisterModel {

    /**
     * The client object for server communication.
     */
    private Client client;

    /**
     * Holds the error message associated with the last operation, if any.
     */
    private String errorMessage;


    public RegisterModel(Client client) {
        this.client = client;
    }

    /**
     * Verifies if the user input of password matches the password confirmation in the signup page.
     * @param password Specified user input of password.
     * @param passwordConfirmation Specified user input of password confirmation.
     * @return True if the password matches. False if otherwise.
     */
    public static boolean verifySignupPassword(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }

    /**
     * Encrypts a specified plain text password using the SHA256 algorithm.
     * @param rawPassword The specified password in plain text.
     * @return The encrypted password.
     */
    public static String encryptPassword(String rawPassword) {
        String encrypted = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            encrypted = Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    /**
     * Retrieves the error message associated with the last operation.
     * @return The error message, if any.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Creates a new user account with the provided information.
     * @param firstName User's first name.
     * @param lastName User's last name.
     * @param username User's chosen username.
     * @param phoneNumber User's phone number.
     * @param password User's chosen password.
     * @return True if the account creation is successful. False otherwise.
     */
    public boolean createAccount(String firstName, String lastName,
                                 String username, String phoneNumber, String password)  {
        //TODO: RMI Implementation
        try{
            boolean conditionModel = this.getClient().getRemote().createAccount(firstName,lastName,username,phoneNumber,encryptPassword(password));
            return conditionModel;
        }catch (RemoteException re){
            re.printStackTrace();
            return false;
        }
    }

    public Client getClient() {
        return client;
    }
}
