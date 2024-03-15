package server;

import shared.ServerMessage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

 public class ServerImplementation extends UnicastRemoteObject implements ServerMessage {

    /**
     * Temporary, while JSON not implemented
     */
    UserParser userParser = new UserParser();
    ReservationParser reservationParser = new ReservationParser();
    ArrayList<String> userLog = new ArrayList<String>();


    /** JSON Parsers */
    GsonUserParser gsonUserParser = new GsonUserParser();
    GsonReservationParser gsonReservationParser = new GsonReservationParser();

    protected ServerImplementation() throws RemoteException {
    }


    @Override
    public boolean login(String username, String password) {
        return gsonUserParser.validateLogin(username, password);
    }

     @Override
     public boolean isUserLoggedIn(String username) throws RemoteException {
         if (!userLog.contains(username.toLowerCase())) {
             userLog.add(username);
             return false;
         }
         return true;
     }

     public String getFullName(String username) {
        return userParser.getUserFullName(username);
    }

    public int getUserTotalBookings(String username) {
        DateTime dateTime = new DateTime();
        return reservationParser.countTotalBookingsPerDay(username, dateTime.getDateTime());
    }


    public Map<String, List<String>> getUserVehicles(String username) {
        return userParser.getUserVehicles(username);
    }

    public List<String> spotTimeAvailable(String identifier, String duration, String date) {
        return reservationParser.availableTime(identifier, duration, date);
    }

    public boolean bookReservation(String identifier, String date, String startTime, String duration, String username) {
        boolean check = !reservationParser.hasSchedulingConflicts(identifier, date, startTime, duration);
        if (check) {
            reservationParser.createReservationNode(identifier, date, startTime, duration, username);
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
        Map<String,Reservations> parkingSpotList = reservationParser.getUserReservations(username);

        ArrayList userBookings = new ArrayList<>();
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
            }
            userBookings.add(booking);
        }
        return userBookings;

    }

     public boolean editUserInformation(String username,String firstname, String lastName, String contactNo){
        try {
            userParser.editUserInfo(username,"firstName", firstname);
            userParser.editUserInfo(username, "lastName", lastName);
            userParser.editUserInfo(username, "contactNumber", contactNo);
        }catch (Exception e) {
           e.printStackTrace();
        }
         return false;
     }
     public boolean editVehicleInfo(String username,String plateNumber, String newInfo){
        try{
            userParser.editVehicle(username,plateNumber,newInfo);

        }catch (Exception e){
            e.printStackTrace();
        }
         return false;
     }

     public boolean createAccount (String firstName, String lastName, String username, String phoneNumber, String
                password){
            userParser.createUser(username, "user", password, lastName, firstName, phoneNumber, null);
            return true;

        }

    public List<String> getClosestReservation (String username){
        DateTime dateTime = new DateTime();
        try {

            List<String> userReservation = reservationParser.getClosestReservation(username, dateTime.getTime());

            return userReservation;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public String getDuration (List<String> userReservation){
        try {
            String duration = reservationParser.computeDuration(userReservation.get(1), userReservation.get(2));

            return duration;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

     @Override
    public void deleteAccount(String firstname) throws RemoteException {
        userParser.deleteUser(firstname);
    }
    
    @Override
    public boolean editPassword(String newPassword) throws RemoteException {
        return false;
    }
}

