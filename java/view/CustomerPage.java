package view;

import controller.CustomerPageController;

import java.util.Scanner;

/**
 * This class prints the user interface for a customer and implements the userInterface interface
 */
public class CustomerPage implements userInterface {
    private String customerId;


    /**
     * Constructor for the customerId class
     * @param customerId - ID of signed in customer
     */
    public CustomerPage(String customerId){
        this.customerId = customerId;
    }
    @Override
    /**
     * This method executes the user's input
     */
    public void printMenu() {
        int selection;
        CustomerPageController customerPageController = new CustomerPageController(customerId);

        do{
            selection = menuItem();
            switch (selection){
                case 1:
                    customerPageController.makeNewBooking();
                    break;
                case 2:
                    customerPageController.viewBooking();
                    break;
                case 3:
                    modificationChoice();
                    break;
                case 4:
                    customerPageController.cancelBooking();
                    break;
                case 5:
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
        System.out.println("1) Make a new booking");
        System.out.println("2) View your bookings");
        System.out.println("3) Modify a booking");
        System.out.println("4) Cancel a booking");
        System.out.println("5) Exit");

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


    public void modificationChoice(){
        int choice;
        CustomerPageController customerPageController = new CustomerPageController(customerId);
        do{
            Scanner selection = new Scanner(System.in);


            //Add verification for booking ID here
            System.out.println();
            System.out.println("-------------------------------------");
            System.out.println("1) Modify Testing Venue");
            System.out.println("2) Modify Testing Date");
            System.out.println("3) Revert a change");
            System.out.println("4) Exit");

            System.out.print("Select an option: ");
            do {
                try{
                    choice = Integer.parseInt(selection.nextLine());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    choice = 0;
                }
            }while (choice == 0);

            switch (choice){
                case 1:
                    customerPageController.modifyVenue();
                    break;
                case 2:
                    customerPageController.modifyTime();
                    break;
                case 3:
                    customerPageController.timeStampMenu();
                    break;
                case 4:
                    printMenu();
                    break;
                default:
                    System.out.println("Invalid number. Please choose another one: ");
                    choice = 0;
                    break;
            }
        } while (choice != 0);


    }

}
