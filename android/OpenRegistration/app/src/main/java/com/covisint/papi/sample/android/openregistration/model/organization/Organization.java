package com.covisint.papi.sample.android.openregistration.model.organization;

import com.covisint.papi.sample.android.openregistration.model.AuthenticationPolicy;
import com.covisint.papi.sample.android.openregistration.model.PAPIModel;
import com.covisint.papi.sample.android.openregistration.model.PasswordPolicy;
import com.covisint.papi.sample.android.openregistration.model.contact.Address;
import com.covisint.papi.sample.android.openregistration.model.contact.Phone;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Nitin.Khobragade on 2/2/2015.
 */
public class Organization extends PAPIModel {
    private String name;
    private Org parentOrganization;
    private Org rootOrganization;
    private ArrayList<Address> addresses;
    private ArrayList<Phone> phones;
    private String url;
    private String authDomain;
    private String organizationType;
    private String accountNumber;
    private String duns;
    @SerializedName("public")
    private boolean accessibility;
    private PasswordPolicy passwordPolicy;
    private AuthenticationPolicy authenticationPolicy;

    public Organization() {
    }

    public Organization(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public long getCreation() {
        return creation;
    }

    public String getRealm() {
        return realm;
    }

    public String getStatus() {
        return status;
    }

    public Org getParentOrganization() {
        return parentOrganization;
    }

    public Org getRootOrganization() {
        return rootOrganization;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public ArrayList<Phone> getPhones() {
        return phones;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthDomain() {
        return authDomain;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getDuns() {
        return duns;
    }

    public boolean isAccessibility() {
        return accessibility;
    }

    public PasswordPolicy getPasswordPolicy() {
        return passwordPolicy;
    }

    public AuthenticationPolicy getAuthenticationPolicy() {
        return authenticationPolicy;
    }

    @Override
    public String toString() {
        return name;
    }
}
