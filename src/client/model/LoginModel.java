package client.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Represents the model for user login a functionality.
 * This class provides methods for validating user credentials, encrypting passwords,
 * and handling user account login.
 */
public class LoginModel {

    /**
     * The client object for server communication.
     */
    private Client client;

    /**
     * Holds the error message associated with the last operation, if any.
     */
    private String errorMessage;

    /**
     * Constructs a LoginRegisterModel with a specified client.
     * @param client The client associated with the model.
     */
    public LoginModel(Client client) {
        this.client = client;
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
     * Verifies the given user credentials by sending the username and password to the server.
     * Returns true if the given credentials are mapped to an existing account. Returns false if the account
     * does not exist or the given credentials are incorrect.
     * @param username The specified username.
     * @param password The specified encrypted password.
     * @return The success state.
     */
    public boolean validateAccount(String username, String password) {
        //TODO: RMI Implementation
        return false;
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
                                 String username, String phoneNumber, String password) {

        //TODO: RMI Implementation
        return false;
    }

    public Client getClient() {
        return client;
    }
}
