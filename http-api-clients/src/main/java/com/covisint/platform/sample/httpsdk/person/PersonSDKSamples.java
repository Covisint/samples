/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.person;

import java.util.List;

import com.covisint.core.http.service.core.Page;
import com.covisint.platform.legacy.address.Address;
import com.covisint.platform.legacy.phone.Phones;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.user.client.sdk.PersonSDK;
import com.covisint.platform.user.client.sdk.PersonSDK.PersonClient;
import com.covisint.platform.user.core.person.Person;

public final class PersonSDKSamples {

    private static PersonClient createPersonClient() {
        return new PersonSDK(ServiceUrl.PERSON_V1.getValue()).newClient();
    }

    /** Performs CRUD operations (as well as search) on person resources. */
    public static void personCRUD() {

        // Create a new instance of the client.
        PersonClient personClient = createPersonClient();

        // Start building a new person resource.
        Person person = new Person();

        // Set the person's parent organization.
        person.setOrganizationId("7d1e0d1abe7d");

        // Set basic preferences.
        person.setPreferredCurrency("USD");
        person.setPreferredLanguage("en");
        person.setPreferredTimezone("America/New_York");

        // Set personal info.
        person.setEmail("bob@covisint.com");
        person.setGivenName("Bob");
        person.setSurname("Smith");
        person.setJobTitle("Software Developer");

        // Set person's address
        Address address = new Address();
        address.setAddress1("One Campus Martius");
        address.setAddress2("Suite 700");
        address.setCity("Detroit");
        address.setState("MI");
        address.setPostal("48226");
        address.setCountry("US");
        person.setMainAddress(address);

        // Set person's phone numbers.
        Phones phones = new Phones();
        phones.setMain("313.961.4100");
        phones.setFax("313.961.4200");
        person.setPhones(phones);

        // Create the person.
        Person createdPerson = personClient.add(person).checkedGet();

        System.out.println("Created person ID is " + createdPerson.getId());

        // Retrieve person resource by ID.

        // The id of the person resource to retrieve.
        String personId = "04ce20c7a824";

        // Retrieve the person.
        person = personClient.get(personId).checkedGet();

        System.out.println("Retrieved person: " + person.getGivenName() + " " + person.getSurname());

        // Update person's job title.
        person.setJobTitle("SVP, Operations");

        // Persist the changes.
        Person updated = personClient.update(person).checkedGet();

        System.out.println("Successfully update person's job title to " + updated.getJobTitle());

        // Search by a person's username.
        List<Person> search = personClient.search(null, null, "johnsmith", null, Page.DEFAULT).checkedGet();

        System.out.println("Search produced " + search.size() + " results.");
    }

    /** Activate a person. */
    public static void activatePerson() {

        PersonClient personClient = createPersonClient();

        // The id of the person to activate.
        String personId = "c2e91ba21450";

        personClient.activate(personId).checkedGet();

        System.out.println("Activated person " + personId);
    }

    /** Suspend a person. */
    public static void suspendPerson() {

        PersonClient personClient = createPersonClient();

        // The id of the person to suspend.
        String personId = "29df4895f735";

        personClient.suspend(personId).checkedGet();

        System.out.println("Suspended person " + personId);
    }

    /** Unsuspend a person. */
    public static void unsuspendPerson() {

        PersonClient personClient = createPersonClient();

        // The id of the person to unsuspend.
        String personId = "31ae112f13be";

        personClient.unsuspend(personId).checkedGet();

        System.out.println("Unsuspended person " + personId);
    }

}
