package testingsites;
import api.ApiInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 * This method returns the testing sites registered on the system
 */
public class GetTestingSite {

    //Private attribute
    private static ObjectNode[] testingSites;

    //Constructor
    public GetTestingSite() {

        //Making the API call
        String usersUrl = ApiInfo.rootUrl + "/testing-site";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(usersUrl))
                .setHeader("Authorization", ApiInfo.myApiKey)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Storing all testing sites in the node object
            testingSites = new ObjectMapper().readValue(response.body(), ObjectNode[].class);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Method to look testing centre by suburb

    /**
     * This method returns the testing sites based on a particular suburb name
     * @param suburbName - suburb name for which testing sites needs to be returned
     * @return - output - Arraylist of testing sites at the provided suburb name
     */
    public ArrayList<ObjectNode> getBySuburb(String suburbName) {
        ArrayList<ObjectNode> output = new ArrayList<>();
        for (ObjectNode x : testingSites) {
            if (suburbName.equalsIgnoreCase(x.get("address").get("suburb").textValue())) {
                output.add(x);
            }
        }
        return output;
    }

    //Method to look for testing site by type (GP/Clinic/...)
    /**
     * This method returns the testing sites based on a particular type of testing centre
     * @param testingCentreType - type for which testing sites needs to be returned
     * @return - output - Arraylist of testing sites of the provided type
     */
    public ArrayList<ObjectNode> getByType(String testingCentreType) {
        ArrayList<ObjectNode> output = new ArrayList<>();
        for (ObjectNode x : testingSites) {
            try {
                if (testingCentreType.equalsIgnoreCase(x.get("additionalInfo").get("testingCentreType").textValue())) {
                    output.add(x);
                }
            }
            catch(Exception e){}
        }
        return output;
    }

    /**
     * This method returns all testing sites registered on the system
     * @return - node - ObjectNode[] of testing sites
     */
    public ObjectNode[] getAllTestingSites(){
        return testingSites;
    }

    public ObjectNode getTestingSiteById(String id){
        ObjectNode output = null;
        for (ObjectNode site : testingSites){
            if (site.get("id").textValue().equalsIgnoreCase(id)){
               output = site;
            }
        }
        return output;
    }
}
