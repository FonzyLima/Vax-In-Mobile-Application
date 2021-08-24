package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

public class Users {

    public String firstname, lastname, email, phone, sex, bday,priority, houseNum, street, barangay, city, firstSchedule,firstTime, secondSchedule,secondTime, vacSite;
    public Boolean isAdmin, isRegistered, isFirstDose, isComplete, isScheduled;

    public Users(){}

    public Users(String firstname, String lastname, String email, String phone, String sex, String bday){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.bday = bday;
        this.isAdmin = false;
        this.isRegistered = false;
        this.isScheduled = false;
        this.isFirstDose = false;
        this.isComplete = false;
        this.houseNum = "Register first";
        this.street = "Register first";
        this.barangay = "Register first";
        this.city = "Register first";
        this.priority = "Register first";
        this.firstSchedule = "TBA";
        this.secondSchedule = "TBA";
        this.vacSite = "TBA";
        this.firstTime = "TBA";
        this.secondTime = "TBA";
    }
}
