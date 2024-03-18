package shared;

import java.io.Serializable;

/**
 * Template for a vehicle object.
 * The vehicle object contains the needed attributes to identify a single vehicle owned by a user.
 * @see User
 */
public class Vehicle implements Serializable {
    /**
     * Type of vehicle: car or motorcycle.
     **/
    private String type;
    /**
     * Model of vehicle (e.g., Honda Civic).
     */
    private String model;
    /**
     * License plate number of the vehicle (NWA 911).
     */
    private String plateNumber;

    /**
     * Constructs a vehicle with specified values.
     * @param type The specified vehicle type.
     * @param model The specified vehicle model.
     * @param plateNumber The specified vehicle license plate number.
     */
    public Vehicle(String type, String model, String plateNumber) {
        this.type = type;
        this.model = model;
        this.plateNumber = plateNumber;
    }

    /**
     * Constructs a new Vehicle object with the provided vehicle information.
     */
    /*
    public Vehicle(String[] vehicleInfo) {
        this.type = vehicleInfo[0];
        this.model = vehicleInfo[1];
        this.plateNumber = vehicleInfo[2];
    }

     */


    /**
     * Retrieves the current vehicle type.
     * @return The current vehicle type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets a new type for the vehicle.
     * @param type The new vehicle type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the current vehicle model.
     * @return The current vehicle model.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets a new model for the vehicle.
     * @param model The new vehicle model.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Retrieves the current vehicle plate number.
     * @return The current vehicle plate number.
     */
    public String getPlateNumber() {
        return plateNumber;
    }

    /**
     * Sets a new plate number for the vehicle.
     * @param plateNumber The new vehicle plate number.
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    /**
     * Returns a string representation of the Vehicle.
     * The string contains the current state of the vehicle's attributes.
     * @return A string representation of the Vehicle.
     */
    @Override
    public String toString() {
        return type + "," +
                model + "," +
                plateNumber;
    }

    /**
     * Resets the vehicle to its initial state.
     */
    public void reset() {
        type = "";
        model = "";
        plateNumber = "";
    }
}
