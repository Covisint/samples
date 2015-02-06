package com.covisint.papi.sample.android.openregistration.model.person;

/**
 * Created by Nitin.Khobragade on 2/4/2015.
 */
public class Name {
    private static final String SINGLE_SPACE = " ";
    private String prefix;
    private String given;
    private String middle;
    private String surname;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFullName() {
        StringBuilder builder = new StringBuilder();
        if (prefix != null && prefix.trim().length() > 0) {
            builder.append(prefix);
            builder.append(SINGLE_SPACE);
        }
        if (given != null && given.trim().length() > 0) {
            builder.append(given);
            builder.append(SINGLE_SPACE);
        }
        if (middle != null && middle.trim().length() > 0) {
            builder.append(middle);
            builder.append(SINGLE_SPACE);
        }
        if (surname != null && surname.trim().length() > 0) {
            builder.append(surname);
            builder.append(SINGLE_SPACE);
        }
        if (suffix != null && suffix.trim().length() > 0) {
            builder.append(suffix);
        }
        return builder.toString().trim();
    }
}
