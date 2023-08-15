package booking;


import api.ApiPost;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class extends the NewBooking class and creates a new booking for on-site testing
 */
public class NewOnSiteBooking extends NewBooking {

    //Constructor
    /**
     * Constructor for NewOnSiteBooking
     * @param customerId A string representing customerID
     * @param testingSiteId A string representing testingSiteId
     * @param startTime A string representing startTime
     * @param notes A string representing notes
     */
    public NewOnSiteBooking(String customerId, String testingSiteId, String startTime, String notes){
        super(customerId, testingSiteId, startTime, notes);
    }

    //Method to create a new booking
    /**
     * This method creates an on-site booking and prints the PIN of the booking
     */
    public void createBooking() {
        //String to be serialised and passed into API call
        String jsonString = "{" +
                "\"customerId\":\"" + getCustomerId() + "\"," +
                "\"testingSiteId\":\"" + getTestingSiteId() + "\"," +
                "\"startTime\":\"" + getStartTime() + "\"," +
                "\"notes\":\"" + getNotes()  + "\"," +
                "\"additionalInfo\":" + "{" +
                "\"changesCount\":\"" + "0" + "\","+
                "\"change1\":\"" + "" + "\","+
                "\"change2\":\"" + "" + "\","+
                "\"change3\":\"" + "" + "\""+
                "}" +
                "}";

        //Making the API call
        ObjectNode jsonNode = null;
        try{
            jsonNode = ApiPost.getInstance(jsonString, "booking");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("The PIN of your booking is: " + jsonNode.get("smsPin").textValue());

    }

}
