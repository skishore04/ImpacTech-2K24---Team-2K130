package com.example.hackathonappnew;

public class ReadWriteUserDetails
{
    public String name,email,phnum,pass;

    public ReadWriteUserDetails() {
    }

    public ReadWriteUserDetails(String fname, String femail, String number, String epass){
        this.name=fname;
        this.email=femail;
        this.phnum= number;
        this.pass = epass;
    }
}