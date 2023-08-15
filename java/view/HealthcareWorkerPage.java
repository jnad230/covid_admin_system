package view;

import controller.HealthcareWorkerPageController;

import java.util.Scanner;

/**
 * This class prints the user interface for a healthcare worker and implements the userInterface interface
 */
public class HealthcareWorkerPage implements userInterface {

    //Private attribute
    private String healthcareWorkerId;


    //Constructor
    /**
     * Constrcutor for the HealthcareWorkerPage class
     * @param healthcareWorkerId - ID of health care worker signed in
     */
    public HealthcareWorkerPage(String healthcareWorkerId){
        this.healthcareWorkerId = healthcareWorkerId;
    }

    @Override
    /**
     * This method executes the user's input
     */
    public void printMenu() {
        int selection;
        HealthcareWorkerPageController healthcareWorkerPageController = new HealthcareWorkerPageController(healthcareWorkerId);

        do{
            selection = menuItem();
            switch (selection){
                case 1:
                    healthcareWorkerPageController.verifyPin();
                    break;
                case 2:
                    healthcareWorkerPageController.performTest();
                    break;
                case 3:
                    TestingCentre testingCentre = new TestingCentre();
                    testingCentre.printMenu();
                    break;
                default:
                    System.out.println("Invalid number. Please choose another one: ");
                    selection = 0;
                    break;
            }
        } while (selection != 0);
    }

    /**
     * This method prints the menu of options available to the user
     * @return - choice - the number selected by the user
     */
    public static int menuItem() {
        Scanner selection = new Scanner(System.in);

        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println("1) Verify Pin & Perform survey");
        System.out.println("2) Perform test");
        System.out.println("3) Exit");

        System.out.print("Select an option:");
        int choice;
        do {
            try{
                choice = Integer.parseInt(selection.nextLine());
            }catch (Exception e){
                System.out.println(e.getMessage());
                choice = 0;
            }
        }while (choice == 0);


        return choice;
    }


}
