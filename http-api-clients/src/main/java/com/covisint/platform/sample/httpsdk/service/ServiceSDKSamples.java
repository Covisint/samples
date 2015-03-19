
package com.covisint.platform.sample.httpsdk.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.covisint.core.http.service.core.Page;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.service.client.sdk.ServiceMembershipSDK;
import com.covisint.platform.service.client.sdk.ServiceMembershipSDK.ServiceMembershipClient;
import com.covisint.platform.service.client.sdk.ServicePackageSDK;
import com.covisint.platform.service.client.sdk.ServicePackageSDK.ServicePackageClient;
import com.covisint.platform.service.client.sdk.ServicePackageSDK.ServicePackageClient.FilterSpec;
import com.covisint.platform.service.client.sdk.ServiceSDK;
import com.covisint.platform.service.client.sdk.ServiceSDK.ServiceClient;
import com.covisint.platform.service.core.service.Service;
import com.covisint.platform.service.core.service.ServicePackage;
import com.google.common.collect.Iterables;

public class ServiceSDKSamples {

    private static ServiceClient createServiceClient() {
        return new ServiceSDK(ServiceUrl.SERVICE_V1.getValue()).newClient();
    }

    private static ServicePackageClient createServicePackageClient() {
        return new ServicePackageSDK(ServiceUrl.SERVICE_V1.getValue()).newClient();
    }

    private static ServiceMembershipClient createServiceMembershipClient() {
        return new ServiceMembershipSDK(ServiceUrl.SERVICE_V1.getValue()).newClient();
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
        pkg.setOwningOrganizationId(ownerOrganizationId);

        // Now create the package.
        ServicePackage createdPackage = client.add(pkg).checkedGet();

        System.out.println("Created service package " + createdPackage.getOwningOrganization());

        // Let's try and retrieve the package we just created, from the server.
        ServicePackage retrievedPackage = client.get(createdPackage.getId()).checkedGet();

        System.out.println("Retrieved service package: " + retrievedPackage);

        // Now perform a search on service packages.
        FilterSpec filterSpec = new FilterSpec().setNames("Utility Applications").setOwningOrganizationId(
                ownerOrganizationId);

        List<ServicePackage> results = client.search(filterSpec, Page.DEFAULT).checkedGet();

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
        service.setOwningOrganizationId(ownerOrganizationId);

        // Now create the service.
        Service createdService = client.add(service).checkedGet();

        System.out.println("Created service " + createdService.getId());

        // Let's retrieve the service we just created, from the server.
        Service retrievedService = client.get(createdService.getId()).checkedGet();

        System.out.println("Retrieved service: " + retrievedService);

        // Search for the service by name.
        com.covisint.platform.service.client.sdk.ServiceSDK.ServiceClient.FilterSpec filter = new com.covisint.platform.service.client.sdk.ServiceSDK.ServiceClient.FilterSpec()
                .setNames(service.getDefaultName());

        List<Service> results = client.search(filter, Page.DEFAULT).checkedGet();

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
        Iterable<Service> members = client.listMemberServices(packageId).checkedGet();

        System.out.println("Found " + Iterables.size(members) + " member services of package " + packageId);

        // Add new service membership.
        client.assignServiceMemberships(packageId, Arrays.asList(serviceId)).checkedGet();

        System.out.println("Successfully added service " + serviceId + " as member of package " + packageId);

        // Remove that service membership.
        client.removeServiceMemberships(packageId, Arrays.asList(serviceId)).checkedGet();

        System.out.println("Successfully removed service membership of " + serviceId + " from package " + packageId);
    }

}
