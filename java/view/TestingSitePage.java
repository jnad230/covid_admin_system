package view;

import controller.TestingSitePageController;
import testingsites.GetTestingSite;

import java.util.Scanner;

/**
 * This class prints the page when a user requests to view testing sites and implements the userInterface interface
 */
public class TestingSitePage implements userInterface {

    GetTestingSite testingSite = null;
    TestingSitePageController testingSitePageController = new TestingSitePageController();

    /**
     * Constructor of the TestingSitePage class
     */
    public TestingSitePage(){
        try {
            testingSite = new GetTestingSite();
        }
        catch( Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    /**
     * This method executes the user's input
     */
    public void printMenu() {
        int selection;

        do{
            selection = menuItem();
            switch (selection){
                case 1:
                    testingSitePageController.viewAllTestingSites();
                    break;
                case 2:
                    testingSitePageController.viewBySuburbName();
                    break;
                case 3:
                    testingSitePageController.viewByType();
                    break;
                case 4:
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
    public static int menuItem(){
        Scanner selection = new Scanner(System.in);

        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println("1) View all testing sites");
        System.out.println("2) Search for testing sites by suburb name");
        System.out.println("3) Search for testing sites by type");
        System.out.println("4) Exit");

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
