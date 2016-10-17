package com.example.winston.myapplication;

/**
 * Created by Winston on 10/16/2016.
 */

public class Transaction {
    private Transaction transaction;
    private String company;
    private String date;
    private String name;
    private String cost;

    public Transaction(String company, String date, String name, String cost) {
        this.company = company;
        this.date = date;
        this.name = name;
        this.cost = cost;
    }

    public String getCompany() {
        return company;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
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
}
