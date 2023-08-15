package booking;


import api.ApiPost;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class extends the NewBooking class
 */
public class NewHomeBooking extends NewBooking {

    //Private attributes
    private boolean hasTest;

    //Constructor

    /**
     * This class creates a new home booking
     * @param customerId - ID of customer for whom booking is being made
     * @param testingSiteId - ID of testing site at which booking is made
     * @param startTime - Date of testing
     * @param hasTest - boolean showing if user already has a test kit or not
     */
    public NewHomeBooking(String customerId, String testingSiteId, String startTime, boolean hasTest){
        super(customerId, testingSiteId, startTime, "RAT Test");
        setHasTest(hasTest);
    }

    //Method to create a new booking when the user is having the test at home
    /**
     * This method creates a home booking and prints the PIN of the booking
     */
    public void createBooking(){
        //String to be serialised and passed into API call
        String jsonString;
        String notes = getNotes();
        if (hasTest) {
            notes += " - User already has testing kit";
        }
        jsonString = "{" +
                "\"customerId\":\"" + getCustomerId() + "\"," +
                "\"testingSiteId\":\"" + getTestingSiteId() + "\"," +
                "\"startTime\":\"" + getStartTime() + "\"," +
                "\"notes\":\"" + notes + "\"," +
                "\"additionalInfo\":" + "{" +
                "\"testType\":\"" + "RAT Test" + "\","+
                "\"qrCode\":\"" + "This is a dummy QR code" + "\","+
                "\"url\":\"" + "www.thisisadummmyurl.com" + "\","+
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

    //Getter and Setter
    /**
     * A method used to return if test is available
     * @return A boolean representing if test is available
     */
    public boolean isHasTest() {
        return hasTest;
    }

    /**
     * A method used to set if test is available
     * @param hasTest A boolean representing if test is available
     */
    public void setHasTest(boolean hasTest) {
        this.hasTest = hasTest;
    }
}
