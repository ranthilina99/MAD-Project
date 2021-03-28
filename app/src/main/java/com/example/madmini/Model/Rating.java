package com.example.madmini.Model;

public class Rating {


    private String RID;
    private String UserName;
    private float Ratedvalue;
    private float precentage;

    public Rating() {
    }

    public Rating(String RID, String userName, float ratedvalue, float precentage) {
        this.RID = RID;
        UserName = userName;
        Ratedvalue = ratedvalue;
        this.precentage = precentage;
    }

    public String getRID() {
        return RID;
    }

    public void setRID(String RID) {
        this.RID = RID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public float getRatedvalue() {
        return Ratedvalue;
    }

    public void setRatedvalue(float ratedvalue) {
        Ratedvalue = ratedvalue;
    }

    public float getPrecentage() {
        return precentage;
    }

    public void setPrecentage(float precentage) {
        this.precentage = precentage;
    }
}
