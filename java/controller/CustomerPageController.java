package controller;

import booking.GetBookings;
import booking.BookingManager;
import booking.NewHomeBooking;
import booking.NewOnSiteBooking;
import com.fasterxml.jackson.databind.node.ObjectNode;
import view.CustomerPage;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerPageController {

    private String customerId;


    public CustomerPageController(String id){
        this.customerId= id;
    }

    /**
     * This method allows the customer to create a new booking for himself
     */
    public void makeNewBooking(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Are you making a booking for home testing? (Y/N): ");
        String answer = scanner.next();

        int valid = 1;
        TestingSitePageController testingSitePageController  =new TestingSitePageController();

        do {
            if (answer.equalsIgnoreCase("Y")) {
                testingSitePageController.viewAllTestingSites();
                Scanner input = new Scanner(System.in);
                System.out.print("Testing Site Id: ");
                String testingSiteId = input.next();
                System.out.print("Date: ");
                String startTime = input.next();
                int valid1 = 1;
                boolean hasTest = false;
                do {
                    System.out.print("Do you already have your test? (Y/N): ");
                    String test = input.next();

                    if (test.equalsIgnoreCase("Y")) {
                        hasTest = true;
                    } else {
                        if (test.equalsIgnoreCase("N")) {
                            hasTest = false;
                        } else {
                            System.out.println("Invalid input. Please try again.");
                            valid1 = 0;
                        }

                    }
                }while (valid1 == 0);
                NewHomeBooking newHomeBooking = new NewHomeBooking(customerId, testingSiteId, startTime, hasTest);
                try {
                    newHomeBooking.createBooking();
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }

            } else {
                if (answer.equalsIgnoreCase("N")) {
                    testingSitePageController.viewAllTestingSites();
                    Scanner input = new Scanner(System.in);
                    System.out.print("Testing Site Id: ");
                    String testingSiteId = input.next();
                    System.out.print("Date: ");
                    String startTime = input.next();
                    System.out.print("Notes (can be 'null'): ");
                    String notes = input.next();

                    NewOnSiteBooking newOnSiteBooking = new NewOnSiteBooking(customerId, testingSiteId, startTime, notes);
                    try {
                        newOnSiteBooking.createBooking();
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }

                } else {
                    System.out.println("Invalid input. Please try again.");
                    valid = 0;
                }
            }
        }while (valid == 0);
        CustomerPage customerPage = new CustomerPage(customerId);
        customerPage.printMenu();

    }

    /**
     * This method allows the customer to view his bookings
     */
    public void viewBooking(){
        GetBookings getBookings = null;
        try {
            getBookings = new GetBookings();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        ArrayList<ObjectNode> bookings = getBookings.getBookingsByField("customer", "id", customerId);
        int i = 1;
        for(ObjectNode booking: bookings) {
            System.out.println(i + ") Booking ID" + booking.get("id") + " Date created: " + booking.get("createdAt") + " Starting time: " + booking.get("startTime") + " Testing site: " + booking.get("testingSite").get("name") + " PIN: " + booking.get("smsPin"));
            i++;
        }
    }

    /**
     * This method allows the customer to cancel his bookings
     */
    public void cancelBooking(){
        viewBooking();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Booking ID of booking to be cancelled: ");
        String bookingId = scanner.next();
        BookingManager modifyBooking = new BookingManager(bookingId);
        try {
            modifyBooking.cancelBooking();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void modifyVenue(){
        viewBooking();
        Scanner selection = new Scanner(System.in);

        System.out.print("Enter ID of Booking to be modified: ");
        String bookingId = selection.nextLine();

        TestingSitePageController testingSitePageController  =new TestingSitePageController();
        testingSitePageController.viewAllTestingSites();

        System.out.print("Enter ID of new Venue : ");
        String venueId = selection.nextLine();

        BookingManager modifyBooking = new BookingManager(bookingId);
        modifyBooking.updateBookingVenue(venueId);
    }

    public void modifyTime(){
        viewBooking();
        Scanner selection = new Scanner(System.in);

        System.out.print("Enter ID of Booking to be modified: ");
        String bookingId = selection.nextLine();

        System.out.print("Enter new date (YYYY-MM-DD): ");
        String date = selection.nextLine();

        BookingManager modifyBooking = new BookingManager(bookingId);
        modifyBooking.updateBookingTime(date);
    }

    public void timeStampMenu(){
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID of Booking to be modified: ");
        String bookingId = scanner.nextLine();
        GetBookings getBookings = new GetBookings();
        ArrayList<ObjectNode> booking = getBookings.getBookingsByField("id", "",bookingId);
        ObjectNode thisBooking = booking.get(0);
        ArrayList<String> changes = new ArrayList<>();
        changes.add(thisBooking.get("additionalInfo").get("change1").textValue());
        changes.add(thisBooking.get("additionalInfo").get("change2").textValue());
        changes.add(thisBooking.get("additionalInfo").get("change3").textValue());
        do{
            System.out.println("-------------------------------------");
            for (int i = 0; i < 3; i++){
                if (!changes.get(i).equalsIgnoreCase("")){
                    System.out.println((i+1) + ") Revert to " + changes.get(i));
                }
            }
            System.out.println("4) Exit");
            System.out.print("Choice: ");
            do {
                try{
                    choice = Integer.parseInt(scanner.nextLine());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    choice = 0;
                }
            }while (choice == 0);

            BookingManager modifyBooking = new BookingManager(bookingId);
            CustomerPage customerPage = new CustomerPage(customerId);
            switch (choice){
                case 1:
                case 2:
                case 3:
                    if (changes.get(0).length() == 36){
                        modifyBooking.updateBookingVenue(changes.get(0));
                        System.out.println("Revert successful");
                        customerPage.printMenu();
                    }
                    else{
                        if (changes.get(0).length() == 24) {
                            modifyBooking.updateBookingTime(changes.get(0));
                            System.out.println("Revert successful");
                            customerPage.printMenu();
                        }
                        else{
                            System.out.println("Invalid Choice. No changes to revert to.");
                        }
                    }
                    break;
                case 4:
                    customerPage.modificationChoice();
                    break;
                default:
                    System.out.println("Invalid number. Please choose another one: ");
                    choice = 0;
                    break;
            }
        } while (choice != 0);


    }


}
