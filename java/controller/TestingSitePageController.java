package controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import testingsites.GetTestingSite;

import java.util.ArrayList;
import java.util.Scanner;

public class TestingSitePageController {
    GetTestingSite testingSite = new GetTestingSite();
    /**
     * This method allows the user to view all testing sites registered on the system
     */
    public void viewAllTestingSites(){

        ObjectNode[] node = testingSite.getAllTestingSites();
        int i = 1;
        for (ObjectNode x : node){
            System.out.println(i+")" + "Testing Site ID: "+ x.get("id") +" Name: " + x.get("name") + " Suburb: " + x.get("address").get("suburb") + " Centre type: " + x.get("additionalInfo").get("testingCentreType"));
            i++;
        }
    }

    /**
     * This method allows the user to look for testing sites at a particular suburb name
     */
    public void viewBySuburbName(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Suburb Name: ");
        String suburbName = scanner.next();

        ArrayList<ObjectNode> node = testingSite.getBySuburb(suburbName);
        int i = 1;
        for (ObjectNode x : node){
            System.out.println(i+")" + "Testing Site ID: "+ x.get("id") +" Name: " + x.get("name") + " Suburb: " + x.get("address").get("suburb") + " Centre type: " + x.get("additionalInfo").get("testingCentreType"));
            i++;
        }
    }

    /**
     * This method allows users to view testing types of a particular type
     */
    public void viewByType(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Testing centre type: ");
        String type = scanner.next();

        ArrayList<ObjectNode> node = testingSite.getByType(type);
        int i = 1;
        for (ObjectNode x : node){
            System.out.println(i+")" + "Testing Site ID: "+ x.get("id") +" Name: " + x.get("name") + " Suburb: " + x.get("address").get("suburb") + " Centre type: " + x.get("additionalInfo").get("testingCentreType"));
            i++;
        }
    }
}
