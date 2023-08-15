package testingsites;

import api.ApiPost;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NewTestingSites {
    //Method to create a testing-site
    /**
     * This method creates a tsting-site by calling the ApiPost.getInstance method which makes the required API call
     * @param name - name of the new testing site
     * @param description - description of the new testing site
     * @param webUrl - website url of the new testing site
     * @param phoneNumber - phone number of the new testing site
     * @param latitude - latitude of the location of the new testing site
     * @param longitude - longitude of the location of the new testing site
     * @param unitNumber - unit number of the new testing site
     * @param street - street of the address of the new testing site
     * @param street2 - 2nd street of the address of the new testing site
     * @param suburb - suburb name of the location of the new testing site
     * @param state - state of the new testing site
     * @param postcode - postcode of the new testing site
     * @param type - type of the new testing site
     * @return - id of created testing-site
     */
    public String createNewTestingSite(String name, String description, String webUrl, String phoneNumber, int latitude, int longitude,
                                       String unitNumber, String street, String street2, String suburb, String state, String postcode, String type){
        //creating String to be serialised
        String jsonString = "{" +
                "\"name\":\"" + name + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"websiteUrl\":\"" + webUrl + "\"," +
                "\"phoneNumber\":\"" + phoneNumber + "\"," +
                "\"address\":" + "{" +
                "\"latitude\":" + latitude + "," +
                "\"longitude\":" + longitude + "," +
                "\"unitNumber\":\"" + unitNumber + "\","+
                "\"street\":\"" + street + "\","+
                "\"street2\":\"" + street2 + "\","+
                "\"suburb\":\"" + suburb + "\","+
                "\"state\":\"" + state + "\","+
                "\"postcode\":\"" + postcode + "\""+
                "}," +
                "\"additionalInfo\":" + "{" +
                "\"testingCentreType\":\"" + type + "\""+
                "}" +
                "}";

        ObjectNode jsonNode = null;
        try {
            jsonNode = ApiPost.getInstance(jsonString, "testing-site");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
