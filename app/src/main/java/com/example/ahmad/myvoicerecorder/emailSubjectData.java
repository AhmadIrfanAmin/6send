package com.example.noman.myvoicerecorder;

/**
 * Created by noman on 1/2/2018.
 */

public class emailSubjectData {
    String id;
    String subject;

    public emailSubjectData(String id, String subject) {
        this.id = id;
        this.subject = subject;
    }
    public emailSubjectData(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
