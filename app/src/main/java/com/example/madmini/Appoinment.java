package com.example.madmini;

public class Appoinment {



    private String App_Id;
    private String ownerName;
    private  String petName;
    private String Type;
    private String Breed;
    private String Date;
    private String Diseases;

    public String getApp_Id() {
        return App_Id;
    }

    public void setApp_Id(String app_Id) {
        App_Id = app_Id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getBreed() {
        return Breed;
    }

    public void setBreed(String breed) {
        Breed = breed;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDiseases() {
        return Diseases;
    }

    public void setDiseases(String diseases) {
        Diseases = diseases;
    }

    public String getVaccine() {
        return Vaccine;
    }

    public void setVaccine(String vaccine) {
        Vaccine = vaccine;
    }

    public String getSurgery() {
        return Surgery;
    }

    public void setSurgery(String surgery) {
        Surgery = surgery;
    }

    private String Vaccine;
    private String Surgery;

    public Appoinment() {
    }

    public String getownerName() {
        return ownerName;
    }

    public void setownerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getpetName() {
        return petName;
    }

    public void setpetName(String petName) {
        this.petName = petName;
    }
}
