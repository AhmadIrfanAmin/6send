package com.example.noman.myvoicerecorder;

/**
 * Created by noman on 1/2/2018.
 */

public class emailData {
    String id;
    String name;
    String emailAddress;


    public emailData(){

    }

    public emailData(String name, String emailAddress, String id) {
        this.name = name;
        this.id = id;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }



    public void setId(String id) {
        this.id = id;
    }
}
