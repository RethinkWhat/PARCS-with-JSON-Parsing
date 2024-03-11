package client.model.application_pages;

import javax.swing.*;

/**
 * The CarMotorButton class extends JButton and represents a button specifically designed for car and motorcycle parking slots.
 * It includes additional functionality to identify the type of parking slot (car or motorcycle) and stores an identifier for the button.
 */
public class CarMotorButton extends JButton {

    /**
     * The identifier associated with the parking slot button.
     */
    private String identifier;

    /**
     * Constructs a CarMotorButton with a specified identifier.
     *
     * @param identifier The identifier associated with the parking slot button.
     */
    public CarMotorButton(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Constructs a CarMotorButton with a specified identifier and icon.
     *
     * @param identifier The identifier associated with the parking slot button.
     * @param icon       The icon to be displayed on the button.
     */
    public CarMotorButton(String identifier, Icon icon) {
        this.identifier = identifier;
        this.setIcon(icon);
    }

    /**
     * Checks if the parking slot represented by the button is for a car.
     *
     * @return True if the parking slot is for a car, false if it is for a motorcycle.
     */
    public boolean isCar() {
        return (identifier.contains("C"));
    }

    /**
     * Retrieves the identifier associated with the parking slot button.
     *
     * @return The identifier of the parking slot button.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier associated with the parking slot button.
     *
     * @param identifier The new identifier for the parking slot button.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
