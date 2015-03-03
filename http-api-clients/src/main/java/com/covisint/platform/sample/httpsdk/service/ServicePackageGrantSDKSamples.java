
package com.covisint.platform.sample.httpsdk.service;

import org.apache.http.protocol.BasicHttpContext;

import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.service.client.service.grant.ServicePackageGrantClient;
import com.covisint.platform.service.client.service.grant.ServicePackageGrantSDK;
import com.covisint.platform.service.core.service.grant.ServicePackageGrant;
import com.google.common.collect.Iterables;

public class ServicePackageGrantSDKSamples {

    private static ServicePackageGrantClient createServicePackageGrantClient() {
        return new ServicePackageGrantSDK(ServiceUrl.SERVICE_V1.getValue()).create();
    }

    /** View, assign and revoke person package grants. */
    public static void personPackageGrants() {

        // Get an instance of the client.
        ServicePackageGrantClient client = createServicePackageGrantClient();

        String personId = "1ad41d2dab3c";
        String packageId = "120da010c33de";

        // Assign the grant.
        ServicePackageGrant grant = client.assignPersonGrant(personId, packageId, new BasicHttpContext()).checkedGet();

        System.out.println("Granted person package: " + grant);

        // Get the grant we just issued.
        ServicePackageGrant retrievedGrant = client.getPersonGrant(personId, packageId, new BasicHttpContext())
                .checkedGet();

        System.out.println("Retrieved person package grant: " + retrievedGrant);

        // Let's find all package grants issued to this person.
        Iterable<ServicePackageGrant> results = client.searchPersonGrants(personId, new BasicHttpContext())
                .checkedGet();

        System.out.println(Iterables.size(results) + " packages granted to person " + personId);
    }

    /** View, assign and revoke organization package grants. */
    public static void organizationPackageGrants() {

        // Get an instance of the client.
        ServicePackageGrantClient client = createServicePackageGrantClient();

        String organizationId = "01ce1d23aae5";
        String packageId = "001cbd314d01d";

        // Assign the grant.
        ServicePackageGrant grant = client.assignOrganizationGrant(organizationId, packageId, new BasicHttpContext())
                .checkedGet();

        System.out.println("Granted organization package: " + grant);

        // Get the grant we just issued.
        ServicePackageGrant retrievedGrant = client.getOrganizationGrant(organizationId, packageId,
                new BasicHttpContext()).checkedGet();

        System.out.println("Retrieved organization package grant: " + retrievedGrant);

        // Let's find all package grants issued to this person.
        Iterable<ServicePackageGrant> results = client.searchOrganizationGrants(organizationId, new BasicHttpContext())
                .checkedGet();

        System.out.println(Iterables.size(results) + " packages granted to organization " + organizationId);
    }

}
