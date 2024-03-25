package com.example.foodiesfavequeuemanagement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main {

    static Scanner input = new Scanner(System.in);
    static FoodQueue queue1 = new FoodQueue(2);
    static FoodQueue queue2 = new FoodQueue(3);
    static FoodQueue queue3 = new FoodQueue(5);
    static FoodQueue[] allQueue = {queue1,queue2,queue3};

    static int incomeQueue1 = 0;
    static int incomeQueue2 = 0;
    static int incomeQueue3 = 0;
    static int burgerStock = 50;
    static final int warningLimit = 10;

    public static void main(String[] args) {

        boolean endLoop = true;
        while(endLoop){
            try{
                System.out.println(
                        """
                                 ________________________________________________________
                                |**************************MENU**************************|
                                |--------------------------------------------------------|
                                |                                                        |
                                | 100 or VFQ: View all Queues.                           |
                                | 101 or VEQ: View all Empty Queues.                     |
                                | 102 or ACQ: Add customer to a Queue.                   |
                                | 103 or RCQ: Remove a customer from a Queue             |
                                | 104 or PCQ: Remove a served customer.                  |
                                | 105 or VCS: View Customers Sorted in alphabetical order|
                                | 106 or SPD: Store Program Data into file               |
                                | 107 or LPD: Load Program Data from file.               |
                                | 108 or STK: View Remaining Burger Stock.               |
                                | 109 or AFS: Add Burger to Stock.                       |
                                | 110 or IFQ: View income of each Queue.                 |
                                | 112 or GUI: View the GUI.                              |
                                | 999 or EXT: Exit the Program.                          |
                                |                                                        |
                                |--------------------------------------------------------|
                                |________________________________________________________|
                                """);

                System.out.print("Please select an option ====> ");
                String option = input.next().toUpperCase();

                switch (option){
                    case "100", "VFQ" -> VFQ();
                    case "101", "VEQ" -> VEQ();
                    case "102", "ACQ" -> ACQ();
                    case "103", "RCQ" -> RCQ();
                    case "104", "PCQ" -> PCQ();
                    case "105", "VCS" -> VCS();
                    case "106", "SPD" -> SPD();
                    case "107", "LPD" -> LPD();
                    case "108", "STK" -> STK();
                    case "109", "AFS" -> AFS();
                    case "110", "IFQ" -> IFQ();
                    case "112", "GUI" -> {
                        System.out.println("""
                                ***********************
                                GUI Loaded Successfully
                                ***********************""");
                        myLaunch(HelloApplication.class);
                    }
                    case "999", "EXT" -> {
                        System.out.println("Exiting Program");
                        endLoop = false;
                    }
                    default -> {
                        System.out.println("Enter Valid Option");
                    }
                }

            }
            catch (InputMismatchException ex){
                System.out.println("Invalid Option");
            }
        }
    }
    public static void VFQ(){

        System.out.println("""
                *************************
                *\t\tCashiers\t\t*
                *************************""");
        /*
        * Set The Limitation to 5(Max Queue Length of Queue 3) in the for loop
        * Set Limitation to Queue 1,Queue 2,Queue3 below 2,3,5 respectively
        * Print output according to Queuesize which returns filled positions
        */


        for (int i = 0; i < 5; i++) {
            if (i < 2){
                System.out.print(i<queue1.getQueueSize() ? "O\t\t\t" : "X\t\t\t");
            }

            if (i < 3) {
                if (i == 2){
                    System.out.print("\t\t\t");
                }
                System.out.print(i<queue2.getQueueSize() ? "O\t\t\t" : "X\t\t\t");
            }

            if (i == 3 || i == 4) {
                System.out.print("\t\t\t\t\t\t");
            }
            System.out.print(i<queue3.getQueueSize() ? "O" : "X");

            System.out.println();
        }
        System.out.println("\nO-Occupied\tX-Not Occupied\n");
    }

    public static void VEQ(){
        /*
        * Print Each Queue With Customer Details
        * If Customer Details is Empty System will output as Empty
        */
        allQueue[0].printEmptyQueue(2,1);
        allQueue[1].printEmptyQueue(3,2);
        allQueue[2].printEmptyQueue(5,3);
    }

    public static void ACQ(){
        int burgersRequired = 0;
        boolean numberError = false;
        boolean queueLimitReached = (queue1.getQueueSize()+queue2.getQueueSize()+queue3.getQueueSize()) == 10;

        System.out.print("\nEnter First Name: ");
        String firstName = input.next();
        System.out.print("Enter Second Name: ");
        String secondName = input.next();
        
        /*
        * This Do While Block Executes to validate the burgers required
        */
        if (burgerStock > 0){
            do{
                try {
                    System.out.print("Enter Burger quantity needed: ");
                    burgersRequired = Integer.parseInt(input.next());
                    while (burgersRequired>burgerStock || burgersRequired < 1){
                        System.out.println("Not Sufficient Burgers in Stock. Enter below the stock level");
                        STK();
                        System.out.print("Enter Burger quantity needed: ");
                        burgersRequired = Integer.parseInt(input.next());
                    }
                    numberError = false;
                }
                catch (NumberFormatException nfe) {
                    System.out.println("Only Integers Allowed!");
                    numberError = true;
                }
            }
            while(numberError);

            Customer customer = new Customer(firstName, secondName, burgersRequired);
            int minQueue = minQueueSize();

            if (!queueLimitReached){
                allQueue[minQueue].addCustomer(customer,minQueue);
                //minQueue.addCustomer(customer);
                burgerStock -= burgersRequired;
            }
            else{
                addWaiting(customer);
            }
            warningAlert();
        }
        else {
            STK();
        }

    }

    public static int minQueueSize(){

        /*
        * Find Minimum Length of a Queue and Return the Index of the Queue*/

        int minQueueIndex = 0;
        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < allQueue.length; i++) {
            int queueLength = allQueue[i].getQueueSize();

            if (queueLength == allQueue[i].getMaxQueueLimit()){
                continue;
            }
            else if (queueLength < minLength) {
                minLength = queueLength;
                minQueueIndex = i;
            }
        }
        return minQueueIndex;
    }

    public static void warningAlert(){
        /*
        * Check if the Stock is below the warning limit (10)
        */
        if(burgerStock <= warningLimit){
            System.out.println("Warning Burgers Running out of Stock");
        }
    }

    public static void RCQ(){
        VEQ();  // Print All Customer Details

        int customerQueue =0, customerQueueIndex=0;
        boolean loopAgain = false, numberError = false;

        do{
            do{ // This Do-While Executes the Validate the customerQueue in any case user inputs a String
                try {
                    System.out.print("\nEnter Customer Queue Number :");
                    customerQueue = Integer.parseInt(input.next());
                    numberError = false;    
                } catch (NumberFormatException nfe) {
                    System.out.println("Only integers Allowed!");
                    numberError = true;
                }
            }
            while(numberError);
            
            if(customerQueue > allQueue.length || customerQueue < 1){   // Checks if the customerQueue is in the desired range
                System.out.println("Enter Only Between 1 and 3");
                loopAgain = true;
            }
            else{
                if (allQueue[customerQueue-1].getQueueSize() > 0){
                    do {  // This Do-While Executes to validate customerQueueIndex in any case User inputs a String
                        try {
                            System.out.print("\nEnter Customer Queue Index :");
                            customerQueueIndex = Integer.parseInt(input.next()); 
                            numberError = false;

                        } catch (NumberFormatException nfe) {
                            System.out.println("Only Integers Allowed");
                            numberError = true;
                        }
                    } 
                    while (numberError);
                    
                    if (customerQueueIndex > 0 && customerQueueIndex <= allQueue[customerQueue-1].getQueueSize()){

                        burgerStock += allQueue[customerQueue-1].getQueue().get(customerQueueIndex-1).getBurgersRequired();

                        String name = allQueue[customerQueue-1].getQueue().get(customerQueueIndex-1).getFullName();

                        allQueue[customerQueue-1].removeCustomer(customerQueueIndex-1);

                        System.out.println("\n*****************************"+
                                            "\nCustomer Removed Successfully"+
                                            "\nCustomer : "+name+
                                            "\n*****************************\n");

                        removeWaiting(allQueue[customerQueue-1],customerQueue);
                        loopAgain = false;
                    }
                    else{
                        System.out.println("There is no Slot in this Index");
                        return;
                    }

                }
                else{
                    System.out.println("This Queue is Empty");
                    return;
                }    
            }
        }
        while(loopAgain);
    }

    public static void PCQ(){
        VEQ();   // Print All Customer Details

        int customerQueue = 0;
        boolean loopAgain = false, numberError = false;

        do{
            do {    // This Do-While Executes the Validate the customerQueue in any case user inputs a String
                try {
                    System.out.print("\nEnter Customer Queue Number : ");
                    customerQueue = Integer.parseInt(input.next());
                    numberError = false;
                } catch (NumberFormatException nfe) {
                    System.out.println("Only Integers Allowed");
                    numberError = true;
                }
            } while (numberError);
            
            if (customerQueue > 0 && customerQueue <= allQueue.length){    // Checks if the customerQueue is in the desired range
                if (allQueue[customerQueue-1].getQueueSize() != 0){   // Checks if the Queue is not Empty

                    addIncome(customerQueue,allQueue[customerQueue-1].getQueue().get(0).getBurgersRequired());

                    String name = allQueue[customerQueue-1].getQueue().get(0).getFullName();

                    allQueue[customerQueue-1].removeCustomer(0);

                    System.out.println("\n************************************"+
                                        "\nServed Customer Removed Successfully"+
                                        "\nCustomer : "+name+
                                        "\n************************************\n");

                    removeWaiting(allQueue[customerQueue-1],customerQueue); // This adds Customer to Queue if there is any Customer in Waiting List

                    loopAgain = false;
                }
                else{
                    System.out.println("""

                            \t**************
                            \tQueue is Empty
                            \t**************""");
                    break;
                }
            }
            else{
                System.out.println("Invalid Input ");
                loopAgain = true;
            }
        }
        while(loopAgain);
    }

    public static void addIncome (int customerQueue, int burgersRequired){
        // Calculate Income According to the customerQueue
        if (customerQueue == 1){
            incomeQueue1 += 650*burgersRequired;
        }
        else if(customerQueue == 2){
            incomeQueue2 += 650*burgersRequired;
        }
        else{
            incomeQueue3 += 650*burgersRequired;
        }
    }

    public static void VCS(){

        /*
        * https://www.geeksforgeeks.org/bubble-sort/*/

        System.out.println("\n*********** Sorted Queue ***********");
        for (int i = 0; i < allQueue.length; i++)
        {
            System.out.println("\nQueue " + (i + 1) + " : ");
            sortAndPrint(allQueue[i]);  // Passes Each Queue as Parameter for Method
        }
        System.out.println("\n************************************\n");
    }

    public static void sortAndPrint(FoodQueue queue){
        ArrayList <String> sortedQueue = new ArrayList<>();
        /*
        * Creates a New ArrayList Each Time the sortAndPrint function is called
        * Add the full name in the queue to the ArrayList*/
        for (int i = 0; i<queue.getQueueSize();i++){
            String fullName = queue.getQueue().get(i).getFullName();
            sortedQueue.add(fullName);
        }

        /*
        * Sort the Created ArrayList using bubble sort method by Swapping position*/
        for (int i = 0; i < sortedQueue.size() - 1; i++) {
            for (int j = 0; j < sortedQueue.size() - i - 1; j++) {
                if(sortedQueue.get(j) != null && sortedQueue.get(j+1) != null){
                    if (compareStrings(sortedQueue.get(j).toLowerCase(), sortedQueue.get(j+1).toLowerCase()) > 0) {
                        String str1 = sortedQueue.get(j);
                        String str2 = sortedQueue.get(j+1);
                        sortedQueue.set(j, str2);
                        sortedQueue.set(j+1, str1);
                    }
                }
            }
        }

        //  Prints the Sorted ArrayList
        for (String sortedName : sortedQueue) {
            System.out.println("\t\t" + sortedName);
        }
    }

    public static int compareStrings(String string1, String string2){
        /*
        * Compares the two Strings
        * */
        int minLength = Math.min(string1.length(), string2.length());

        for (int i = 0; i < minLength; i++) {
            char char1 = string1.charAt(i);
            char char2 = string2.charAt(i);

            if (char1 < char2) {
                return -1;
            } else if (char1 > char2) {
                return 1;
            }
        }

        return string1.length() - string2.length();
    }
    

    public static void SPD(){
        /*
        * Stores Data in Queue1,Queue2,Queue3 to a textfile and stores burger count as well*/
        int index = 1;

        try {
            FileWriter writer = new FileWriter("Text.txt");
            for(FoodQueue queue : allQueue){
                for (int i = 0; i <queue.getQueueSize(); i++) {
                    writer.write(Integer.toString(index)+" "+queue.getQueue().get(i).getFirstName()+" "+queue.getQueue().get(i).getSecondName()+" "+Integer.toString(queue.getQueue().get(i).getBurgersRequired()));
                    writer.write(System.lineSeparator());
                }
                index++;
            }
            writer.close();
            System.out.println("Array written to file successfully.");
        }

        catch (IOException e) {
            System.out.println("An error occurred while writing the array to the file.");
            e.printStackTrace();
        }
    }

    public static void LPD(){
        /*
        * Initially Clears all Queues
        * Then Updates burger Stock as in textfile
        * Finally adds the customer data to the respective arrays*/
        queue1.getQueue().clear();
        queue2.getQueue().clear();
        queue3.getQueue().clear();
        burgerStock = 50;
        try {
            File readFile = new File("Text.txt");
            Scanner rf = new Scanner(readFile);

            while( rf.hasNextLine()){
                String text = rf.nextLine();
                String[] textSplitted = text.split(" ");    //Splits the String by space and returns a String Array
                int queueNumber = Integer.parseInt(textSplitted[0]);
                String firstName = textSplitted[1];
                String secondName = textSplitted[2];
                int burgersRequired = Integer.parseInt(textSplitted[3]);
                Customer customer = new Customer(firstName,secondName,burgersRequired);
                burgerStock -= burgersRequired;
                if (queueNumber == 1){ queue1.getQueue().add(customer);}
                else if (queueNumber == 2){ queue2.getQueue().add(customer);}
                else if (queueNumber == 3){ queue3.getQueue().add(customer);}
            }
            rf.close();

            System.out.println("Customer Data Loaded to program");
        }
        catch (IOException e) {
            System.out.println("Error while reading a file.");
        }
    }

    public static void STK(){

        /* Prints the Burger Stock Available*/
        System.out.println("\n***********************************"+
                            "\nBurgers Available in Stock : "+burgerStock+
                            "\n***********************************");
    }

    public static void AFS(){
        /* Adds Burgers to the Stock*/
        boolean numberError = false;
        int addBurgers = 0;
        do {
            try {
                System.out.print("\nEnter Burger Amount to add : ");
                addBurgers = Integer.parseInt(input.next());
                burgerStock += addBurgers;
                System.out.println("""

                        \t*********************
                        \t******RESTOCKED******
                        \t*********************""");
                numberError = false;        
            } catch (Exception e) {
                System.out.println("Only Integers Allowed");
                numberError = true;
            }
        } while (numberError);
        
    }

    public static void IFQ(){
        /* Prints Income of Each Queue*/
        System.out.println("\nThe Income of Queue 1 --> "+incomeQueue1+" Rupees\n");
        System.out.println("The Income of Queue 2 --> "+incomeQueue2+" Rupees\n");
        System.out.println("The Income of Queue 3 --> "+incomeQueue3+" Rupees");
    }

//*******************************Waiting List***********************************
/*Implements Circular Queue
* https://stackoverflow.com/questions/41088144/implementing-a-circular-queue-using-an-arraylist
* https://www.geeksforgeeks.org/introduction-to-circular-queue/
* https://www.youtube.com/watch?v=IVlSZcwJytw
*/

    static int maxWaitingCapacity = 10;
    static Customer[] waiting = new Customer[maxWaitingCapacity];
    static int front = -1;
    static int rear = -1;

    public static void addWaiting(Customer customer){
        if (front == -1 && rear == -1){
            front = rear = 0;
            waiting[rear]=customer;
            System.out.println("Customer added to Waiting");
        }
        else if ((rear+1)%maxWaitingCapacity == front){
            System.out.println("Waiting Queue is full");
        }
        else{
            rear = (rear+1)%maxWaitingCapacity;
            waiting[rear] = customer;
            System.out.println("Customer Added to Waiting ");
        }
    }

    public static void removeWaiting(FoodQueue queue,int queueNumber){
        if (front == -1 && rear == -1){
            System.out.println("No Customers in Waiting Queue");
        }
        else if (front == rear){
            queue.addCustomerFromWaiting(waiting[front],queueNumber);
            front = rear = -1;
        }
        else{
            queue.addCustomerFromWaiting(waiting[front],queueNumber);
            front = (front+1)%maxWaitingCapacity;
        }

    }

//*************************************GUI***************************************
/*Launch GUI Multiple Times
* */
    private static volatile boolean javaFxLaunched = false;
    public static void myLaunch(Class<? extends Application> applicationClass){
        if (!javaFxLaunched) {
            // This Runs For the First Time
            Platform.setImplicitExit(false);
            new Thread(() -> Application.launch(applicationClass)).start();
            javaFxLaunched = true;
        } 
        else {
            // This Runs if It has Launched for the first time
            Platform.runLater(() -> {
                try
                {
                    Application application = applicationClass.getDeclaredConstructor().newInstance();
                    Stage primaryStage = new Stage();
                    application.start(primaryStage);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        }
    }
}
