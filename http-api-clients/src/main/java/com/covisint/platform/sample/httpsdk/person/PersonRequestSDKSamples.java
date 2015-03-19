/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.person;

import java.util.List;
import java.util.UUID;

import com.covisint.core.http.service.core.Page;
import com.covisint.platform.legacy.address.Address;
import com.covisint.platform.legacy.phone.Phones;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.user.client.sdk.PasswordAccountSDK;
import com.covisint.platform.user.client.sdk.PasswordAccountSDK.PasswordAccountClient;
import com.covisint.platform.user.client.sdk.PersonRequestSDK;
import com.covisint.platform.user.client.sdk.PersonRequestSDK.PersonRequestClient;
import com.covisint.platform.user.client.sdk.PersonSDK;
import com.covisint.platform.user.client.sdk.PersonSDK.PersonClient;
import com.covisint.platform.user.core.person.Person;
import com.covisint.platform.user.core.person.account.password.PasswordAccount;
import com.covisint.platform.user.core.person.request.PersonRequest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

public final class PersonRequestSDKSamples {

    private static PersonClient createPersonClient() {
        return new PersonSDK(ServiceUrl.PERSON_V1.getValue()).newClient();
    }

    private static PasswordAccountClient createPasswordAccountClient() {
        return new PasswordAccountSDK(ServiceUrl.PERSON_V1.getValue()).newClient();
    }

    private static PersonRequestClient createPersonRequestClient() {
        return new PersonRequestSDK(ServiceUrl.PERSON_V1.getValue()).newClient();
    }

    /** Perform CRUD operations (as well as search) on person request resources. */
    public static void personRequestCRUD() {

        // Create new instance of the client we will be using.
        PersonRequestClient client = createPersonRequestClient();
        PersonClient personClient = createPersonClient();
        PasswordAccountClient passwordClient = createPasswordAccountClient();

        // First, need to create the person record.
        Person person = new Person()
                .setOrganizationId("737e9ad36cc2")
                .setPreferredCurrency("USD")
                .setPreferredLanguage("en")
                .setPreferredTimezone("America/New_York")
                .setEmail("tom@covisint.com")
                .setGivenName("Tom")
                .setSurname("Jones")
                .setMainAddress(
                        new Address().setAddress1("One Campus Martius").setCity("Detroit").setState("MI")
                                .setPostal("48226").setCountry("US")).setPhones(new Phones().setMain("313.961.4100"));

        // Go ahead and create our person now.
        Person createdPerson = personClient.add(person).checkedGet();

        // Next, set the username/password for this person.
        PasswordAccount account = new PasswordAccount().setUsername(UUID.randomUUID().toString())
                .setPassword("Covisint$2015").setOwner(new Person().setId(createdPerson.getId()))
                .setAuthnPolicyId("8fe701224ecd").setPasswordPolicyId("3d7555e782a5").setVersion(1L);

        // Persist the credentials.
        passwordClient.updatePasswordAccount(account).checkedGet();

        // Set up the person request object.
        String registrantId = createdPerson.getId();
        PersonRequest request = new PersonRequest();
        request.setRegistrant(registrantId);
        request.setPackageId("a4ad06e9190a");
        request.setJustification("Need access.");

        // Create the person request.
        PersonRequest created = client.add(request).checkedGet();

        System.out.println("Created person request " + created.getId());

        // Next we will retrieve an existing person request. */

        // The id of the request being retrieved.
        String requestId = "bcfba3b9a42b";

        // Execute the call.
        request = client.get(requestId).checkedGet();

        System.out.println("Retrieved person request: " + request);

        // Set up a search filter to return all requests made by the registrant (person).
        Multimap<String, String> filter = ArrayListMultimap.<String, String> create();
        filter.put("registrantId", request.getRegistrant().getId());

        // Execute the search.
        List<PersonRequest> results = client.search(request.getRegistrant().getId(), Page.DEFAULT).checkedGet();

        System.out.println(Iterables.size(results) + " requests have been made by registrant "
                + request.getRegistrant().getId());

        // Delete the request.
        client.delete("abf886c7505a").checkedGet();

        System.out.println("Deleted person request " + requestId);
    }

    /** Approves a person request. */
    public static void approvePersonRequest() {

        // Create a new instance of the client.
        PersonRequestClient client = createPersonRequestClient();

        // The id of the request being approved.
        String requestId = "f4daa39948db";

        // Approve the request.
        client.approve(requestId).checkedGet();

        System.out.println("Approved person request " + requestId);
    }

}
