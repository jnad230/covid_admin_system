package view;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controller.AdminPageController;

import java.util.Scanner;

/**
 * This class prints the user interface for a receptionist (admin) and implements the userInterface interface
 */
public class AdminPage implements userInterface {
    private ObjectNode admin;


    public AdminPage(ObjectNode admin){
        this.admin = admin;
    }

    @Override
    /**
     * This method executes the user's input
     */
    public void printMenu() {
        int selection;
        AdminPageController adminPageController = new AdminPageController(admin);

        do{
            selection = menuItem();
            switch (selection){
                case 1:
                    adminPageController.registerBooking();
                    break;
                case 2:
                    adminPageController.createTestingSite();
                    break;
                case 3:
                    adminPageController.verifyBooking();
                    break;
                case 4:
                    adminPageController.deleteBooking();
                    break;
                case 5:
                    adminPageController.printUpdates();
                    break;
                case 6:
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
        System.out.println("1) Register booking for a user");
        System.out.println("2) Create a new testing site");
        System.out.println("3) Modify a booking");
        System.out.println("4) Delete a booking");
        System.out.println("5) View updates");
        System.out.println("6) Exit");

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


    public void modifyBooking(ObjectNode booking){
        int choice;
        AdminPageController adminPageController = new AdminPageController(admin);
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
                    adminPageController.modifyVenue(booking.get("id").textValue());
                    break;
                case 2:
                    adminPageController.modifyTime(booking.get("id").textValue());
                    break;
                case 3:
                    adminPageController.timeStampMenu(booking.get("id").textValue());
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
