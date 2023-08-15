package survey;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class determines the test to be performed based on the answers given by the user in the survey
 */
public class InterviewLogic {

    private static String covidTestType = "RAT";

    /**
     * This method determines the test to be performed by the customer
     * @return covidTestType - PCR or RAT test to be performed by customer
     */
    public String getTestType() {
        InterviewConsole inputs = new InterviewConsole();
        inputs.interviewResponse();
        ArrayList pcrInputs = inputs.getPcrResponse();
        if (pcrInputs.contains("yes")){
            covidTestType = covidTestType.replace("RAT", "PCR");
        }else if (!inputs.getRatResponse().contains("yes")){
            System.out.println("Provide reason for the necessity of a covid test:");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
        }
        return covidTestType;
    }

}
