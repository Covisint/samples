
package com.covisint.platform.sample.httpsdk.service;

import java.util.List;

import com.covisint.core.http.service.core.Page;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.service.client.sdk.ServicePackageGrantSDK;
import com.covisint.platform.service.client.sdk.ServicePackageGrantSDK.ServicePackageGrantClient;
import com.covisint.platform.service.core.service.grant.ServicePackageGrant;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

public class ServicePackageGrantSDKSamples {

    private static ServicePackageGrantClient createServicePackageGrantClient() {
        return new ServicePackageGrantSDK(ServiceUrl.SERVICE_V1.getValue()).newClient();
    }

    /** View, assign and revoke person package grants. */
    public static void personPackageGrants() {

        // Get an instance of the client.
        ServicePackageGrantClient client = createServicePackageGrantClient();

        String personId = "1ad41d2dab3c";
        String packageId = "120da010c33de";

        // Assign the grant.
        ServicePackageGrant grant = client.assignPersonGrant(personId, packageId).checkedGet();

        System.out.println("Granted person package: " + grant);

        // Get the grant we just issued.
        ServicePackageGrant retrievedGrant = client.getPersonGrant(personId, packageId).checkedGet();

        System.out.println("Retrieved person package grant: " + retrievedGrant);

        // Let's find all package grants issued to this person.
        Iterable<ServicePackageGrant> results = client.listPersonGrants(personId, Page.DEFAULT).checkedGet();

        System.out.println(Iterables.size(results) + " packages granted to person " + personId);
    }

    /** View, assign and revoke organization package grants. */
    public static void organizationPackageGrants() {

        // Get an instance of the client.
        ServicePackageGrantClient client = createServicePackageGrantClient();

        String organizationId = "01ce1d23aae5";
        String packageId = "001cbd314d01d";

        // Assign the grant.
        ServicePackageGrant grant = client.assignOrganizationGrant(organizationId, packageId).checkedGet();

        System.out.println("Granted organization package: " + grant);

        // Get the grant we just issued.
        ServicePackageGrant retrievedGrant = client.getOrganizationGrant(organizationId, packageId).checkedGet();

        System.out.println("Retrieved organization package grant: " + retrievedGrant);

        // Let's find all package grants issued to this organization.
        Iterable<ServicePackageGrant> results = client.listOrganizationGrants(organizationId, Page.DEFAULT)
                .checkedGet();

        System.out.println(Iterables.size(results) + " packages granted to organization " + organizationId);
    }

    /** Search all grants for a given package, optionally scoped to person or organization grantees. */
    public static void searchPackageGrants() {

        // Get an instance of the client.
        ServicePackageGrantClient client = createServicePackageGrantClient();

        // Filter by the package id of interest.
        String packageId = "51a4ce2ee1ac3";
        Multimap<String, String> filter = ArrayListMultimap.<String, String> create();
        filter.put("grantedPackageId", packageId);

        // Now list all grants for that package.
        List<ServicePackageGrant> grants = client.search(packageId, null, Page.DEFAULT).checkedGet();

        System.out.println("Retrieved " + grants.size() + " grants issued for package " + packageId);
    }

}
