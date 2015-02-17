package com.covisint.papi.sample.android.openregistration.model.person;

/**
 * Created by Nitin.Khobragade on 2/17/2015.
 */
public class ServicePackageRequest {
    private String packageId;
    private String realm;
    private String code;
    private String codeKind;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeKind() {
        return codeKind;
    }

    public void setCodeKind(String codeKind) {
        this.codeKind = codeKind;
    }
}
