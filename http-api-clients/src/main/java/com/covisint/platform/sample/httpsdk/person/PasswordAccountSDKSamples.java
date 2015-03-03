/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.person;

import org.apache.http.protocol.BasicHttpContext;

import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.user.client.person.account.password.PasswordAccountClient;
import com.covisint.platform.user.client.person.account.password.PasswordAccountSDK;
import com.covisint.platform.user.core.person.Person;
import com.covisint.platform.user.core.person.account.password.PasswordAccount;

public final class PasswordAccountSDKSamples {

    private static PasswordAccountClient createPasswordAccountClient() {
        return new PasswordAccountSDK(ServiceUrl.PERSON_V1.getValue()).create();
    }

    /** Retrieve a person's password account. */
    public static void getPersonPasswordAccount() {

        // Create a new instance of the client.
        PasswordAccountClient client = createPasswordAccountClient();

        // The id of the person.
        String personId = "e4d7e969af82";

        // Retrieve the account.
        PasswordAccount account = client.get(personId, new BasicHttpContext()).checkedGet();

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
        account.setUsername("USER0002");
        account.setPassword("$up3r_SecR3t");
        account.setOwner(owner);
        account.setAuthnPolicyId("4e9a64577378");
        account.setPasswordPolicyId("ee0b54dceb9a");
        account.setVersion(1L);

        // Update the account.
        PasswordAccount updated = client.persist(account, new BasicHttpContext()).checkedGet();

        System.out.println("Updated or specified password account for " + updated.getUsername());
    }

}
