package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This Class follows the Singleton design pattern and only has a getInstance method which performs the patch operation
 * in the API call
 */
public class ApiPatch {
    //Private constructor
    private ApiPatch(){}
    /**
     *
     * @param jsonString - String to be serialized and passed as input to the API call
     * @param toBePatched - String representing the object being patched. It is concatenated with the root url of the API.
     * @return response.statusCode() - int showing if the delete has been successful or not
     * @throws IOException
     * @throws InterruptedException
     */
    public static int getInstance(String jsonString, String toBePatched) throws IOException, InterruptedException {
        String url = ApiInfo.rootUrl + "/" + toBePatched;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Authorization", ApiInfo.myApiKey)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectNode jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode.class);
        return response.statusCode();

    }
}
