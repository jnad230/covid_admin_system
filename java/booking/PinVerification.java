package booking;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class verifies if a pin is valid
 */
public class PinVerification {

    //Private attributes
    private String pin;

    //Constructor
    /**
     * Constructor for class PinVerification
     * @param pin A string representing a pin
     */
    public PinVerification(String pin){
        setPin(pin);
    }

    //Method to verify pin

    /**
     * This class verifies the provided pin and return its instance of booking
     * @return bookingNode - Booking object of PIN provided
     */
    public ObjectNode verifyPin(){
        GetBookings getBookings = null;
        try {
            getBookings = new GetBookings();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        ObjectNode[] bookings = null;
        ObjectNode bookingNode = null;
        try {
            bookings = getBookings.getAllBookings();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        for (ObjectNode x : bookings){
            if (x.get("smsPin").textValue().equalsIgnoreCase(pin)){
                bookingNode = x;
                break;
            }
        }
        return bookingNode;
    }

    /**
     * A method that sets a new pin
     * @param pin A string that represents a new pin
     */
    public void setPin(String pin) {
        this.pin = pin;
    }
}
