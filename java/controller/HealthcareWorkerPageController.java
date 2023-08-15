package controller;

import booking.BookingManager;
import booking.PinVerification;
import com.fasterxml.jackson.databind.node.ObjectNode;
import covidtest.CovidTest;
import survey.InterviewLogic;
import view.HealthcareWorkerPage;

import java.util.Scanner;

public class HealthcareWorkerPageController {

    private String healthcareWorkerId;


    public HealthcareWorkerPageController(String id){
        this.healthcareWorkerId = id;
    }

    /**
     * This method allows the health care worker to verify the PIN of a user's booking and perform the testing survey
     */
    public void verifyPin(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Booking PIN: ");
        String pin = scanner.next();

        PinVerification pinVerification = new PinVerification(pin);
        ObjectNode booking = pinVerification.verifyPin();

        if (booking == null){
            System.out.println("Invalid Pin");
        }
        else{
            InterviewLogic interviewLogic = new InterviewLogic();
            String testType = interviewLogic.getTestType();
            System.out.println(testType);
            BookingManager modifyBooking = new BookingManager(booking.get("id").textValue());
            try {
                modifyBooking.updateBooking("testType", testType);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This method allows the healthcare worker to create a covid test object and store the result of the test
     */
    public void performTest(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Booking PIN: ");
        String pin = scanner.next();

        PinVerification pinVerification = new PinVerification(pin);
        ObjectNode booking = pinVerification.verifyPin();
        HealthcareWorkerPage healthcareWorkerPage = new HealthcareWorkerPage(healthcareWorkerId);

        if (booking == null){
            System.out.println("Invalid Pin");
        }
        else{
            String testType = booking.get("additionalInfo").get("testType").textValue();
            if (testType.equalsIgnoreCase(null)){
                System.out.println("This booking has not been verified/ surveyed");
                healthcareWorkerPage.printMenu();
            }
            else{
                System.out.println("A " + testType + " should be performed.");
                scanner = new Scanner(System.in);
                System.out.print("Result: ");
                String result = scanner.next().toUpperCase();
                System.out.print("Notes (can be null): ");
                String notes = scanner.next();
                CovidTest covidTest = new CovidTest(testType, booking.get("customer").get("id").textValue(), healthcareWorkerId, booking.get("id").textValue(), result, "CREATED", notes);
                try {
                    covidTest.createTest();
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                System.out.println("Covid test successfully performed and saved.");
            }


        }
    }

}
