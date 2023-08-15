package controller;

import booking.GetBookings;
import booking.BookingManager;
import booking.NewOnSiteBooking;
import booking.PinVerification;
import com.fasterxml.jackson.databind.node.ObjectNode;
import testingsites.GetTestingSite;
import testingsites.NewTestingSites;
import user.NewUser;
import view.AdminPage;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminPageController {

    private ObjectNode admin;


    public AdminPageController(ObjectNode admin){
        this.admin = admin;
    }

    /**
     * This method allows an admin (receptionist) to register a new user and create a new booking for the latter
     */
    public void registerBooking(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Given Name: ");
        String givenName = scanner.next();
        System.out.print("Family Name: ");
        String familyName = scanner.next();
        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.next();
        boolean isCustomer = true;
        boolean isAdmin = false;
        boolean isHealthcareWorker = false;

        NewUser newUser = new NewUser();
        String customerId = null;
        try{
            customerId = newUser.createUser(givenName, familyName, username, password, phoneNumber, isCustomer, isAdmin, isHealthcareWorker);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        //Making booking
        TestingSitePageController testingSitePageController  =new TestingSitePageController();
        testingSitePageController.viewAllTestingSites();
        Scanner input = new Scanner(System.in);
        System.out.print("Testing Site Id: ");
        String testingSiteId = input.next();
        System.out.print("Date: ");
        String startTime = input.next();
        System.out.print("Notes (optional): ");
        String notes = input.next();
        NewOnSiteBooking newOnSiteBooking;

        newOnSiteBooking = new NewOnSiteBooking(customerId, testingSiteId, startTime, notes);

        try {
            newOnSiteBooking.createBooking();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }


    public void createTestingSite(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Testing Site Name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Website Url: ");
        String websiteUrl = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Latitude: ");
        String lat = scanner.next();
        int latitude = Integer.parseInt(lat);
        System.out.print("Longitude: ");
        String lo = scanner.next();
        int longitude = Integer.parseInt(lo);
        Scanner scanner1 = new Scanner(System.in);
        System.out.print("Unit Number: ");
        String unitNumber = scanner1.nextLine();
        System.out.print("Street: ");
        String street = scanner1.nextLine();
        System.out.print("Street 2: ");
        String street2 = scanner1.nextLine();
        System.out.print("Suburb: ");
        String suburb = scanner1.nextLine();
        System.out.print("State: ");
        String state = scanner1.nextLine();
        System.out.print("Postcode: ");
        String postcode = scanner1.nextLine();
        System.out.print("Testing Site Type: ");
        String type = scanner1.nextLine();

        NewTestingSites newTestingSites = new NewTestingSites();
        try{
            newTestingSites.createNewTestingSite(name, description, websiteUrl, phoneNumber, latitude, longitude,
                    unitNumber, street, street2, suburb, state, postcode, type);
            System.out.println("Testing site successfully created!");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void verifyBooking(){
        int choice;
        do{
            Scanner selection = new Scanner(System.in);


            //Add verification for booking ID here
            System.out.println();
            System.out.println("-------------------------------------");
            System.out.println("1) Verify Booking by ID");
            System.out.println("2) Verify Booking by PIN");
            System.out.println("3) Exit");

            System.out.print("Select an option: ");
            do {
                try{
                    choice = Integer.parseInt(selection.nextLine());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    choice = 0;
                }
            }while (choice == 0);
            AdminPage adminPage = new AdminPage(admin);
            switch (choice){
                case 1:
                    System.out.print("Enter Booking ID: ");
                    String id = selection.nextLine();
                    GetBookings getBookings = new GetBookings();
                    ObjectNode booking = getBookings.getBookingsByField("id", "", id).get(0);
                    if (booking == null){
                        System.out.println("Invalid ID provided");
                        verifyBooking();
                    }
                    else{
                        System.out.print("Enter User ID: ");
                        String userID = selection.nextLine();
                        if (userID.equalsIgnoreCase(booking.get("customer").get("id").textValue())){
                            adminPage.modifyBooking(booking);
                        }
                        else{
                            System.out.println("Invalid User ID provided");
                            verifyBooking();
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter PIN: ");
                    String pin = selection.nextLine();
                    PinVerification pinVerification = new PinVerification(pin);
                    ObjectNode booking1 = pinVerification.verifyPin();
                    if (booking1 == null){
                        System.out.println("Invalid PIN provided");
                        verifyBooking();
                    }
                    else{
                        System.out.print("Enter User ID: ");
                        String userID = selection.nextLine();
                        if (userID.equalsIgnoreCase(booking1.get("customer").get("id").textValue())){
                            adminPage.modifyBooking(booking1);
                        }
                        else{
                            System.out.println("Invalid User ID provided");
                            verifyBooking();
                        }
                    }
                    break;
                case 3:
                    adminPage.printMenu();
                    break;
                default:
                    System.out.println("Invalid number. Please choose another one: ");
                    choice = 0;
                    break;
            }
        } while (choice != 0);

    }

    public void modifyVenue(String id){
        Scanner selection = new Scanner(System.in);

        TestingSitePageController testingSitePageController  =new TestingSitePageController();
        testingSitePageController.viewAllTestingSites();

        System.out.print("Enter ID of new Venue : ");
        String venueId = selection.nextLine();

        BookingManager modifyBooking = new BookingManager(id);
        modifyBooking.updateBookingVenue(venueId);
    }

    public void modifyTime(String id){
        Scanner selection = new Scanner(System.in);

        System.out.print("Enter new date (YYYY-MM-DD): ");
        String date = selection.nextLine();

        BookingManager modifyBooking = new BookingManager(id);
        modifyBooking.updateBookingTime(date);
    }

    /**
     * This method allows the admin to delete bookings
     */
    public void deleteBooking(){
        GetBookings getBookings = new GetBookings();
        ObjectNode[] allBookings = null;
        try {
            allBookings = getBookings.getAllBookings();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        int i = 1;
        for(ObjectNode booking: allBookings) {
            System.out.println(i + ") Booking ID" + booking.get("id") + " Date created: " + booking.get("createdAt") + " Starting time: " + booking.get("startTime") + " Testing site: " + booking.get("testingSite").get("name") + " PIN: " + booking.get("smsPin"));
            i++;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Booking ID of booking to be deleted: ");
        String bookingId = scanner.next();
        BookingManager modifyBooking = new BookingManager(bookingId);
        try {
            modifyBooking.deleteBookingById();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void timeStampMenu(String id) {
        int choice;
        Scanner scanner = new Scanner(System.in);
        GetBookings getBookings = new GetBookings();
        ArrayList<ObjectNode> booking = getBookings.getBookingsByField("id", "", id);
        ObjectNode thisBooking = booking.get(0);
        ArrayList<String> changes = new ArrayList<>();
        changes.add(thisBooking.get("additionalInfo").get("change1").textValue());
        changes.add(thisBooking.get("additionalInfo").get("change2").textValue());
        changes.add(thisBooking.get("additionalInfo").get("change3").textValue());
        do {
            System.out.println("-------------------------------------");
            for (int i = 0; i < 3; i++) {
                if (!changes.get(i).equalsIgnoreCase("")) {
                    System.out.println((i + 1) + ") Revert to " + changes.get(i));
                }
            }
            System.out.println("4) Exit");
            System.out.print("Choice: ");
            do {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    choice = 0;
                }
            } while (choice == 0);

            BookingManager bookingManager = new BookingManager(id);
            AdminPage adminPage = new AdminPage(admin);
            switch (choice) {
                case 1:
                case 2:
                case 3:
                    if (changes.get(0).length() == 36) {
                        bookingManager.updateBookingVenue(changes.get(0));
                        System.out.println("Revert successful");
                        adminPage.printMenu();
                    } else {
                        if (changes.get(0).length() == 24) {
                            bookingManager.updateBookingTime(changes.get(0));
                            System.out.println("Revert successful");
                            adminPage.printMenu();
                        } else {
                            System.out.println("Invalid Choice. No changes to revert to.");
                        }
                    }
                    break;
                case 4:
                    adminPage.printMenu();
                    break;
                default:
                    System.out.println("Invalid number. Please choose another one: ");
                    choice = 0;
                    break;
            }
        } while (choice != 0);

    }

    public void printUpdates(){
        String testingSiteId = admin.get("additionalInfo").get("testingSiteId").textValue();
        GetTestingSite getTestingSite = new GetTestingSite();
        ObjectNode testingSite = getTestingSite.getTestingSiteById(testingSiteId);
        String updateString = null;
        try {
            updateString = testingSite.get("additionalInfo").get("updates").textValue();
        }
        catch(Exception e){}

        if (updateString == null){
            System.out.println("No updates relevant to this testing site");
        }
        else{
            updateString = updateString.replaceAll("__", "\n");
            System.out.println(updateString);
        }
    }

}
