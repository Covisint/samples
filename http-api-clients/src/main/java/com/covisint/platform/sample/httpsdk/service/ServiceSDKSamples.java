
package com.covisint.platform.sample.httpsdk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.BasicHttpContext;

import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.ResourceReference;
import com.covisint.core.http.service.core.SortCriteria;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.service.client.service.ServiceClient;
import com.covisint.platform.service.client.service.ServiceMembershipClient;
import com.covisint.platform.service.client.service.ServiceMembershipSDK;
import com.covisint.platform.service.client.service.ServicePackageClient;
import com.covisint.platform.service.client.service.ServicePackageSDK;
import com.covisint.platform.service.client.service.ServiceSDK;
import com.covisint.platform.service.core.service.Service;
import com.covisint.platform.service.core.service.ServicePackage;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

public class ServiceSDKSamples {

    private static ServiceClient createServiceClient() {
        return new ServiceSDK(ServiceUrl.SERVICE_V1.getValue()).create();
    }

    private static ServicePackageClient createServicePackageClient() {
        return new ServicePackageSDK(ServiceUrl.SERVICE_V1.getValue()).create();
    }

    private static ServiceMembershipClient createServiceMembershipClient() {
        return new ServiceMembershipSDK(ServiceUrl.SERVICE_V1.getValue()).create();
    }

    /** CRUD operations on service package resources. */
    public static void servicePackageCRUD() {

        // Get an instance of the service package client.
        ServicePackageClient client = createServicePackageClient();

        // Let's create a new service package.
        ServicePackage pkg = new ServicePackage();

        // Assign a category for the package.
        Map<String, String> category = new HashMap<>();
        category.put("en", "Administration");
        pkg.setCategories(category);

        // Now set the package name and description
        Map<String, String> name = new HashMap<>();
        name.put("en", "Utility Applications");
        name.put("fr", "d'applications utilitaires");
        pkg.setNames(name);

        Map<String, String> desc = new HashMap<>();
        desc.put("en", "Helpful utilities for daily work.");
        desc.put("fr", "Utilitaires utiles pour le travail quotidien.");
        pkg.setDescriptions(desc);

        // Packages must be owned by an organization. Set that here.
        String ownerOrganizationId = "aea612bcd32";
        pkg.setOwningOrganization(new ResourceReference(ownerOrganizationId, "organization"));

        // Now create the package.
        ServicePackage createdPackage = client.add(pkg, new BasicHttpContext()).checkedGet();

        System.out.println("Created service package " + createdPackage.getOwningOrganization());

        // Let's try and retrieve the package we just created, from the server.
        ServicePackage retrievedPackage = client.get(createdPackage.getId(), new BasicHttpContext()).checkedGet();

        System.out.println("Retrieved service package: " + retrievedPackage);

        // Now perform a search on service packages.
        Multimap<String, String> filter = ArrayListMultimap.<String, String> create();
        filter.put("name", "Utility Applications");
        filter.put("owningOrganization.id", ownerOrganizationId);

        List<ServicePackage> results = client.search(filter, SortCriteria.NONE, Page.DEFAULT, new BasicHttpContext())
                .checkedGet();

        System.out.println("Executed service package search and found " + results.size() + " results.");
    }

    /** CRUD operations on service resources. */
    public static void serviceCRUD() {

        // Get an instance of the service client.
        ServiceClient client = createServiceClient();

        // Start building the service resource.
        Service service = new Service();

        // Assign a category.
        Map<String, String> category = new HashMap<>();
        category.put("en", "Administration");
        service.setCategory(category);

        // Specify the service URL.
        service.setDefaultUrl("http://covisint.com/utilities/");

        // Set a name and description.
        service.setDefaultName("My Service");
        service.setDefaultDescription("This is the default service.");

        // Packages must be owned by an organization. Set that here.
        final String ownerOrganizationId = "aea612bcd32";
        service.setOwningOrganization(new ResourceReference(ownerOrganizationId, "organization"));

        // Now create the service.
        Service createdService = client.add(service, new BasicHttpContext()).checkedGet();

        System.out.println("Created service " + createdService.getId());

        // Let's retrieve the service we just created, from the server.
        Service retrievedService = client.get(createdService.getId(), new BasicHttpContext()).checkedGet();

        System.out.println("Retrieved service: " + retrievedService);

        // Search for the service by name.
        Multimap<String, String> filter = ArrayListMultimap.<String, String> create();
        filter.put("name", createdService.getDefaultName());

        List<Service> results = client.search(filter, SortCriteria.NONE, Page.DEFAULT, new BasicHttpContext())
                .checkedGet();

        System.out.println("Executed service search and found " + results.size() + " results.");
    }

    /** View, assign and remove service memberships from packages. */
    public static void serviceMemberships() {

        // Get an instance of the client.
        ServiceMembershipClient client = createServiceMembershipClient();

        // The package and service ids we will be assigning/removing.
        String packageId = "cee6db4a001";
        String serviceId = "653e4abce6f";

        // List all service members of the package.
        Iterable<Service> members = client.getMemberServices(packageId, new BasicHttpContext()).checkedGet();

        System.out.println("Found " + Iterables.size(members) + " member services of package " + packageId);

        // Add new service membership.
        client.addServiceMembership(packageId, serviceId, new BasicHttpContext()).checkedGet();

        System.out.println("Successfully added service " + serviceId + " as member of package " + packageId);

        // Remove that service membership.
        client.removeServiceMembership(packageId, serviceId, new BasicHttpContext()).checkedGet();

        System.out.println("Successfully removed service membership of " + serviceId + " from package " + packageId);
    }

}
