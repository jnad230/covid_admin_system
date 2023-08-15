package booking;
import api.ApiInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 * This class either gets the all the bookings stored on the system or gets specific bookings based on a field requested
 * by the user
 */
public class GetBookings {

    //Private attribute
    ObjectNode[] jsonNodes;

    /**
     * This method returns all bookings made on the system. It calls ApiGet.getInstance which makes the required API call
     * @return jsonNodes - ObjectNode array of all bookings
     */
    public ObjectNode[] getAllBookings(){
        //Making the API call
        String usersUrl = ApiInfo.rootUrl + "/booking";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(usersUrl))
                .setHeader("Authorization", ApiInfo.myApiKey)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


        return jsonNodes;
    }

    /**
     * This method returns specific bookings based on the fieldName and fieldValue provided by the user
     * @param fieldName - field at what value needs to be checked
     * @param subFieldName - In case fieldName is additionalInfo - can be null
     * @param fieldValue - value to be looked for
     * @return output - Arraylist of bookings
     */
    public ArrayList<ObjectNode> getBookingsByField(String fieldName,String subFieldName, String fieldValue){
        try {
            getAllBookings();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        ArrayList<ObjectNode> output = new ArrayList<>();
        if (subFieldName.equalsIgnoreCase("")) {
            for (ObjectNode x : jsonNodes) {
                if (x.get(fieldName).textValue().equalsIgnoreCase(fieldValue)) {
                    try{
                        if (!(x.get("additionalInfo").get("status").textValue().equalsIgnoreCase("cancelled"))){
                            output.add(x);
                        }
                    }
                    catch(Exception e){
                        output.add(x);
                    }
                }
            }
        }
        else{
            for (ObjectNode x : jsonNodes) {
                if (x.get(fieldName).get(subFieldName).textValue().equalsIgnoreCase(fieldValue)) {
                    try{
                        if (!(x.get("additionalInfo").get("status").textValue().equalsIgnoreCase("cancelled"))){
                            output.add(x);
                        }
                    }
                    catch(Exception e){
                        output.add(x);
                    }

                }
            }
        }
        return output;
    }

    public String getStartDate(String bookingId){
        ArrayList<ObjectNode> bookings = getBookingsByField("id", "", bookingId);
        return bookings.get(0).get("startTime").textValue();
    }

}
