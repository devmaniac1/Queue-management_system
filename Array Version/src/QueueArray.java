import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class QueueArray {
    public static Scanner input = new Scanner(System.in);
    public static int burgersAvailable = 50;
    public static int burgerLimitWarning = 10;
    public static void main(String[] args) {

        boolean loop = true;
        int queue1Size = 2;    // define Queue Size for Queues
        int queue2Size = 3;
        int queue3Size = 5;

        String[] queue1 = new String[queue1Size];  // Initialize by creating queue by queue size
        String[] queue2 = new String[queue2Size];
        String[] queue3 = new String[queue3Size];
        String[][] allQueue = {queue1,queue2,queue3};

        while(loop) {
            System.out.println("\n--------------------------------------------------------\n"+
                                "**************************MENU**************************\n"+
                                "--------------------------------------------------------\n");
            System.out.println("100 or VFQ: View all Queues.\n"+
                    "101 or VEQ: View all Empty Queues.\n"+
                    "102 or ACQ: Add customer to a Queue.\n"+
                    "103 or RCQ: Remove a customer from a Queue.\n"+
                    "104 or PCQ: Remove a served customer.\n"+
                    "105 or VCS: View Customers Sorted in alphabetical order.\n"+
                    "106 or SPD: Store Program Data into file.\n"+
                    "107 or LPD: Load Program Data from file.\n"+
                    "108 or STK: View Remaining burgers Stock.\n"+
                    "109 or AFS: Add burgers to Stock.\n"+
                    "999 or EXT: Exit the Program.");
            System.out.print("\nEnter Option: ");
            String option = input.nextLine().toUpperCase();

            switch (option) {
                case "100","VFQ":
                    VFQ(queue1,queue2,queue3);
                    break;

                case "101","VEQ":
                    VEQ(queue1, queue1Size, "Queue 1");
                    VEQ(queue2, queue2Size, "Queue 2");
                    VEQ(queue3, queue3Size, "Queue 3");
                    System.out.println();
                    break;

                case "102","ACQ":
                    ACQ(queue1,queue2,queue3);
                    break;

                case "103","RCQ":
                    RCQ(queue1,queue2,queue3);
                    break;

                case "104","PCQ":
                    PCQ(queue1,queue2,queue3);
                    break;

                case "105","VCS":
                    VCS(queue1,"Queue 1");
                    VCS(queue2,"Queue 2");
                    VCS(queue3,"Queue 3");
                    break;

                case "106","SPD":
                    SPD(allQueue);
                    break;

                case "107","LPD":
                    LPD(queue1,queue2,queue3);
                    break;

                case "108","STK":
                    STK();
                    break;

                case "109","AFS":
                    AFS();
                    break;

                case "999","EXT":
                    System.out.println("Exitting Program");
                    loop = false;
                    break;
                default: System.out.println("Invalid Input");
            }
        }
    }


    public static void VFQ(String[] queue1, String[] queue2, String[] queue3){
        //https://www.w3schools.com/java/java_conditions_shorthand.asp

        int maxLength = Math.max(queue1.length, Math.max(queue2.length, queue3.length));   // Get the maximum queue length from all queues

        System.out.println("*************************\n"+
                "*\t\tCashiers \t\t*\n"+
                "*************************");

        for (int i = 0; i < maxLength; i++) {          // Loop through each position in the queues and print the queue status
            if (i < 2)
                System.out.print(queue1[i] == null ? "X\t\t\t" : "O\t\t\t");
            if (i < 3) {
                if (i == 2)
                    System.out.print("\t\t\t");
                System.out.print(queue2[i] == null ? "X\t\t\t" : "O\t\t\t");
            }
            if (i < 5) {
                if (i == 3 || i == 4)
                    System.out.print("\t\t\t\t\t\t");
                System.out.print(queue3[i] == null ? "X" : "O");
            }
            System.out.println();
        }
        System.out.println("\nO-Occupied\tX-Not Occupied\n");
    }

    public static void VEQ(String[] queue, int queueSize, String queueName){
        System.out.println("\n\t\t"+queueName+":");

        for(int i =0 ; i<queueSize; i++){          // Loop through each slot in the queue and print the queue status
            if (queue[i] == null){
                System.out.println("\t\t\t\tSlot "+(i+1)+" : Empty");
            }
            else{
                System.out.println("\t\t\t\tSlot "+(i+1)+" : "+queue[i]);
            }
        }
    }

    public static void ACQ(String[] queue1, String[] queue2, String[] queue3){

        if(burgersAvailable>0){
            System.out.print("\nEnter Name: ");
            String name = input.nextLine();
            try{
                System.out.print("Enter Desired Queue Number: ");
                int queueNumber = Integer.parseInt(input.nextLine());
                while (queueNumber > 3 || queueNumber < 1){                   // Validate the inputted queue number
                    System.out.println("\nEnter Queue Numbers between 1 and 3");
                    System.out.print("Enter Desired Queue Number: ");
                    queueNumber = input.nextInt();
                    input.nextLine();
                }

                // Add the customer to the selected queue
                if (queueNumber == 1){
                    addToQueue(queue1,name,"queue 1");
                }
                if (queueNumber == 2){
                    addToQueue(queue2,name,"queue 2");
                }
                if (queueNumber == 3){
                    addToQueue(queue3,name,"queue 3");
                }

            }
            catch(NumberFormatException nfe){
                System.out.println("Only Intergers Alllowed");
            }


        }
        else{
            System.out.println("\n\t*************************SORRY*************************\n"+
                    "\tCannot Add customers to queue due to insufficient stock\n"+
                    "\t*******************************************************\n");
        }
    }

    public static void addToQueue(String[] queue, String name, String queueName){
        boolean queueLimitReached = true;
        for (int i = 0 ; i<queue.length; i++){           // Loop through the slots in the queue and add the customer to the first available slot
            if (queue[i] == null){
                queue[i] = name;
                System.out.println("\n*****"+name+" Successfully added to "+queueName+"*****\n");
                burgersAvailable-=5;
                if (burgersAvailable<=burgerLimitWarning){                     // Check if the stock is running low and display a warning
                    System.out.println("\n\t\t***********WARNING***********\n"+
                            "\t\tBurgers Running out of Stock!\n"+
                            "\t\t*****************************");
                }
                queueLimitReached = false;
                break;
            }
        }
        if(queueLimitReached){          // Display a queue limit warning if the queue is full
            System.out.println("\n\t*********Queue Limit Warning*********\n"+
                    "\tThe Limit in "+queueName+" has been reached\n"+
                    "\t*************************************");
        }
    }

    public static void RCQ(String[] queue1, String[] queue2, String[] queue3){
        System.out.print("Enter Name to Remove: ");
        String nameToRemove = input.nextLine().toLowerCase();
        removeName(queue1,nameToRemove,"queue 1");
        removeName(queue2,nameToRemove,"queue 2");
        removeName(queue3,nameToRemove,"queue 3");
    }

    public static void removeName(String[] queue, String nameToRemove,String queueName){
        boolean nameFound = true;
        for (int i = 0; i<queue.length; i++){            // Loop through the slots in the queue and remove the customer with a matching name
            if (queue[i] != null){
                if (queue[i].toLowerCase().equals(nameToRemove)){
                    queue[i]=null;
                    System.out.println("Found in "+queueName+" Slot "+ (i+1));
                    System.out.println("Customer Removed");
                    burgersAvailable+=5;
                    int customerIndex = i;

                    for (int j=customerIndex;j<queue.length-1;j++){             // Moves the elements in the queue to fill the empty slot
                        queue[j] = queue[j+1];
                    }
                    queue[queue.length-1] = null;
                    nameFound = false;
                    break;
                }
            }
        }
        if (nameFound){
            System.out.println("Name not found in "+queueName);
        }
    }

    public static void PCQ(String[] queue1, String[] queue2, String[] queue3){
        System.out.print("Enter Name to Remove: ");
        String removeServedCustomer = input.nextLine().toLowerCase();
        boolean nameFound = removeServed(queue1,removeServedCustomer,"queue 1");
        if (nameFound == false){
            nameFound = removeServed(queue2,removeServedCustomer,"queue 2");
        }
        if(nameFound == false){
            nameFound = removeServed(queue3,removeServedCustomer,"queue 3");
        }
    }

    public static boolean removeServed(String[] queue, String customerName, String queueName){
        boolean nameFound = false;
        for (int i = 0; i<queue.length; i++){    // Loop through the slots in the queue and remove the customer with a matching name
            if (queue[i] != null){
                if (queue[i].toLowerCase().equals(customerName)){
                    queue[i]=null;
                    System.out.println("Found in "+queueName+" Slot "+ (i+1));
                    System.out.println("Customer Removed");
                    int customerIndex = i;
                    for (int j=customerIndex;j<queue.length-1;j++){      // Moves the elements in the queue to fill the empty slot
                        queue[j] = queue[j+1];
                    }
                    queue[queue.length-1] = null;
                    nameFound = true;
                    break;
                }
            }
        }
        if (nameFound == false){
            System.out.println("Name not found in "+queueName);
        }
        return nameFound;
    }

    public static void VCS(String[] queue, String queueName) {

        //https://www.geeksforgeeks.org/bubble-sort/
        // Bubble Sort to sort the customers in alphabetical order

        int queueLength = queue.length;

        for (int i = 0; i < queueLength - 1; i++) {
            for (int j = 0; j < queueLength - i - 1; j++) {
                if(queue[j] != null && queue[j+1] != null){
                    if (compareStrings(queue[j].toLowerCase(), queue[j + 1].toLowerCase()) > 0) {
                        String tempStore = queue[j];
                        queue[j] = queue[j + 1];
                        queue[j + 1] = tempStore;
                    }
                }
            }
        }

        System.out.println(queueName);
        for (int k=0;k<queue.length;k++){            // Print the sorted queue
            if (queue[k] != null){
                System.out.println("\tSlot "+(k+1)+" : "+queue[k]);
            }

        }
    }

    public static int compareStrings(String string1, String string2) {
        int minLength = Math.min(string1.length(), string2.length());

        for (int i = 0; i < minLength; i++) {          // Compare two strings lexicographically
            char character1 = string1.charAt(i);
            char character2 = string2.charAt(i);

            if (character1 < character2) {
                return -1;
            } else if (character1 > character2) {
                return 1;
            }
        }

        return string1.length() - string2.length();
    }



    public static void SPD(String[][] allQueue) {
        int queueIndex = 1;
        try {
            FileWriter writer = new FileWriter("text.txt");

            // Write the customer details from each queue to the file
            for (String[] queue : allQueue){
                for (String name : queue){
                    if (name != null){
                        writer.write("Queue"+Integer.toString(queueIndex)+" "+name);
                        writer.write(System.lineSeparator());
                    }
                }
                queueIndex++;
            }
            writer.close();
            System.out.println("Array written to file successfully.");
        }
        catch (IOException e) {
            System.out.println("An error occurred while writing the array to the file.");
        }
    }

    public static void LPD(String[] queue1, String[] queue2, String[] queue3){
        burgersAvailable = 50;
        Arrays.fill(queue1,null);
        Arrays.fill(queue2,null);
        Arrays.fill(queue3,null);

        try {
            File readFile = new File("text.txt");
            Scanner rf = new Scanner(readFile);
            while (rf.hasNextLine()){                // Read the customer details from the file and add them to the respective queues
                String lineText = rf.nextLine();
                String[] splitted = lineText.split(" ");
                if (splitted[0].equals("Queue1")){
                    addFromText(queue1,splitted[1]);
                } else if (splitted[0].equals("Queue2")) {
                    addFromText(queue2,splitted[1]);
                } else {
                    addFromText(queue3,splitted[1]);
                }
            }

            System.out.println("Customer Details Loaded");
            rf.close();
        }
        catch (IOException e) {
            System.out.println("Error while reading a file.");

        }

    }

    public static void addFromText(String[] queue,String name){
        for(int i=0 ; i<queue.length;i++){
            if(queue[i] == null){
                queue[i] = name;
                burgersAvailable -= 5;
                break;
            }
        }
    }

    public static void STK(){
        System.out.println("The available burgers in the stock: "+burgersAvailable);
    }

    public static void AFS() {
        boolean isInt = false;
        while(!isInt) {
            try {
                System.out.print("Enter Number of Burgers to Add: ");
                int burgersToAdd = Integer.parseInt(input.nextLine());
                burgersAvailable = Math.addExact(burgersAvailable, burgersToAdd);
                isInt = true;
                System.out.println("\n\t********RESTOCKED*********\n"+
                        "\tBurgers Added Successfully\n"+
                        "\t**************************\n");
            }
            catch (InputMismatchException e) {
                System.out.println("Enter Only Integers");
            }
            catch (NumberFormatException ex) {
                System.out.println("Integer Needed");
            }
        }
    }
}
