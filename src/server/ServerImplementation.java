package server;

import shared.ServerMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class ServerImplementation extends UnicastRemoteObject implements ServerMessage {

    /**Temporary, while JSON not implemented */
    UserParser userParser = new UserParser();
    ReservationParser reservationParser = new ReservationParser();

    protected ServerImplementation() throws RemoteException {
    }


    @Override
    public boolean login(String username, String password) throws RemoteException {

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
}
