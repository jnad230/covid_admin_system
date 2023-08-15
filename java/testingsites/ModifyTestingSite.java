package testingsites;
import api.ApiPatch;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class modifies the object of a testing site
 */
public class ModifyTestingSite {

    //Method to modify additionalInfo field of testing sites
    /**
     * This method modifies the additionalInfo object of the testing site with the provided testing site ID
     */
    public void updateTestingSiteInfo(String testingSiteId,String updateString) {
        GetTestingSite getTestingSite = new GetTestingSite();
        ObjectNode testingSite = getTestingSite.getTestingSiteById(testingSiteId);
        String additionalInfo = "";
        try {
            additionalInfo = testingSite.get("additionalInfo").get("updates").textValue();
        }catch(Exception e){
            additionalInfo = "";
        }
        if (additionalInfo == null){
            additionalInfo = "";
        }
        //additionalInfo += "\n";
        additionalInfo += updateString;
        //String to be serialised and passed into API

        String jsonString = "{" +
                "\"additionalInfo\":" + "{" +
                "\"updates\":\"" + additionalInfo + "\""+
                "}" +
                "}";

        //Making the API call
        String patchUrl = "testing-site/" + testingSiteId;
        try{
            ApiPatch.getInstance(jsonString, patchUrl);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void updateTestingSiteField(String testingSiteId,String fieldName, String newValue){
        //String to be serialised and passed into API
        String jsonString = "{" +
                "\"additionalInfo\":" + "{" +
                "\""+ fieldName +"\":\"" + newValue +"\""+
                "}" +
                "}";
        //Making the API call
        String patchUrl = "testing-site/" + testingSiteId;
        try{
            ApiPatch.getInstance(jsonString, patchUrl);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
