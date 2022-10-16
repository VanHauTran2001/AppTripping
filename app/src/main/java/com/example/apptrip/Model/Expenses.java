package com.example.apptrip.Model;

public class Expenses {
    private int IdExpenses;
    private int idTrip;
    private String type;
    private String amount;
    private String timeOf;

    public Expenses(int idExpenses, int idTrip, String type, String amount, String timeOf) {
        IdExpenses = idExpenses;
        this.idTrip = idTrip;
        this.type = type;
        this.amount = amount;
        this.timeOf = timeOf;
    }

    public Expenses() {
    }

    public int getIdExpenses() {
        return IdExpenses;
    }

    public void setIdExpenses(int idExpenses) {
        IdExpenses = idExpenses;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimeOf() {
        return timeOf;
    }

    public void setTimeOf(String timeOf) {
        this.timeOf = timeOf;
    }
}
