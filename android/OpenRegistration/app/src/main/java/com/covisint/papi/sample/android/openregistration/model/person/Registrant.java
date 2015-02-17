package com.covisint.papi.sample.android.openregistration.model.person;

/**
 * Created by Nitin.Khobragade on 2/17/2015.
 */
public class Registrant {
    private String id;
    private String realm;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
