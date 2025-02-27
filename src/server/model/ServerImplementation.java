package server.model;

import shared.ServerMessage;
import shared.Vehicle;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ServerImplementation implements the different methods in the ServerMessage.
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerMessage {
    /**
     * Instance of the RMI registry.
     */
    private Registry reg;

    /**
     * Maintains a record of users currently logged into the system.
     */
    private ArrayList<String> userLog;
    /**=======================================*/


    /**
     * JSON Parser for users.
     */
    private GsonUserParser gsonUserParser;
    /**
     * JSON Parser for the reservation of all user.
     */
    private GsonReservationParser gsonReservationParser;

    /**
     * Constructs an object of ServerImplementation with a given registry port.
     * @param registryPort The specified port.
     * @throws RemoteException If error or exception occurs in instantiation.
     */
    public ServerImplementation(int registryPort) throws RemoteException {
        try {
            reg = LocateRegistry.createRegistry(registryPort);
            reg.rebind("server", this);
            userLog = new ArrayList<>();
            gsonUserParser = new GsonUserParser();
            gsonReservationParser = new GsonReservationParser();
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RemoteException();
        }
    }

    /**
     * Method to validate a user login attempt
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean login(String username, String password) {
        return gsonUserParser.validateLogin(username, password);
    }

    /**
     * Method to check whether a user is already logged into the system
     * @param username
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean isUserLoggedIn(String username) throws RemoteException {
        if (!userLog.contains(username.toLowerCase())) {
            userLog.add(username);
            return false;
        }
        return true;
    }

    /**
     * Method to get the full name of a user
     * @param username
     * @return
     */
    public String getFullName(String username) {
        return gsonUserParser.getUserFullName(username);
    }
    public String getFirstName(String username){return gsonUserParser.getUserFirstName(username);}
    public String getLastName(String username){return gsonUserParser.getUserLastName(username);}
    public String getContact(String username){return gsonUserParser.getContact(username);}

    /**
     * Method to get all the bookings associated with a user
     * @param username
     * @return
     */
    public int getUserTotalBookings(String username) {
        DateTime dateTime = new DateTime();
        return gsonReservationParser.getUserTotalBookings(username, dateTime.getDateTime());
    }

    /**
     * Method to get all the vehicles associated with a user
     * @param username
     * @return
     */
    public List<Vehicle> getUserVehicles(String username) {
        return gsonUserParser.getUserVehicles(username);
    }

    /**
     * Method to get all the time in a date a specific car spot is available
     * @param identifier
     * @param duration
     * @param date
     * @return
     */
    public List<String> spotTimeAvailable(String identifier, String duration, String date) {
        DateTime dateTime = new DateTime();
        if (dateTime.getDateTime().equals(date)) {
            return gsonReservationParser.spotTimeAvailable(identifier, Integer.valueOf(duration), date, dateTime.getTime(), "15:00");
        } else {
            return gsonReservationParser.spotTimeAvailable(identifier, Integer.valueOf(duration), date, "7:00", "15:00");
        }

    }

    /**
     * Method to handle attempting a booking reservation
     * @param identifier
     * @param date
     * @param startTime
     * @param duration
     * @param username
     * @return
     */
    public boolean bookReservation(String identifier, String date, String startTime, String duration, String username) {
        boolean check = !gsonReservationParser.hasSchedulingConflicts(identifier, date, startTime, duration);
        if (check) {
            gsonReservationParser.makeReservation(identifier, date, startTime, duration, username);
        }
        return check;
    }


    public boolean logout(String username) {
        try {
                userLog.remove(username);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public List<List<String>> viewHistory(String username) throws RemoteException {
        Map<String,Reservations> parkingSpotList = gsonReservationParser.getUserReservations(username);

        List<List<String>> toReturn = new ArrayList<>();
        for (String key : parkingSpotList.keySet()) {

            HashMap<TimeRange, String> map = parkingSpotList.get(key).getTimeAndUserMap();

            for (TimeRange timeRange : map.keySet()) {
                List<String> tempArray = new ArrayList<>();
                String type = "Motor";
                if (key.contains("C"))
                    type = "Car";
                tempArray.add(key);
                tempArray.add(type);
                tempArray.add(parkingSpotList.get(key).getDate());
                tempArray.add(timeRange.startTime());
                tempArray.add(timeRange.endTime());
                toReturn.add(tempArray);
            }
        }

        /*
        List<List<String>> userBookings = new ArrayList<>();
        for (String key : parkingSpotList.keySet()) {
            ArrayList<String> booking = new ArrayList<>();
            if (key.contains("C"))
                booking.add("Car");
            else
                booking.add("Motor");

            booking.add(key);

            Reservations value = parkingSpotList.get(key);
            booking.add(value.getDate());

            for (TimeRange timeRange : value.getTimeAndUserMap().keySet()) {
                booking.add(timeRange.startTime());
                booking.add(timeRange.endTime());
                userBookings.add(booking);
            }
        }

         */
        return toReturn;

    }

    /**
     * Edits the information of a vehicle associated with a user.
     *
     * @param username
     * @param plateNumber
     * @param newPlate
     * @param newModel
     * @param newType
     * @return
     */
    public boolean editVehicleInformation(String username, String plateNumber, String newPlate, String newModel, String newType) {
        try {
            return gsonUserParser.editVehicleInformation(username, plateNumber, newPlate, newModel, newType);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a vehicle to the vehicleList of a user
     * @param username
     * @param type
     * @param model
     * @param plateNumber
     * @return
     */
    public boolean addVehicle(String username, String type, String model, String plateNumber){
        try {
            return gsonUserParser.addVehicle(username, type, model, plateNumber);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Edits the information of a user.
     *
     * @param username
     * @param firstname
     * @param lastName
     * @param phoneNumber
     * @return
     */
    public boolean editUserInformation(String username,String firstname, String lastName, String phoneNumber ){
        try {
            return gsonUserParser.editUserInformation(username,firstname,lastName,phoneNumber);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Attempts to create a new account with the provided user details.
     * @param firstName
     * @param lastName
     * @param username
     * @param phoneNumber
     * @param password
     * @return
     */
    public boolean createAccount (String firstName, String lastName, String username, String phoneNumber, String
            password){

        if (userLog.contains(username)) {
            return false;
        }
        boolean condition = gsonUserParser.createUser(firstName, lastName, username, phoneNumber, password);

        if (condition) {
            userLog.add(username.toLowerCase());
        }

        return condition;

    }

    /**
     * Retrieves the closest reservation for the given user.
     *
     * @param username The username of the user.
     * @return A list containing reservation details, or null if an exception occurs.
     */
    public List<String> getClosestReservation (String username){
        DateTime dateTime = new DateTime();
        try {

            List<String> userReservation = gsonReservationParser.getClosestReservation(username, dateTime.getDateTime(), dateTime.getTime());

            return userReservation;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * Computes the duration between reservation start and end times.
     *
     * @param userReservation
     * @return The duration of the reservation, or null if an exception occurs.
     */
    public String getDuration (List<String> userReservation){
        try {
            String duration = gsonReservationParser.computeDuration(userReservation.get(1), userReservation.get(2));
            return duration;
        } catch (Exception nullDuration) {
            return null;
        }
    }

    @Override
    public void deleteAccount(String username) throws RemoteException {
        userLog.remove(username);
        gsonUserParser.deleteUser(username);
    }

    @Override
    public boolean editPassword(String username, String password, String newPassword) throws RemoteException {
        return gsonUserParser.changePassword(username, password, newPassword);
    }

    /**
     * Retrieves all car bookings in a list of lists.
     *
     * @return List of lists of all car bookings.
     * @throws RemoteException If error or exception occurs.
     */
    @Override
    public List<List<String>> getAllCarBookings() throws RemoteException {
        return gsonReservationParser.getAllCarBookings();
    }

    /**
     * Retrieves all motor bookings in a list of lists.
     *
     * @return List of lists of all car bookings.
     * @throws RemoteException If error or exception occurs.
     */
    @Override
    public List<List<String>> getAllMotorBookings() throws RemoteException {
        return gsonReservationParser.getAllMotorBookings();
    }

    /**
     * Retrieves the current Registry.
     * @return The current Registry.
     */
    public Registry getReg() {
        return reg;
    }


}

