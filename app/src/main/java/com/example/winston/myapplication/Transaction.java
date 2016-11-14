package com.example.winston.myapplication;

/**
 * Created by Winston on 10/16/2016.
 */

/**
 * The purpose of the Transaction class is to create a method for storing each time a
 * member makes a purchase between the pair
 * Thus far it stores the basic information required to display in the card views
 *
 */
public class Transaction {
    private String transaction;
    //The name or location of where money was spent
    private String company;
    //The date of the transaction
    private String date;
    //The purchaser of the goods
    private String name;
    //The actual cost of the transaction
    private String cost;
    //Type of transaction taking place
    private String type;

    public Transaction(String transaction, String company, String date, String name, String cost, String type) {
        this.transaction = transaction;
        this.company = company;
        this.date = date;
        this.name = name;
        this.cost = cost;
        this.type = type;
    }


    public String getCompany() {
        return company;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setType(String type){ this.type = type;}

    public String getType(){ return type;}

}
