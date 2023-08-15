package user;
import api.ApiInfo;
import api.ApiPost;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class creates a new user account
 */
public class NewUser {

    //Method to create a user
    /**
     * This method creates a user account by calling the ApiPost.getInstance method which makes the required API call
     * @param givenName - user's given name
     * @param familyName - user's family name
     * @param userName - user's unique username
     * @param password - user's password
     * @param phoneNumber - user's phone number
     * @param isCustomer - if user is a customer
     * @param isReceptionist - if user is an admin
     * @param isHealthcareWorker - if user is a healthcare worker
     * @return - id of created user
     */
    public String createUser(String givenName, String familyName, String userName, String password, String phoneNumber,
                             boolean isCustomer, boolean isReceptionist, boolean isHealthcareWorker){

        String jsonString = "";
        //Creating String to be serialised
        if (isCustomer) {
            jsonString = "{" +
                    "\"givenName\":\"" + givenName + "\"," +
                    "\"familyName\":\"" + familyName + "\"," +
                    "\"userName\":\"" + userName + "\"," +
                    "\"password\":\"" + password + "\"," +
                    "\"phoneNumber\":\"" + phoneNumber + "\"," +
                    "\"isCustomer\":" + isCustomer + "," +
                    "\"isReceptionist\":" + isReceptionist + "," +
                    "\"isHealthcareWorker\":" + isHealthcareWorker +
                    "}";
        }
        else{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the ID of the Testing Site you work at: ");
            String testingSiteId = scanner.nextLine();
            jsonString = "{" +
                    "\"givenName\":\"" + givenName + "\"," +
                    "\"familyName\":\"" + familyName + "\"," +
                    "\"userName\":\"" + userName + "\"," +
                    "\"password\":\"" + password + "\"," +
                    "\"phoneNumber\":\"" + phoneNumber + "\"," +
                    "\"isCustomer\":" + isCustomer + "," +
                    "\"isReceptionist\":" + isReceptionist + "," +
                    "\"isHealthcareWorker\":" + isHealthcareWorker + "," +
                    "\"additionalInfo\":" + "{" +
                    "\"testingSiteId\":\"" + testingSiteId + "\""+
                    "}" +
                    "}";
        }
        ObjectNode jsonNode = null;
        try {
            jsonNode = ApiPost.getInstance(jsonString, "user");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return jsonNode.get("id").textValue();
    }

    //Method to get the user with the provided ID
    public ObjectNode login(String userName, String password){

        String jsonString = "{" +
                "\"userName\":\"" + userName + "\"," +
                "\"password\":\"" + password + "\"" +
                "}";

        //Making the API call
        String usersUrl = ApiInfo.rootUrl + "/user/login";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(usersUrl))
                .setHeader("Authorization", ApiInfo.myApiKey)
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        ArrayList<ObjectNode> user = null;
        if (response.statusCode() == 200){
            GetUsers getUsers = new GetUsers();
            user = getUsers.getSpecificUsers("userName", userName);

        }
        return user.get(0);
    }

    //Getting user's role:
    /**
     * This method returns the role of a user
     * @param node - ObjectNode of user whose role needs to be returned
     * @return 1 - Customer
     *         2 - Admin
     *         3 - HC worker
     */
    public int getRole(ObjectNode node) {
        if (node.get("isCustomer").booleanValue()) {
            return 1;
        } else {
            if (node.get("isReceptionist").booleanValue()) {
                return 2;

            } else {
                return 3;
            }
        }
    }


}
