package com.covisint.papi.sample.android.openregistration.model;

/**
 * Created by Nitin.Khobragade on 2/10/2015.
 */
public class PasswordAccount {
    String realm;
    String subject;
    String username;
    String password;
    Long version;
    String passwordPolicyId;
    String authenticationPolicyId;
    Integer expiration;
    Boolean locked;
    Integer unlockInstant;
    String creator;
    Long creation;

    public PasswordAccount() {
        version = 0L;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
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

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
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
