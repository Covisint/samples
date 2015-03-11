
package com.covisint.platform.sample.httpsdk.service;

import java.util.List;

import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.ResourceReference;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.service.client.sdk.ServicePackageRequestSDK;
import com.covisint.platform.service.client.sdk.ServicePackageRequestSDK.ServicePackageRequestClient;
import com.covisint.platform.service.client.sdk.ServicePackageRequestSDK.ServicePackageRequestClient.RequestorType;
import com.covisint.platform.service.core.service.request.ServicePackageRequest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ServicePackageRequestSDKSamples {

    private static ServicePackageRequestClient createServicePackageRequestClient() {
        return new ServicePackageRequestSDK(ServiceUrl.SERVICE_V1.getValue()).newClient();
    }

    /** CRUD on service package requests. */
    public static void servicePackageRequestCRUD() {

        // Get an instance of the client.
        ServicePackageRequestClient client = createServicePackageRequestClient();

        String personId = "cc1a00e3d2a";
        String packageId = "a00ac3de4d12";

        // Build the request object.
        ServicePackageRequest request = new ServicePackageRequest();

        // Specify the package requestor.
        request.setRequestor(new ResourceReference(personId, "person"));

        // Specify the requested package.
        request.setRequestedPackage(new ResourceReference(packageId, "servicePackage"));

        // Create the package request.
        ServicePackageRequest createdRequest = client.add(request).checkedGet();

        System.out.println("Created service package request " + createdRequest.getId());

        // Now let's retrieve the request resource we just created.
        ServicePackageRequest retrievedRequest = client.get(createdRequest.getId()).checkedGet();

        System.out.println("Retrieved request: " + retrievedRequest);

        // Search for all requests made by this person.
        Multimap<String, String> filter = ArrayListMultimap.<String, String> create();
        filter.put("requestor.id", personId);
        filter.put("requestor.type", "person");

        // Execute the search.
        List<ServicePackageRequest> results = client.search(personId, RequestorType.PERSON, Page.DEFAULT).checkedGet();

        System.out.println("Service package request search result size: " + results.size());

        // Finally, let's delete the package request.
        client.delete(retrievedRequest.getId()).checkedGet();
    }

}
