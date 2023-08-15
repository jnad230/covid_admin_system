package controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import user.NewUser;
import view.AdminPage;
import view.CustomerPage;
import view.HealthcareWorkerPage;

import java.util.Scanner;

public class TestingCentreController {
    /**
     * This method allows a user to login to the system
     */
    public void userLogin(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        ObjectNode node = null;
        NewUser newUser = new NewUser();
        //logging the user in
        try {
            node = newUser.login(username, password);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Invalid username/password");
        }
        if (node == null){
            userLogin();
        }
        else {
            System.out.println("Login successful! Welcome.");
            int role = newUser.getRole(node);
            if (role == 1) {
                CustomerPage customerPage = new CustomerPage(node.get("id").textValue());
                customerPage.printMenu();
            } else {
                if (role == 2) {
                    AdminPage adminPage = new AdminPage(node);
                    adminPage.printMenu();
                } else {
                    HealthcareWorkerPage healthcareWorkerPage = new HealthcareWorkerPage(node.get("id").textValue());
                    healthcareWorkerPage.printMenu();
                }
            }
        }

    }

    /**
     * This method allows a user to create a new account in the system
     */
    public void registerUser(){
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
        int valid = 1;
        boolean isCustomer = false;
        boolean isReceptionist = false;
        boolean isHealthcareWorker = false;
        do {
            System.out.print("Are you a customer, admin or healthcare worker? (C/ A/ HC): ");
            String role = scanner.next();

            if (role.equalsIgnoreCase("C")){
                isCustomer = true;
            }
            else{
                if (role.equalsIgnoreCase("A")){
                    isReceptionist = true;
                }
                else{
                    if (role.equalsIgnoreCase("HC")){
                        isHealthcareWorker = true;
                    }
                    else{
                        System.out.println("Invalid input. Please try again");
                        valid = 0;
                    }
                }
            }
        }while (valid == 0);

        NewUser newUser = new NewUser();

        try{
            newUser.createUser(givenName, familyName, username, password, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker);
            System.out.println("New account successfully created");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
