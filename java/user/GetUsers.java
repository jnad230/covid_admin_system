package user;
import api.ApiInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 * This method gets all registered users on the system
 */
public class GetUsers {

    //Method 1 - get all users

    /**
     * This method returns all users registered on the system.
     * @return jsonNodes - ObjectNode[] of all users on the system
     */
    public ObjectNode[] getAllUsers() throws IOException, InterruptedException {
        //Making the API call
        String usersUrl = ApiInfo.rootUrl + "/user";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(usersUrl))
                .setHeader("Authorization", ApiInfo.myApiKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);
        //Returning an object of users
        return jsonNodes;
    }

    //Method 2 - getting specific users based on a field
    /**
     * This method gets specific users based on a fieldName and provided fieldValue
     * @param fieldName - field name at which fieldValue needs to be looked for
     * @param fieldValue - field value which needs to be looked for
     * @return output - Arraylist of users needed
     */
    public ArrayList<ObjectNode> getSpecificUsers(String fieldName, String fieldValue) {
        ObjectNode[] users = null;
        try {
            users = getAllUsers();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        ArrayList<ObjectNode> output = new ArrayList<>();
        for(ObjectNode x : users){
            if (fieldValue.equalsIgnoreCase(x.get(fieldName).textValue())){
                output.add(x);
            }
        }
        return output;
    }



}
