package client.model;

import java.rmi.RemoteException;

/**
 * Represents the model for adding vehicles to the user's account.
 * This class provides methods for retrieving vehicle types, writing vehicle information to the server,
 * and facilitating navigation back to the main application view.
 */
public class VehicleAdderModel {
    /**
     * The vehicle types
     */
    private String[] vehicleTypes;
    /**
     * The compiled data of user input to be sent to the server.
     */


    /**
     * Reference to the client
     */
    private Client client;

    /**
     * Constructs an object of VehicleAdderModel with default values.
     */
    public VehicleAdderModel(Client client) {
        vehicleTypes = new String[]{"Select Type:", "Car", "Motorcycle"};
        this.client = client;
    }

    /**
     * Retrieves the current vehicle types.
     * @return The current vehicle types.
     */
    public String[] getVehicleTypes() {
        return vehicleTypes;
    }

    /**
     * Writes the provided vehicle information to the server and returns whether the vehicle was accepted.
     * @param type The type of the vehicle.
     * @param model The model of the vehicle.
     * @param plateNumber The plate number of the vehicle.
     * @return True if the vehicle information was accepted by the server, false otherwise.
     */
    public boolean writeVehicle(String type, String model, String plateNumber) {
        try {
            return client.getRemote().addVehicle(client.getUsername(), type, model, plateNumber);

        }catch (RemoteException e) {
            return false;
        }
    }


    /**
     * Gets the associated client.
     * @return The client associated with the model.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the client associated with the model.
     * @param client The client to be associated with the model.
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
