package com.covisint.papi.sample.android.openregistration.model.contact;

/**
 * Created by Nitin.Khobragade on 2/2/2015.
 */
public class Phone {
    private PhoneType type;
    private String number;

    public Phone(PhoneType type, String number) {
        this.type = type;
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }
}
