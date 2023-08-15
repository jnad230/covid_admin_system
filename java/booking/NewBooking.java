package booking;

/**
 * This abstract class is inherited by NewHomeBooking and NewOnSiteBooking classes
 */
public abstract class NewBooking {
    //Private attributes
    private String customerId;
    private String testingSiteId;
    private String startTime;
    private String notes;

    /**
     * The constructor to create new instances of class NewBooking
     * @param customerId A string representing customerID
     * @param testingSiteId A string representing testingSiteId
     * @param startTime A string representing startTime
     * @param notes A string representing notes
     */
    public NewBooking(String customerId, String testingSiteId, String startTime, String notes){
        setCustomerId(customerId);
        setTestingSiteId(testingSiteId);
        setStartTime(startTime);
        setNotes(notes);
    }

    /**
     * This method is implemented by the child classes
     */
    public abstract void createBooking();

    //Getters and Setters
    /**
     * A method that returns the customer ID
     * @return A string representing the customer ID
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * A method that sets a new customer ID
     * @param customerId A string representing a new customerID
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * A method that returns the siteID
     * @return A string representing a siteID
     */
    public String getTestingSiteId() {
        return testingSiteId;
    }

    /**
     * A method that sets new testingSiteId
     * @param testingSiteId A string representing a new testingSiteId
     */
    public void setTestingSiteId(String testingSiteId) {
        this.testingSiteId = testingSiteId;
    }

    /**
     * A method that returns the start time
     * @return A string that represents the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * A method that sets a new startTime
     * @param startTime A string representing  a new startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * A method that returns notes
     * @return A string representing notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * A method to set new notes
     * @param notes A string representing new notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
