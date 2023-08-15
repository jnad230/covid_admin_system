package survey;

import java.util.Scanner;
import java.util.ArrayList; // import the ArrayList class
/**
 * The class InterviewLogic allows to ask a series of questions to determine whether a PCR test or an RAT test
 * should be performed
 */
public class InterviewConsole {

    private ArrayList<String> pcrResponse = new ArrayList<String>();
    private ArrayList<String> ratResponse = new ArrayList<String>();
    private String[] pcrQuestions = {"Do you have a temperature of 37.5 or higher? (Yes/No)",
            "Do you have chills or body ache? (Yes/No)",
            "Do you have difficulty breathing? (Yes/No)",
            "Do you have a loss of sense of smell and taste? (Yes/No)",
            "Are you over the age of 60 or have a disease that affects your lungs? (Yes/No)",
            "Have you been advised to get tested for Covid-19 by a health professional? (Yes/No)"};

    private String[] ratQuestions = {"Do you have cough or sore throat? (Yes/No)",
            "Do you have a runny nose or nasal congestion? (Yes/No)",
            "Have you had any close contact with someone with Covid-19 in the past 10 days? (Yes/No)"};

    /**
     * This class asks the auestions to the user and records the answers in 2 array lists
     */
    public void interviewResponse(){
        for (String i : this.pcrQuestions) {
            String input = verifiedInput(i);
            pcrResponse.add(input);
        }

        if (this.pcrResponse.contains("No")){
            for (String i : this.ratQuestions) {
                String input = verifiedInput(i);
                ratResponse.add(input);
            }
        }
    }

    public String verifiedInput(String i){
        String input;
        int valid = 1;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println(i);
            input = sc.nextLine().toLowerCase();
            if (!input.equalsIgnoreCase("Yes")){
                if (!input.equalsIgnoreCase("No")){
                    System.out.println("Please enter 'Yes' or 'No' only");
                    valid = 0;
                }
            }
        }while (valid == 0);

        return input;
    }

    /**
     * This class returns the responses to the PCR test questions
     * @return - pcrResponse - Arraylist representing the answers to the PCR test questions
     */
    public ArrayList<String> getPcrResponse(){
        return this.pcrResponse;
    }

    /**
     * This class returns the responses to the RAT test questions
     * @return - ratResponse - Arraylist representing the answers to the RAT test questions
     */
    public ArrayList<String> getRatResponse(){
        return this.ratResponse;
    }

}
