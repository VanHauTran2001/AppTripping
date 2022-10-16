package com.example.apptrip.Model;

import java.io.Serializable;

public class Trip implements Serializable {
    private int idTrip;
    private String nameTrip;
    private String dateTrip;
    private String locateTrip;
    private String RequiredTrip;
    private String description;

    public Trip() {
    }

    public Trip(int idTrip, String nameTrip, String dateTrip, String locateTrip, String requiredTrip,String description) {
        this.idTrip = idTrip;
        this.nameTrip = nameTrip;
        this.dateTrip = dateTrip;
        this.locateTrip = locateTrip;
        this.RequiredTrip = requiredTrip;
        this.description = description;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public String getNameTrip() {
        return nameTrip;
    }

    public void setNameTrip(String nameTrip) {
        this.nameTrip = nameTrip;
    }

    public String getDateTrip() {
        return dateTrip;
    }

    public void setDateTrip(String dateTrip) {
        this.dateTrip = dateTrip;
    }

    public String getLocateTrip() {
        return locateTrip;
    }

    public void setLocateTrip(String locateTrip) {
        this.locateTrip = locateTrip;
    }

    public String getRequiredTrip() {
        return RequiredTrip;
    }

    public void setRequiredTrip(String requiredTrip) {
        RequiredTrip = requiredTrip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
