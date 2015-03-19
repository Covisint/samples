/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.person;

import com.covisint.core.http.service.core.ResourceReference;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.user.client.sdk.PasswordAccountSDK;
import com.covisint.platform.user.client.sdk.PasswordAccountSDK.PasswordAccountClient;
import com.covisint.platform.user.core.person.Person;
import com.covisint.platform.user.core.person.account.password.PasswordAccount;

public final class PasswordAccountSDKSamples {

    private static PasswordAccountClient createPasswordAccountClient() {
        return new PasswordAccountSDK(ServiceUrl.PERSON_V1.getValue()).newClient();
    }

    /** Retrieve a person's password account. */
    public static void getPersonPasswordAccount() {

        // Create a new instance of the client.
        PasswordAccountClient client = createPasswordAccountClient();

        // The id of the person.
        String personId = "e4d7e969af82";

        // Retrieve the account.
        PasswordAccount account = client.getPasswordAccount(personId).checkedGet();

        System.out.println("Retrieved password account for username: " + account.getUsername());
    }

    /** Sets a person's password account. */
    public static void setPersonPasswordAccount() {

        // Create a new instance of the client.
        PasswordAccountClient client = createPasswordAccountClient();

        // The owner of the password account.
        Person owner = new Person();
        owner.setId("3792c2000ad5");

        // Build the account object.
        PasswordAccount account = new PasswordAccount();
        account.setUsername("johnsmith");
        account.setPassword("$up3r_SecR3t");
        account.setOwner(owner);
        account.setAuthnPolicy(new ResourceReference("4e9a64577378", "authenticationPolicy"));
        account.setPasswordPolicy(new ResourceReference("ee0b54dceb9a", "passwordPolicy"));
        account.setVersion(1L);

        // Update the account.
        PasswordAccount updated = client.updatePasswordAccount(account).checkedGet();

        System.out.println("Updated or specified password account for " + updated.getUsername());
    }

}