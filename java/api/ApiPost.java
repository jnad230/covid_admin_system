package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 * This Class follows the Singleton design pattern and only has a getInstance method which performs the post operation
 * in the API call
 */
public class ApiPost {
    //Private constructor
    private ApiPost(){}
    /**
     *
     * @param jsonString - String to be serialized and passed as input to the API call
     * @param toBeCreated - String representing the object being created. It is concatenated with the root url of the API.
     * @return jsonNodes - ObjectNode containing the object created by the API call
     * @throws IOException
     * @throws InterruptedException
     */
    public static ObjectNode getInstance(String jsonString, String toBeCreated) throws IOException, InterruptedException {
        String url = ApiInfo.rootUrl + "/" + toBeCreated;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(url))
                .setHeader("Authorization", ApiInfo.myApiKey)
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectNode jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode.class);
        return jsonNodes;

    }

}
