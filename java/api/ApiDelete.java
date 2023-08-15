package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This Class follows the Singleton design pattern and only has a getInstance method which performs the delete operation
 * in the API call
 */
public class ApiDelete {
    //Private constructor
    private ApiDelete(){}
    /**
     * This method executes the delete method of the API
     * @param toBeDeleted - String representing the object being deleted. It is concatenated with the root url of the API.
     * @return response.statusCode() - int showing if the delete has been successful or not
     * @throws IOException
     * @throws InterruptedException
     */
    public static int getInstance(String toBeDeleted) throws IOException, InterruptedException {
        String url = ApiInfo.rootUrl + "/" + toBeDeleted;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(url))
                .setHeader("Authorization", ApiInfo.myApiKey)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.statusCode();

    }
}
