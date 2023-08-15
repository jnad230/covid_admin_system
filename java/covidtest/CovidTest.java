package covidtest;

import api.ApiPost;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

/**
 * This class creates and saves the result of a covid test
 */
public class CovidTest {

    //Private attributes
    private String testType;
    private String patientId;
    private String administererId;
    private String bookingId;
    private String result;
    private String status;
    private String notes;

    //constructor
    /**
     * The constructor creates new instance of the class CovidTest
     * @param testType A string representing the type of covid test
     * @param patientId A string representing the ID of the patient
     * @param administererId A string representing the ID of the administerer
     * @param bookingId A string representing the booking ID
     * @param result A string to represent the result
     * @param status A string representing the status of the test
     * @param notes A string representing any notes that are required
     */
    public CovidTest(String testType, String patientId, String administererId, String bookingId, String result, String status, String notes){
        setTestType(testType);
        setPatientId(patientId);
        setAdministererId(administererId);
        setBookingId(bookingId);
        setResult(result);
        setStatus(status);
        setNotes(notes);
    }

    //Method to create test
    /**
     * The method createTest allows to create a string which concatenates all the details of the covid test.
     * It then calls the ApiPost.getInstance method which makes the required API call
     */
    public void createTest(){
        //Creating String to be serialised
        String jsonString = "{" +
                "\"type\":\"" + testType + "\"," +
                "\"patientId\":\"" + patientId + "\"," +
                "\"administererId\":\"" + administererId + "\"," +
                "\"bookingId\":\"" + bookingId + "\"," +
                "\"result\":\"" + result + "\"," +
                "\"status\":\"" + status + "\"," +
                "\"notes\":\"" + notes + "\"" +
                "}";

        //Making the API call
        ObjectNode jsonNode = null;
        try{
            jsonNode = ApiPost.getInstance(jsonString, "covid-test");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Getters and Setters
    /**
     * The method getTestType returns a string representing the testType
     * @return A string representing a testType
     */
    public String getTestType() {
        return testType;
    }

    /**
     * The method setTestType allows the setting of a new testType
     * @param testType A string representing a new testType
     */
    public void setTestType(String testType) {
        this.testType = testType;
    }

    /**
     * The method getPatientId returns a string representing the patient ID
     * @return A string representing a patient ID
     *
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * The method setPatientId allows the setting of a new patientId
     * @param patientId A string representing a new patientId
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * The method returns a string representing the administerer ID
     * @return A string representing a administerer ID
     */
    public String getAdministererId() {
        return administererId;
    }

    /**
     * A method which sets a new administerer ID
     * @param administererId A string representing a new administerer ID
     */
    public void setAdministererId(String administererId) {
        this.administererId = administererId;
    }

    /**
     * A method that returns the booking ID
     * @return A string representing the booking ID
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * A method which sets a new booking ID
     * @param bookingId A string representing a new booking ID
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * A method that returns the result
     * @return A string that represents the result
     */
    public String getResult() {
        return result;
    }

    /**
     * A method that sets a new result
     * @param result A string representing a new result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * A method that returns the status
     * @return A string that represents the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * A method that sets a new status
     * @param status A sttring that represents a new status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * A method that returns the notes
     * @return A string that represents the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * A method that sets a new notes
     * @param notes A string that represents a new note
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
