package booking;


import api.ApiDelete;
import api.ApiPatch;
import com.fasterxml.jackson.databind.node.ObjectNode;
import testingsites.ModifyTestingSite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This call modifies bookings made prior
 */
public class BookingManager {
    //Private attributes
    private String bookingId;

    //Constructor
    /**
     * Constructor used to create new objects of class ModifyBooking
     * @param bookingId A string representing a new bookingID
     */
    public BookingManager(String bookingId){
        setBookingId(bookingId);
    }

    //Method to modify additionalInfo field of testing sites
    /**
     * A method that updates existing bookings. It calls the ApiPatch.getInstance() which makes the necessary API call.
     */
    public void updateBooking(String fieldName, String newValue){
        //String to be serialised and passed into API
        String jsonString = "{" +
                "\"additionalInfo\":" + "{" +
                "\""+ fieldName +"\":\"" + newValue +"\""+
                "}" +
                "}";
        //Making the API call
        String patchUrl = "booking/" + bookingId;
        try{
            int i = ApiPatch.getInstance(jsonString, patchUrl);
            System.out.println(i);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void updateTimeStamp(String update){
        GetBookings getBookings = new GetBookings();
        ArrayList<ObjectNode> booking = getBookings.getBookingsByField("id", "", bookingId);
        int count;
        String change1 = "";
        String change2 = "";
        String change3 = "";
        try {
            count = booking.get(0).get("additionalInfo").get("changesCount").asInt();
            change1 = booking.get(0).get("additionalInfo").get("change1").textValue();
            change2 = booking.get(0).get("additionalInfo").get("change2").textValue();
            change3 = booking.get(0).get("additionalInfo").get("change3").textValue();
        }
        catch(Exception e){
            count = 0;
            change1 = "";
            change2 = "";
            change3 = "";
        }
        String jsonString = null;
        if (count == 0){
            count++;
            jsonString = "{" +
                    "\"additionalInfo\":" + "{" +
                    "\"changesCount\":\"" + count + "\","+
                    "\"change1\":\"" + update + "\","+
                    "\"change2\":\"" + change2 + "\","+
                    "\"change3\":\"" + change3 + "\""+
                    "}" +
                    "}";
        }
        else{
            if (count == 1){
                count++;
                jsonString = "{" +
                        "\"additionalInfo\":" + "{" +
                        "\"changesCount\":\"" + count + "\","+
                        "\"change1\":\"" + change1 + "\","+
                        "\"change2\":\"" + update + "\","+
                        "\"change3\":\"" + change3 + "\""+
                        "}" +
                        "}";
            }
            else{
                if (count == 2) {
                    count++;
                    jsonString = "{" +
                            "\"additionalInfo\":" + "{" +
                            "\"changesCount\":\"" + count + "\"," +
                            "\"change1\":\"" + change1 + "\"," +
                            "\"change2\":\"" + change2 + "\"," +
                            "\"change3\":\"" + update + "\"" +
                            "}" +
                            "}";
                }
                else{
                    count = 1;
                    jsonString = "{" +
                            "\"additionalInfo\":" + "{" +
                            "\"changesCount\":\"" + count + "\"," +
                            "\"change1\":\"" + update + "\"," +
                            "\"change2\":\"" + change2 + "\"," +
                            "\"change3\":\"" + change3 + "\"" +
                            "}" +
                            "}";
                }
            }
        }
        //Making the API call
        String patchUrl = "booking/" + bookingId;
        try{
            int i = ApiPatch.getInstance(jsonString, patchUrl);
            System.out.println(i);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * This method allows the system's user to update a Booking's testing date
     * @param newDate - The newDate of the booking
     */
    public void updateBookingTime(String newDate){
        GetBookings getBookings = new GetBookings();
        String testDate = getBookings.getStartDate(bookingId);
        ArrayList<ObjectNode> booking = getBookings.getBookingsByField("id", "", bookingId);
        Date currentDate = new Date();
        Date bookingDate = null;
        try {
            bookingDate = new SimpleDateFormat("yyyy-MM-dd").parse(testDate);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (bookingDate.compareTo(currentDate) > 0) {
            //String to be serialised
            String jsonString = "{" +
                    "\"startTime\":\"" + newDate  + "\"" +
                    "}";

            //Making the API call
            String patchUrl = "booking/" + bookingId;
            try{
                int i = ApiPatch.getInstance(jsonString, patchUrl);
                System.out.println("Your Booking time has been successfully updated!");
                updateTimeStamp(testDate);
                String update =  booking.get(0).get("customer").get("givenName").textValue() +
                        booking.get(0).get("customer").get("familyName").textValue() +
                        " has updated the time of his booking (ID: " + bookingId + ") from " +
                        testDate + " to " + newDate + " __ ";
                String testingSiteId = booking.get(0).get("testingSite").get("id").textValue();
                ModifyTestingSite modifyTestingSite = new ModifyTestingSite();
                modifyTestingSite.updateTestingSiteInfo(testingSiteId, update);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("This booking is overdue, please create a new booking.");
        }
    }


    /**
     * This method allows a system's user to update a Booking's venue (testing centre)
     * @param newVenueId - The ID of the new Venue
     */
    public void updateBookingVenue(String newVenueId){
        GetBookings getBookings = new GetBookings();
        String testDate = getBookings.getStartDate(bookingId);
        ArrayList<ObjectNode> booking = getBookings.getBookingsByField("id", "", bookingId);
        String oldVenueId = booking.get(0).get("testingSite").get("id").textValue();
        Date currentDate = new Date();
        Date bookingDate = null;
        try {
            bookingDate = new SimpleDateFormat("yyyy-MM-dd").parse(testDate);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (bookingDate.compareTo(currentDate) > 0) {
            //String to be serialised
            String jsonString = "{" +
                    "\"testingSiteId\":\"" + newVenueId + "\"" +
                    "}";

            //Making the API call
            String patchUrl = "booking/" + bookingId;
            try {
                int i = ApiPatch.getInstance(jsonString, patchUrl);
                System.out.println("Your Booking venue has been successfully updated!");
                updateTimeStamp(oldVenueId);
                String update = booking.get(0).get("customer").get("givenName").textValue() +
                        booking.get(0).get("customer").get("familyName").textValue() +
                        " has updated the venue of his booking (ID: " + bookingId + ") from " +
                        oldVenueId + " to " + newVenueId + " __ ";
                ModifyTestingSite modifyTestingSite = new ModifyTestingSite();
                modifyTestingSite.updateTestingSiteInfo(oldVenueId, update);
                modifyTestingSite.updateTestingSiteInfo(newVenueId, update);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("This booking is overdue, please create a new booking.");
        }

    }

    /**
     * The method deleteBookingById is used to delete a booking using the ID. It calls ApiDelete.getInstance which
     * makes the API call
     */
    public void deleteBookingById(){
        //Making the API call
        String deleteUrl = "booking/" + bookingId;
        try{
            ApiDelete.getInstance(deleteUrl);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void cancelBooking(){
        updateBooking("status", "cancelled");
        GetBookings getBookings = new GetBookings();
        ArrayList<ObjectNode> booking = getBookings.getBookingsByField("id", "", bookingId);
        String update = booking.get(0).get("customer").get("givenName").textValue() +
                booking.get(0).get("customer").get("familyName").textValue() +
                " has cancelled his booking (ID: " + bookingId + ")" + " __ ";
        String testingSiteId = booking.get(0).get("testingSite").get("id").textValue();
        ModifyTestingSite modifyTestingSite = new ModifyTestingSite();
        modifyTestingSite.updateTestingSiteInfo(testingSiteId, update);
    }

    //Setter
    /**
     * A method that sets a new booking ID
     * @param bookingId A string that represents a new booking ID
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

}
