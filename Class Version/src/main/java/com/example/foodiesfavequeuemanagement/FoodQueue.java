package com.example.foodiesfavequeuemanagement;

import java.util.ArrayList;

public class FoodQueue{
    private int maxQueueLimit;  // Maximum limit of customers in the queue
    private ArrayList<Customer> customers = new ArrayList<>();  // ArrayList to store the customers in the queue
    
    public FoodQueue(int maxQueueLimit){        // Constructor to initialize the FoodQueue object with the maximum queue limit
        this.maxQueueLimit = maxQueueLimit;
        this.customers = new ArrayList<>();
    }

    public int getMaxQueueLimit(){
        return maxQueueLimit;
    }

    public int getQueueSize(){
        return customers.size();
    }

    public ArrayList<Customer> getQueue(){
        return customers;
    }

    public void addCustomer(Customer customer, int queueIndex){       // Method to add a customer to the queue at a specific index
        if (customers.size() < maxQueueLimit) {
            customers.add(customer);
            System.out.println("\n************************************"+
                            "\nCustomer "+customer.getFirstName()+" added to the Queue "+(queueIndex+1)+
                            "\n************************************\n");
        } else {
            System.out.println("Queue is full. Customer " + customer.getFirstName() + " cannot be added.");
            System.out.println("Customer Added to Waiting List");
        }
    }

    public void addCustomerFromWaiting(Customer customer, int queueNumber){      // Method to add a customer from the waiting list to a specific queue
        customers.add(customer);
        System.out.println("\n************************************"+
                            "\nCustomer "+customer.getFirstName()+" added From Waiting List to Queue "+queueNumber+
                            "\n************************************\n");
    }

    public void printEmptyQueue(int maxPerQueue , int queueNum){       // Method to print the status of an empty queue
        System.out.println("Queue " + queueNum + " : ");
        for (int i=0; i<maxPerQueue; i++){
            if (i<getQueueSize()){
                System.out.println("\tSlot "+(i+1)+" : "+getQueue().get(i).getFullName());
            }
            else{
                System.out.println("\tSlot "+(i+1)+ " : Empty");
            }
        }
        System.out.println();
    }

    public void removeCustomer(int customerIndex){
        this.customers.remove(customerIndex);
    }

}