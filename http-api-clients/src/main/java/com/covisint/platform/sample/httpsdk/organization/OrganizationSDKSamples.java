/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.organization;

import java.util.Arrays;
import java.util.List;

import com.covisint.core.http.service.core.NamedResourceReference;
import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.ResourceReferenceNode;
import com.covisint.platform.legacy.address.Address;
import com.covisint.platform.legacy.phone.Phones;
import com.covisint.platform.organization.client.sdk.OrganizationSDK;
import com.covisint.platform.organization.client.sdk.OrganizationSDK.OrganizationClient;
import com.covisint.platform.organization.core.organization.Organization;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.google.common.collect.Iterables;

public final class OrganizationSDKSamples {

    private static OrganizationClient createOrganizationClient() {
        return new OrganizationSDK(ServiceUrl.ORG_V1.getValue()).newClient();
    }

    /** Performs CRUD (and search) on organization resources. */
    public static void organizationCRUD() {

        // Create a new instance of the client.
        OrganizationClient organizationClient = createOrganizationClient();

        // Start building a new organization resource.
        Organization organization = new Organization();

        organization.setName("My Organization");

        // Set the organization's address.
        Address address = new Address();
        address.setAddress1("One Campus Martius");
        address.setAddress2("Suite 700");
        address.setCity("Detroit");
        address.setState("MI");
        address.setPostal("48226");
        address.setCountry("USA");
        organization.setAddress(address);

        // Set organization's phone numbers.
        Phones phones = new Phones();
        phones.setMain("313.961.4100");
        phones.setFax("313.961.4200");
        organization.setPhones(phones);

        // Set organiation's contact info.
        organization.setUrl("http://covisint.com");
        organization.setEmail("sales@covisint.com");

        // Create the org.
        Organization createdOrganization = organizationClient.add(organization).checkedGet();

        System.out.println("Created organization ID is " + createdOrganization.getId());

        /* Retrieve an organization by ID. */

        // The id of the organization resource to retrieve.
        String organizationId = "04ce20c7a824";

        // Retrieve the person.
        organization = organizationClient.get(organizationId).checkedGet();

        System.out.println("Retrieved organization: " + organization);

        /* Search organizations by name. */

        // Don't apply any special pagination params.
        Page page = Page.DEFAULT;

        // Execute the search.
        List<Organization> search = organizationClient.search(Arrays.asList("My Organization"), null, null, page)
                .checkedGet();

        System.out.println("Search produced " + search.size() + " results.");

        /* Update person details. */

        // Retrieve the person to be updated.
        organization = organizationClient.get("04ce20c7a824").checkedGet();

        // Update org name.
        organization.setName("ABC Org");

        // Persist the changes.
        Organization updated = organizationClient.update(organization).checkedGet();

        System.out.println("Successfully update organization's name to " + updated.getName());
    }

    /** Retrieves an organization hierarchy given a root organization id. */
    public static void organizationHierarchy() {

        OrganizationClient client = createOrganizationClient();

        String rootOrganizationId = "a1c3de4010a01";

        ResourceReferenceNode<NamedResourceReference> root = client.getHierarchy(rootOrganizationId).checkedGet();

        System.out.println("Organization " + rootOrganizationId + " has " + Iterables.size(root.getChildren())
                + " children.");
    }

}
