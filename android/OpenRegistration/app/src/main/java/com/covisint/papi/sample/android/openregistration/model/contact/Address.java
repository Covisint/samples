package com.covisint.papi.sample.android.openregistration.model.contact;

import java.util.ArrayList;

/**
 * Created by Nitin.Khobragade on 2/2/2015.
 */
public class Address {
    private ArrayList<String> streets;
    private String city;
    private String state;
    private String country;
    private String postal;

    public ArrayList<String> getStreets() {
        return streets;
    }

    public void setStreets(ArrayList<String> streets) {
        this.streets = streets;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getAddress() {
        StringBuilder builder = new StringBuilder();
        for (String street : streets) {
            if (street != null && street.trim().length() > 0)
                builder.append(street + "\n");
        }
        if (city != null && city.trim().length() > 0)
            builder.append(city + "\n");
        if (state != null && state.trim().length() > 0)
            builder.append(state + "\n");
        if (country != null && country.trim().length() > 0)
            builder.append(country + "\n");
        if (postal != null && postal.trim().length() > 0)
            builder.append(postal + "\n");
        return builder.toString().trim();
    }
}
