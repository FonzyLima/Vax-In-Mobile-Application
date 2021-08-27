package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

public class Users {

    public String firstname,middlename, lastname, email, phone, sex, bday,priority, houseNum, street, barangay, city, firstSchedule,firstTime, secondSchedule,secondTime, vacSite, uID;
    public Boolean isAdmin, isRegistered, isFirstDose, isComplete, isScheduled, isSelected;

    public Users(){}

    public Users(String firstname, String middlename, String lastname, String email, String phone, String sex, String bday, String uId){
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.bday = bday;
        this.uID = uId;
        this.isSelected = false;
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
