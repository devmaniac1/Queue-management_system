package com.example.foodiesfavequeuemanagement;

public class Customer{
    private String firstName;
    private String secondName;
    private int burgersRequired;


    public Customer(String firstName, String secondName, int burgersRequired) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.burgersRequired = burgersRequired;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setSecondName(String secondName){
        this.secondName = secondName;
    }

    public void setBurgersRequired(int burgersRequired){
        this.burgersRequired = burgersRequired;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getSecondName(){
        return this.secondName;
    }

    public int getBurgersRequired(){
        return this.burgersRequired;
    }

    public String getFullName(){
        return firstName+" "+secondName;
    }


}