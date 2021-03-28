package com.example.madmini;

public class UserModels {
    String FirstName;
    String LastName;
    String Mobile;
    String Email;

    public UserModels() {

    }

    public UserModels(String firstName, String lastName, String mobile, String email) {
        FirstName = firstName;
        LastName = lastName;
        Mobile = mobile;
        Email = email;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getEmail() {
        return Email;
    }
}
