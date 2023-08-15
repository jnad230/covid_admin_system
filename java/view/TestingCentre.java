package view;

import controller.TestingCentreController;

import java.util.Scanner;

/**
 * This class prints the main page of the testing and booking system and implements the userInterface interface
 */
public class TestingCentre implements userInterface {

    /**
     * This method executes the user's input
     */
    public void printMenu(){
        System.out.println("+-------------------------------------+");
        System.out.println("|        Welcome to the Covid         |");
        System.out.println("|      Booking & Testing System       |");
        System.out.println("+-------------------------------------+");

        int selection;
        TestingCentreController testingCentreController = new TestingCentreController();
        do{
            selection = menuItem();
            switch (selection){
                case 1:
                    testingCentreController.userLogin();
                    break;
                case 2:
                    testingCentreController.registerUser();
                    break;
                case 3:
                    TestingSitePage testingSitePage = new TestingSitePage();
                    testingSitePage.printMenu();
                    break;
                case 4:
                    System.exit(0);
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
        System.out.println("1) User Login");
        System.out.println("2) Register new user");
        System.out.println("3) View testing sites");
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
