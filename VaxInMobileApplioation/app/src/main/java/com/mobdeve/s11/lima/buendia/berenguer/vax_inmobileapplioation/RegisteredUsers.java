package com.mobdeve.s11.lima.buendia.berenguer.vax_inmobileapplioation;

import android.widget.EditText;

public class RegisteredUsers {
    private String prioGroup, fName, lName, mName, email, number,
            bday, sex, street, barangay, city, firstSchedule, secondSchedule, vacSite;

    public RegisteredUsers(){}

    public RegisteredUsers(String priority, String fName, String lName, String mName, String email, String number, String bday,
                            String sex, String street, String barangay, String city){
        this.prioGroup = priority;
        this.fName = fName;
        this.lName = lName;
        this.mName = mName;
        this.email = email;
        this.number = number;
        this.bday = bday;
        this.sex = sex;
        this.street = street;
        this.barangay = barangay;
        this.city = city;
        this.firstSchedule = "TBA";
        this.secondSchedule = "TBA";
        this.vacSite = "TBA";
    }
}
