package com.covisint.papi.sample.android.openregistration.model;

/**
 * Created by Nitin.Khobragade on 2/10/2015.
 */
public class PasswordAccount {
    String id;
    Integer version;
    String creator;
    String creatorAppId;
    Long creation;
    String realm;
    String username;
    String passwordPolicyId;
    String authenticationPolicyId;
    Long expiration;
    Boolean locked;
    Integer unlockInstant;
    String subject;
    String password;

    public PasswordAccount() {
        version = 0;
    }

    public String getId() {
        return id;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPasswordPolicyId() {
        return passwordPolicyId;
    }

    public void setPasswordPolicyId(String passwordPolicyId) {
        this.passwordPolicyId = passwordPolicyId;
    }

    public String getAuthenticationPolicyId() {
        return authenticationPolicyId;
    }

    public void setAuthenticationPolicyId(String authenticationPolicyId) {
        this.authenticationPolicyId = authenticationPolicyId;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Integer getUnlockInstant() {
        return unlockInstant;
    }

    public void setUnlockInstant(Integer unlockInstant) {
        this.unlockInstant = unlockInstant;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCreation() {
        return creation;
    }

    public void setCreation(Long creation) {
        this.creation = creation;
    }
}
