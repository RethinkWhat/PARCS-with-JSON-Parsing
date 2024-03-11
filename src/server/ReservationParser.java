package server;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationParser {


    DocumentBuilder builder;
    Document document;


    DateTime dateTime = new DateTime();

    HashMap<String, Integer> timeHashMap;

    final File reservationsFile = new File("src/server/res/reservations.xml");

    DateTime dateTimeFormatter = new DateTime();

    public ArrayList<String> populateTime() {
        ArrayList<String> timeArray = new ArrayList<>();
        String[] time =  {"6:00", "7:00", "8:00", "9:00",
                "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
        for (String timeItem : time)
            timeArray.add(timeItem);
        return timeArray;
    }

    /**
     * Parses the reservations file and initializes the Document object for further processing.
     * If an error occurs during parsing or configuration, appropriate exceptions are handled.
     */
    private void getReservationsFile() {
        try {
            builder = DocumentBuilderFactory.newNSInstance().newDocumentBuilder();
            document = builder.parse(reservationsFile);
            document.getDocumentElement().normalize();
        } catch (IOException | SAXException ignore) {

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to retrieve parking information
     */
    public List<ParkingSpot> getParkingInformation() {
        getReservationsFile();

        List<ParkingSpot> parkingSpotList = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("parkingSpot");

        for (int x = 0; x < nodeList.getLength(); x++) {
            // Will hold the parking spot object in each iteration
            ParkingSpot currParkingSpot = new ParkingSpot();
            Node currSpot = nodeList.item(x);

            if (currSpot.getNodeType() == Node.ELEMENT_NODE) {
                Element currSpotAsElement = (Element) currSpot;
                String currParkingSpotIdentifier = currSpotAsElement.getAttribute("identifier");

                // Set the parking identifier for curr parking spot
                currParkingSpot.setIdentifier(currParkingSpotIdentifier);

                NodeList reservationList = currSpotAsElement.getElementsByTagName("reservation");
                for (int y = 0; y< reservationList.getLength(); y++) {
                    Node currReservationNode = reservationList.item(y);

                    if (currReservationNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element currReservationAsElement = (Element) currReservationNode;

                        String date = currReservationAsElement.getAttribute("day");
                        String startTime = currReservationAsElement.getElementsByTagName("startTime").item(0).getTextContent();
                        String endTime = currReservationAsElement.getElementsByTagName("endTime").item(0).getTextContent();
                        String user = currReservationAsElement.getElementsByTagName("user").item(0).getTextContent();

                        TimeRange currReservationTimeRange = new TimeRange(startTime, endTime);
                        Reservations currReservation = new Reservations();

                        currReservation.setDate(date);
                        currReservation.getTimeAndUserMap().put(currReservationTimeRange, user);
                        currParkingSpot.getReservationsList().add(currReservation);
                    }
                }
            }
            parkingSpotList.add(currParkingSpot);
        }
        return parkingSpotList;
    }

    /**
     * This method will return a ParkingSpot's current information
     * @param identifier
     * @return
     */
    public ParkingSpot getParkingSlotInformationByIdentifier(String identifier){
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setIdentifier(identifier);

        getReservationsFile();

        NodeList nodeList = document.getElementsByTagName("parkingSpot");


        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currParkingSpotNode = nodeList.item(i);

            // Check if the current node is an element node
            if (currParkingSpotNode.getNodeType() == Node.ELEMENT_NODE) {
                Element parkingSpotElement = (Element) currParkingSpotNode;
                String currParkingSpotIdentifier = parkingSpotElement.getAttribute("identifier");

                if (currParkingSpotIdentifier.equalsIgnoreCase(identifier)) {

                    //Get the reservation nodes
                    NodeList reservationList = parkingSpotElement.getElementsByTagName("reservation");

                    for (int j = 0; j < reservationList.getLength(); j++) {
                        //Getting the current reservation node with respect to j
                        Node reservationNode = reservationList.item(j);

                        if (reservationNode.getNodeType() == Node.ELEMENT_NODE) {

                            //Casting reservation node to an Element object
                            Element reservationElement = (Element) reservationNode;

                            String date = reservationElement.getAttribute("day");
                            String startTime = reservationElement.getElementsByTagName("startTime").item(0).getTextContent();
                            String endTime = reservationElement.getElementsByTagName("endTime").item(0).getTextContent();
                            String user = reservationElement.getElementsByTagName("user").item(0).getTextContent();

                            TimeRange currReservationTimeRange = new TimeRange(startTime, endTime);
                            Reservations currReservation = new Reservations();

                            currReservation.setDate(date);
                            currReservation.getTimeAndUserMap().put(currReservationTimeRange, user);
                            parkingSpot.getReservationsList().add(currReservation);
                        }
                    }
                    break;
                }
            }
        }
        return parkingSpot;
    }

    /**
     * This method counts the total number of bookings by retrieving reservation information from the reservations file.
     * It iterates through each parking spot and sums up the sizes of their reservation lists.
     *
     * @return The total number of bookings across all parking spots.
     */
    public int countBookings() {
        getReservationsFile();
        List<ParkingSpot> parkingSpotList = getParkingInformation();
        int size = 0;

        for (ParkingSpot parkingSpot : parkingSpotList) {
            List<Reservations> reservationList =  parkingSpot.getReservationsList();
            size += reservationList.size();
        }
        return size;
    }

    /**
     * This method counts the total number of bookings made by a specific user on a given date by parsing the reservations file.
     * It iterates through each reservation in the reservations file, checks if the reservation matches the provided
     * username and date, and increments the count if a match is found.
     *
     * @param username The username of the user for whom the bookings are to be counted.
     * @param date The date for which the bookings are to be counted.
     * @return The total number of bookings made by the specified user on the given date.
     */
    public int countTotalBookingsPerDay(String username, String date) {
        getReservationsFile();
        int count = 0;
        NodeList nodeList = document.getElementsByTagName("reservation");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node reservationNode = nodeList.item(i);
            if (reservationNode.getNodeType() == Node.ELEMENT_NODE) {
                Element reservationElement = (Element) reservationNode;
                String reservationDate = reservationElement.getAttribute("day");
                String reservationUsername = reservationElement.getElementsByTagName("user").item(0).getTextContent();
                if (reservationDate.equals(date) && reservationUsername.equals(username)) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * Returns all reservation of a user with its corresponding parking slot
     * @param userName
     * @return
     */
    public Map<String, Reservations> getUserReservations(String userName) {
        getReservationsFile();

        /**
         * Key: Parking Slot Identifier
         * Value: Reservations object
         *
         * EXAMPLE:
         * {"C1", Reservations}
         */
        Map<String, Reservations> userReservations = new HashMap<>();

        NodeList nodeList = document.getElementsByTagName("parkingSpot");

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node currParkingSpotNode = nodeList.item(i);

            if (currParkingSpotNode.getNodeType() == Element.ELEMENT_NODE){

                //Getting the current parking spot
                Element currParkingSpotElement = (Element) nodeList.item(i);

                NodeList reservationList = currParkingSpotNode.getChildNodes();

                for (int j = 0; j < reservationList.getLength(); j++) {
                    Node reservationNode = reservationList.item(j);

                    if (reservationNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element currReservationElement = (Element) reservationNode;

                        //Getting the username in a certain reservation
                        String currUsername = currReservationElement.getElementsByTagName("user").item(0).getTextContent();

                        //Checks if the passed username is equals to the current reservation's username
                        if (userName.equalsIgnoreCase(currUsername)) {
                            String day = currReservationElement.getAttribute("day");
                            String startTime = currReservationElement.getElementsByTagName("startTime").item(0).getTextContent();
                            String endTime = currReservationElement.getElementsByTagName("endTime").item(0).getTextContent();

                            TimeRange currTimeRange = new TimeRange(startTime, endTime);

                            //Creating a new reservation and adding the current reservation's time range and username to the map of Reservation object
                            Reservations reservations = new Reservations();
                            reservations.getTimeAndUserMap().put(currTimeRange, currUsername);
                            reservations.setDate(day);

                            String parkingSpotIdentifier = currParkingSpotElement.getAttribute("identifier");

                            //Adding the reservations of the user to the userReservations map
                            userReservations.put(parkingSpotIdentifier, reservations);
                        }else {
                            continue;
                        }
                    }
                }
            }

        }
        return userReservations;
    }

    /**
     * Checks if a passed startTime and endTime will have a conflict in the current reservations of a user
     * true: if there are no conflicts
     * false: if there are conflicts
     * @param username
     * @param startTime
     * @param endTime
     * @return
     */
    public boolean checkScheduleConflicts(String username, String startTime, String endTime, String date){
        getReservationsFile();

        String[] startTimeParts = startTime.split(":");
        String[] endTimeParts = endTime.split(":");

        int passedStartTime = Integer.parseInt(startTimeParts[0]);
        int passedEndTime = Integer.parseInt(endTimeParts[0]);

        Element root = document.getDocumentElement();

        NodeList parkingSpotNodes = root.getElementsByTagName("parkingSpot");

        //Traverse all parking spot
        for (int i = 0; i < parkingSpotNodes.getLength(); i++){
            Element currParkingSpotElement = (Element) parkingSpotNodes.item(i);

            NodeList reservationNodes = currParkingSpotElement.getElementsByTagName("reservation");

            //Traverse all reservation nodes in the current parking spot
            for (int j = 0; j < reservationNodes.getLength(); j++){

                Element currReservationElement = (Element) reservationNodes.item(j);

                // Checks if the current reservation's username is equals to the passed username
                if (currReservationElement.getElementsByTagName("user").item(0).getTextContent().equalsIgnoreCase(username) && currReservationElement.getAttribute("day").equalsIgnoreCase(date)){

                    String[] currStartTimeParts = currReservationElement.getElementsByTagName("startTime").item(0).getTextContent().split(":");
                    String[] currEndTimeParts = currReservationElement.getElementsByTagName("endTime").item(0).getTextContent().split(":");

                    int currStartTime = Integer.parseInt(currStartTimeParts[0]);
                    int currEndTime = Integer.parseInt(currEndTimeParts[0]);

                    //Checks if the startTime and endTime of the user is not in between the current start and end time of the current reservation
                    if ((passedStartTime>=currStartTime && passedStartTime<= currEndTime) || (passedEndTime>=currStartTime && passedEndTime<=currEndTime)){
                        return false;
                    }

                }
            }
        }
        return true;
    }


    /**
     * This class retrieves the availability of a parking spot identified by its identifier on a specific date.
     * It parses the reservations file to identify all booked time ranges for the specified parking spot and date.
     *
     * @param identifier The unique identifier of the parking spot for which availability is to be checked.
     * @param date The date for which availability is to be checked.
     * @return A list of TimeRange objects representing the booked time ranges for the specified parking spot on the given date.
     */
    public List<TimeRange> getParkingSpotAvailability(String identifier, String date){
        getReservationsFile();

        //This will have a list of time ranges that is booked in a certain date and a certain parking spot identifier
        List<TimeRange> bookedTimeRange = new ArrayList<>();

        Element root = document.getDocumentElement();

        NodeList parkingSpotNodes = root.getElementsByTagName("parkingSpot");

        //Iterate to all parkingSpot nodes
        for (int i = 0; i < parkingSpotNodes.getLength(); i++){

            Element currParkingSpotElement = (Element) parkingSpotNodes.item(i);

            //Checks if the current parking spot node has an identifier similar to the identifier passed as an argument
            if (currParkingSpotElement.getAttribute("identifier").equalsIgnoreCase(identifier)){

                NodeList reservationNodes = currParkingSpotElement.getElementsByTagName("reservation");

                for (int j = 0; j < reservationNodes.getLength(); j++){
                    Element currReservationElement = (Element) reservationNodes.item(j);

                    //Checks if current date of a reservation is equals to the date passed as an argument
                    if (currReservationElement.getAttribute("day").equalsIgnoreCase(date)){

                        TimeRange validTimeRange = new TimeRange(currReservationElement.getElementsByTagName("startTime").item(0).getTextContent(), currReservationElement.getElementsByTagName("endTime").item(0).getTextContent());

                        //Adds the TimeRange of the reservation to the booked time ranges in a certain parking spot identifier
                        bookedTimeRange.add(validTimeRange);
                    }
                }

            }
        }
        return bookedTimeRange;
    }

    /**
     * This method will create a reservation node for the xml file
     * If the passed identifier already exists, it will add a reservation under that identifier
     * Else, it will create a new parking spot in the xml file with the new reservation
     * @param identifier
     * @param date
     * @param startTime
     * @param duration
     * @param username
     */
    public void createReservationNode(String identifier, String date, String startTime, String duration, String username){
        getReservationsFile();

        Element root = document.getDocumentElement();

        NodeList parkingSpotNodes = root.getElementsByTagName("parkingSpot");

        Element reservationElement = document.createElement("reservation");
        reservationElement.setAttribute("day", date);

        Element startTimeElement = document.createElement("startTime");
        startTimeElement.setTextContent(startTime);
        reservationElement.appendChild(startTimeElement);

        Element endTimeElement = document.createElement("endTime");
        String[] timeParts = startTime.split(":");
        int endTime = Integer.parseInt(timeParts[0]) + Integer.parseInt(duration);
        endTimeElement.setTextContent(Integer.toString(endTime) + ":00");
        reservationElement.appendChild(endTimeElement);

        Element usernameElement = document.createElement("user");
        usernameElement.setTextContent(username);
        reservationElement.appendChild(usernameElement);

        // Searching if the passed identifier already exists
        for (int i = 0; i < parkingSpotNodes.getLength(); i++){
            Element currParkingSpotElement = (Element) parkingSpotNodes.item(i);

            //If the passed identifier already exists, it will add that reservation under the passed identifier
            if (currParkingSpotElement.getAttribute("identifier").equalsIgnoreCase(identifier)){
                currParkingSpotElement.appendChild(reservationElement);
                transform();
                return;
            }
        }

        // This adds a new parking spot with the reservation node if the passed identifier does not exist
        Element parkingSpotElement = document.createElement("parkingSpot");
        parkingSpotElement.setAttribute("identifier", identifier);
        parkingSpotElement.appendChild(reservationElement);
        root.appendChild(parkingSpotElement);
        transform();
    }

    /**
     * A transformer that will write into the reservationsFile
     */
    private void transform(){
        DOMSource source = new DOMSource(document);

        try{

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            StreamResult result = new StreamResult(reservationsFile);

            transformer.transform(source,result);

        }catch (TransformerException te){
            te.printStackTrace();
        }
    }

    /**
     * This method retrieves the available time slots for booking a parking spot on a specified date and duration.
     * @param date The date for which available time slots are to be checked.
     * @param duration The duration for which the parking spot is required, in hours.
     * @param identifier The unique identifier of the parking spot for which availability is to be checked.
     * @return A list of available time slots for booking the parking spot on the specified date and duration.
     */
    public List<String> availableTime(String date, String duration, String identifier) {
        ArrayList<String> timeArray = populateTime();
        DateTime dateTime = new DateTime();
        List<TimeRange> bookedTimeRange = getParkingSpotAvailability(date, identifier);


        List<String> allBookings = new ArrayList<>();
        List<String> allTime = new ArrayList<>(timeArray);
        List<String> toReturnTime = new ArrayList<>();
        int durationAsInt = Integer.parseInt(duration);

        for (TimeRange timeRange : bookedTimeRange) {
            allBookings.addAll(timeRange.getStartToEndTime());
        }

        for (String time : allTime) {
            boolean isAvailable = true;

            for (int i = 0; i < durationAsInt; i++) {
                if (allBookings.contains(dateTime.addDuration(time, i)) || (Integer.parseInt(dateTime.addDuration(time, i).split(":")[0]) + durationAsInt) > 18) {
                    isAvailable = false;
                    break;  // Break if any of the incremented times is booked
                }
            }

            if (isAvailable) {
                toReturnTime.add(time);
            }
        }

        return toReturnTime;
    }

    /**
     * Computes the end time based on the start time and duration provided.
     *
     * @param startTime The start time in HH:mm format.
     * @param duration The duration in hours.
     * @return The end time in HH:mm format.
     */
    public String computeEndTime(String startTime, String duration) {
        String[] startTimeParts = startTime.split(":");
        int endTime = Integer.parseInt(startTimeParts[0]) + Integer.parseInt(duration);
        return endTime+":00";
    }

    /**
     * Computes the duration between the start time and end time provided.
     *
     * @param startTime The start time in HH:mm format.
     * @param endTime The end time in HH:mm format.
     * @return The duration between the start and end time in hours.
     */
    public String computeDuration(String startTime, String endTime){
        List<Integer> duration = new ArrayList<>();

        String[] startTimeParts = startTime.split(":");
        String[] endTimeParts = endTime.split(":");

        int totalHours = Integer.parseInt(endTimeParts[0]) - Integer.parseInt(startTimeParts[0]);

        duration.add(totalHours);
        duration.add(00);

        return String.valueOf(totalHours);
    }

    /**
     * Method that returns the information for the closest reservation of a user
     * [parkingIdentifier, startTime, endTime, date]
     *
     * @param username
     * @return
     */
    public List<String> getClosestReservation(String username, String currentTime){
        getReservationsFile();

        List<String> reservationInformation = new ArrayList<>();
        reservationInformation.add("C0");
        reservationInformation.add("24:00");
        reservationInformation.add("00:00");
        reservationInformation.add("default");

        String[] currentTimeParts = currentTime.split(":");

        Element root = document.getDocumentElement();

        NodeList parkingSpotNodes = root.getElementsByTagName("parkingSpot");

        for (int i = 0; i < parkingSpotNodes.getLength(); i++){
            Element currParkingSpotElement = (Element) parkingSpotNodes.item(i);

            NodeList reservationNodes = currParkingSpotElement.getElementsByTagName("reservation");

            for (int j = 0; j < reservationNodes.getLength(); j++){

                Element currReservationElement = (Element) reservationNodes.item(j);

                // Splits the startTime string to an array because we only need the hour
                String[] currStartTimeParts = currReservationElement.getElementsByTagName("startTime").item(0).getTextContent().split(":");
                String[] currEndTimeParts = currReservationElement.getElementsByTagName("endTime").item(0).getTextContent().split(":");

                // Checks if the current reservation is by the user
                // If the current reservation element's startTime is less than the reservation element startTime stored in the reservationInformation arraylist, it will replace its values
                // It checks if the current time passed is not greater than the endTime of the current reservation
                if (currReservationElement.getElementsByTagName("user").item(0).getTextContent().equalsIgnoreCase(username) && compareStartTime(currStartTimeParts[0], reservationInformation.get(1)) && !(Integer.parseInt(currentTimeParts[0]) >= Integer.parseInt(currEndTimeParts[0]))){
                    reservationInformation.set(0, currReservationElement.getParentNode().getAttributes().item(0).getTextContent());
                    reservationInformation.set(1, currReservationElement.getElementsByTagName("startTime").item(0).getTextContent());
                    reservationInformation.set(2, currReservationElement.getElementsByTagName("endTime").item(0).getTextContent());
                    reservationInformation.set(3, currReservationElement.getAttributes().item(0).getTextContent());
                }
            }
        }
        return reservationInformation;
    }

    /**
     * Will return true if the first startTime is less than the second startTime
     * @param st1
     * @param st2
     * @return
     */
    private boolean compareStartTime(String st1, String st2){
        String[] st1Parts = st1.split(":");
        String[] st2Parts = st2.split(":");

        if (Integer.parseInt(st1Parts[0]) < Integer.parseInt(st2Parts[0])){
            return true;
        }

        return false;
    }

    /**
     * Returns all the current car booking reservations
     * FORMAT: [username, parking identifier, date, start time, end time, duration]
     * @return
     */
    public List<List<String>> getAllCarBookings(){
        getReservationsFile();
        List<List<String>> carBookings = new ArrayList<>();

        Element root = document.getDocumentElement();

        NodeList parkingSpotNodes = root.getElementsByTagName("parkingSpot");

        //This will traverse all parking spot nodes
        for (int i = 0; i < parkingSpotNodes.getLength(); i++){

            Element currParkingSpotElement = (Element) parkingSpotNodes.item(i);

            if (currParkingSpotElement.getAttribute("identifier").startsWith("C")){

                NodeList reservationNodes = currParkingSpotElement.getElementsByTagName("reservation");

                for (int j = 0; j < reservationNodes.getLength(); j++){

                    Element currReservationElement = (Element) reservationNodes.item(j);


                    String currUser = currReservationElement.getElementsByTagName("user").item(0).getTextContent();
                    String currIdentifier = currParkingSpotElement.getAttribute("identifier");
                    String currDate = currReservationElement.getAttribute("day");
                    String currStartTime = currReservationElement.getElementsByTagName("startTime").item(0).getTextContent();
                    String currEndTime = currReservationElement.getElementsByTagName("endTime").item(0).getTextContent();

                    String[] currStartTimeParts = currStartTime.split(":");
                    String[] currEndTimeParts = currEndTime.split(":");
                    String duration = Integer.toString(Integer.parseInt(currEndTimeParts[0]) - Integer.parseInt(currStartTimeParts[0]));

                    //This will add the current reservation element to the list of all car bookings
                    List<String> currReservation = new ArrayList<>();
                    currReservation.add(currUser);
                    currReservation.add(currIdentifier);
                    currReservation.add(currDate);
                    currReservation.add(currStartTime);
                    currReservation.add(currEndTime);
                    currReservation.add(duration);

                    carBookings.add(currReservation);
                }

            }

        }

        return carBookings;
    }

    /**
     * Returns all the current motor booking reservations
     * FORMAT: [username, parking identifier, date, start time, end time, duration]
     * @return
     */
    public List<List<String>> getAllMotorBookings(){
        getReservationsFile();
        List<List<String>> motorBookings = new ArrayList<>();

        Element root = document.getDocumentElement();

        NodeList parkingSpotNodes = root.getElementsByTagName("parkingSpot");

        for (int i = 0; i < parkingSpotNodes.getLength(); i++){

            Element currParkingSpotElement = (Element) parkingSpotNodes.item(i);

            if (currParkingSpotElement.getAttribute("identifier").startsWith("M")){

                NodeList reservationNodes = currParkingSpotElement.getElementsByTagName("reservation");

                for (int j = 0; j < reservationNodes.getLength(); j++){

                    Element currReservationElement = (Element) reservationNodes.item(j);


                    String currUser = currReservationElement.getElementsByTagName("user").item(0).getTextContent();
                    String currIdentifier = currParkingSpotElement.getAttribute("identifier");
                    String currDate = currReservationElement.getAttribute("day");
                    String currStartTime = currReservationElement.getElementsByTagName("startTime").item(0).getTextContent();
                    String currEndTime = currReservationElement.getElementsByTagName("endTime").item(0).getTextContent();

                    String[] currStartTimeParts = currStartTime.split(":");
                    String[] currEndTimeParts = currEndTime.split(":");
                    String duration = Integer.toString(Integer.parseInt(currEndTimeParts[0]) - Integer.parseInt(currStartTimeParts[0]));

                    //This will add the current reservation element as a list to the lists of motor bookings
                    List<String> currReservation = new ArrayList<>();
                    currReservation.add(currUser);
                    currReservation.add(currIdentifier);
                    currReservation.add(currDate);
                    currReservation.add(currStartTime);
                    currReservation.add(currEndTime);
                    currReservation.add(duration);

                    motorBookings.add(currReservation);
                }

            }

        }
        return motorBookings;
    }

    /**
     * Returns a list of reservations ahead of the passed date
     * [username, parking identifier, date, start time, end time, duration]
     * @param date
     * @return
     */
    public List<List<String>> getFutureReservations(String date) throws ParseException {
        getReservationsFile();

        //Formats the passed date to a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date passedDate = dateFormat.parse(date);

        List<List<String>> futureReservations = new ArrayList<>();

        Element root = document.getDocumentElement();

        NodeList parkingSpotNodes = root.getElementsByTagName("parkingSpot");

        for (int i = 0; i < parkingSpotNodes.getLength(); i++){
            Element currParkingSpotElement = (Element) parkingSpotNodes.item(i);

            NodeList reservationNodes = currParkingSpotElement.getElementsByTagName("reservation");

            for (int j = 0; j < reservationNodes.getLength(); j++){
                Element currReservationElement = (Element) reservationNodes.item(j);

                Date currReservationDate = dateFormat.parse(currReservationElement.getAttribute("day"));

                //Check if current reservation's date is ahead of the passed date
                if (currReservationDate.after(passedDate)){

                    String username = currReservationElement.getElementsByTagName("user").item(0).getTextContent();
                    String identifier = currParkingSpotElement.getAttribute("identifier");
                    String reservationDate = currReservationElement.getAttribute("day");
                    String startTime = currReservationElement.getElementsByTagName("startTime").item(0).getTextContent();
                    String endTime = currReservationElement.getElementsByTagName("endTime").item(0).getTextContent();
                    String duration = computeDuration(startTime, endTime);

                    List<String> currReservationDetails = new ArrayList<>();
                    currReservationDetails.add(username);
                    currReservationDetails.add(identifier);
                    currReservationDetails.add(reservationDate);
                    currReservationDetails.add(startTime);
                    currReservationDetails.add(endTime);
                    currReservationDetails.add(duration);

                    futureReservations.add(currReservationDetails);
                }
            }
        }
        return futureReservations;
    }

    /**
     * This method checks if the chosen parking spot, date, starting time, and duration of the user will have a conflict to other reservations of other users
     * TRUE: conflicts exist
     * FALSE: conflicts do not exist
     * @param date
     * @param startTime
     * @param duration
     * @return
     */
    public boolean hasSchedulingConflicts(String identifier, String date, String startTime, String duration){
        getReservationsFile();

        String endTime = computeEndTime(startTime, duration);

        Element root = document.getDocumentElement();

        NodeList parkingSpotNodes = root.getElementsByTagName("parkingSpot");

        for (int i = 0; i < parkingSpotNodes.getLength(); i++){
            Element currParkingSpotElement = (Element) parkingSpotNodes.item(i);

            if (currParkingSpotElement.getAttribute("identifier").equalsIgnoreCase(identifier)){

                NodeList reservationNodes = currParkingSpotElement.getElementsByTagName("reservation");

                for (int j = 0; j < reservationNodes.getLength(); j++){
                    Element currReservationElement = (Element) reservationNodes.item(j);

                    if (currReservationElement.getAttribute("day").equalsIgnoreCase(date)){

                        String[] resStartTime = currReservationElement.getElementsByTagName("startTime").item(0).getTextContent().split(":");
                        String[] resEndTime = currReservationElement.getElementsByTagName("endTime").item(0).getTextContent().split(":");

                        String[] userStartTime = startTime.split(":");
                        String[] userEndTime = endTime.split(":");

                        //Checks if chosen startTime is inside a reservation timeframe
                        if (Integer.parseInt(userStartTime[0]) >= Integer.parseInt(resStartTime[0]) && Integer.parseInt(userStartTime[0]) < Integer.parseInt(resEndTime[0])){
                            return true;
                        }

                        //Checks if chosen endTie is inside a reservation timeframe
                        if (Integer.parseInt(userEndTime[0]) > Integer.parseInt(resStartTime[0]) && Integer.parseInt(userEndTime[0]) <= Integer.parseInt(resEndTime[0])){
                            return true;
                        }

                        //Checks if there's a reservation inside/between the chosen startTime and endTime
                        if (Integer.parseInt(resStartTime[0]) >= Integer.parseInt(userStartTime[0]) && Integer.parseInt(resEndTime[0]) <= Integer.parseInt(userEndTime[0])){
                            return true;
                        }
                    }
                }
            }

        }

        return false;
    }
}
