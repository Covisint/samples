package com.covisint.papi.sample.android.openregistration.model.person;

import com.covisint.papi.sample.android.openregistration.model.PAPIModel;
import com.covisint.papi.sample.android.openregistration.model.contact.Address;
import com.covisint.papi.sample.android.openregistration.model.contact.Phone;

/**
 * Created by Nitin.Khobragade on 2/4/2015.
 */
public class Person extends PAPIModel {
    private Name name;
    private Address addresses;
    private String language;
    private String timezone;
    private Phone[] phones;
    private String title;
    private String email;
    private String organizationId;
    private String currency;

    public Person() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getCreation() {
        return creation;
    }

    public void setCreation(long creation) {
        this.creation = creation;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Address getAddresses() {
        return addresses;
    }

    public void setAddresses(Address addresses) {
        this.addresses = addresses;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Phone[] getPhones() {
        return phones;
    }

    public void setPhones(Phone[] phones) {
        this.phones = phones;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
