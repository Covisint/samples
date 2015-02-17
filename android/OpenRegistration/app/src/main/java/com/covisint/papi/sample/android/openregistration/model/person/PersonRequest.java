package com.covisint.papi.sample.android.openregistration.model.person;

import com.covisint.papi.sample.android.openregistration.model.common.RealmScopedResource;

/**
 * Created by Nitin.Khobragade on 2/17/2015.
 */
public class PersonRequest extends RealmScopedResource {
    private String justification;
    private Registrant registrant;
    private ServicePackageRequest servicePackageRequest;

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Registrant getRegistrant() {
        return registrant;
    }

    public void setRegistrant(Registrant registrant) {
        this.registrant = registrant;
    }

    public ServicePackageRequest getServicePackageRequest() {
        return servicePackageRequest;
    }

    public void setServicePackageRequest(ServicePackageRequest servicePackageRequest) {
        this.servicePackageRequest = servicePackageRequest;
    }
}
