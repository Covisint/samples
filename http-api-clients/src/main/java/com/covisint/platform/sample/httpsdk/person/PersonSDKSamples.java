/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.person;

import java.util.Arrays;
import java.util.List;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.SortCriteria;
import com.covisint.platform.legacy.address.Address;
import com.covisint.platform.legacy.phone.Phones;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.user.client.person.PersonClient;
import com.covisint.platform.user.client.person.PersonSDK;
import com.covisint.platform.user.core.person.Person;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public final class PersonSDKSamples {

    private static PersonClient createPersonClient() {
        return new PersonSDK(ServiceUrl.PERSON_V1.getValue()).create();
    }

    /** Performs CRUD operations (as well as search) on person resources. */
    public static void personCRUD() {

        // Create a new instance of the client.
        PersonClient personClient = createPersonClient();

        // Start building a new person resource.
        Person person = new Person();

        // Set the person's parent organization.
        person.setOrganization("7d1e0d1abe7d");

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
        address.setCountry("USA");
        person.setMainAddress(address);

        // Set person's phone numbers.
        Phones phones = new Phones();
        phones.setMain("313.961.4100");
        phones.setFax("313.961.4200");
        person.setPhones(phones);

        // Create the person.
        Person createdPerson = personClient.add(person, new BasicHttpContext()).checkedGet();

        System.out.println("Created person ID is " + createdPerson.getId());

        // Retrieve person resource by ID.

        // The id of the person resource to retrieve.
        String personId = "04ce20c7a824";

        // Retrieve the person.
        person = personClient.get(personId, new BasicHttpContext()).checkedGet();

        System.out.println("Retrieved person: " + person.getGivenName() + " " + person.getSurname());

        // Update person's job title.
        person.setJobTitle("SVP, Operations");

        // Persist the changes.
        Person updated = personClient.persist(person, new BasicHttpContext()).checkedGet();

        System.out.println("Successfully update person's job title to " + updated.getJobTitle());

        // Search person resources by one or more IDs.

        // The unique id of the person resources to search.
        String[] personIds = { "0f7a031b9370", "211a20a6fb78", "4f6e76c4f732" };

        // Build the filter criteria.
        Multimap<String, String> filter = ArrayListMultimap.<String, String> create();
        filter.putAll("id", Arrays.asList(personIds));

        // Create the HTTP context to pass.
        HttpContext context = new BasicHttpContext();

        // Execute the search.
        List<Person> search = personClient.search(filter, SortCriteria.NONE, Page.DEFAULT, context).checkedGet();

        System.out.println("Search produced " + search.size() + " results.");
    }

    /** Activate a person. */
    public static void activatePerson() {

        PersonClient personClient = createPersonClient();

        // The id of the person to activate.
        String personId = "c2e91ba21450";

        personClient.activate(personId, new BasicHttpContext()).checkedGet();

        System.out.println("Activated person " + personId);
    }

    /** Suspend a person. */
    public static void suspendPerson() {

        PersonClient personClient = createPersonClient();

        // The id of the person to suspend.
        String personId = "29df4895f735";

        personClient.suspend(personId, new BasicHttpContext()).checkedGet();

        System.out.println("Suspended person " + personId);
    }

    /** Unsuspend a person. */
    public static void unsuspendPerson() {

        PersonClient personClient = createPersonClient();

        // The id of the person to unsuspend.
        String personId = "31ae112f13be";

        personClient.unsuspend(personId, new BasicHttpContext()).checkedGet();

        System.out.println("Unsuspended person " + personId);
    }

}
