package com.covisint.papi.sample.android.openregistration.model.packages;

import com.covisint.papi.sample.android.openregistration.model.common.Resource;

/**
 * Created by Nitin.Khobragade on 2/17/2015.
 */
public class Package extends Resource {
    private String status;
    private Grantee grantee;
    private ServicePackage servicePackage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Grantee getGrantee() {
        return grantee;
    }

    public void setGrantee(Grantee grantee) {
        this.grantee = grantee;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    @Override
    public String toString() {
        return servicePackage.getId();
    }
}
