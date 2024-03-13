package server;

import shared.ServerMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServerImplementation extends UnicastRemoteObject implements ServerMessage {

    /**Temporary, while JSON not implemented */
    UserParser userParser = new UserParser();
    ReservationParser reservationParser = new ReservationParser();
    ArrayList<String> userLog = new ArrayList<String>();

    protected ServerImplementation() throws RemoteException {
    }


    @Override
    public boolean login(String username, String password) {

        Map<String, String> accounts = userParser.getUserLoginCredentials();
        String associatedPass = accounts.getOrDefault(username, "");
        boolean authenticateLogin = password.equals(associatedPass);
        System.out.println(authenticateLogin);

        /*
        if (authenticateLogin) {
            if (server.getUserLog().contains(username)) {
                writer.println("userExists");
            }else {
                server.accountLogin(username);
                writer.println("true");
            }
        }
        else
            writer.println("false");
    }
         */
        return authenticateLogin;
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

    public boolean logout(String username){
        try{
            userLog.remove(username);
            return true;
        }catch (Exception e){
            return false;
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

    public boolean createAccount(String firstName, String lastName, String username, String phoneNumber, String password) {
            userParser.createUser(username, "user", password, lastName, firstName, phoneNumber, null);
            return true;
    }
}
