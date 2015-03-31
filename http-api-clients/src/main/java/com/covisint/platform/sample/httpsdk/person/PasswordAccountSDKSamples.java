/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.person;

import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.user.client.sdk.PasswordAccountSDK;
import com.covisint.platform.user.client.sdk.PasswordAccountSDK.PasswordAccountClient;
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

        // Build the account object.
        PasswordAccount account = new PasswordAccount();
        account.setUsername("johnsmith");
        account.setPassword("$up3r_SecR3t");
        account.setAuthnPolicyId("4e9a64577378");
        account.setPasswordPolicyId("ee0b54dceb9a");
        account.setVersion(1L);

        // Update the account.
        PasswordAccount updated = client.updatePasswordAccount("3792c2000ad5", account).checkedGet();

        System.out.println("Updated or specified password account for " + updated.getUsername());
    }

}
