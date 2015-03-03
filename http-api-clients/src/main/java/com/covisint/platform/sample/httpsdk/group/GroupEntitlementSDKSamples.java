/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.group;

import java.util.List;

import org.apache.http.protocol.BasicHttpContext;

import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.SortCriteria;
import com.covisint.platform.group.client.group.entitlement.GroupEntitlementClient;
import com.covisint.platform.group.client.group.entitlement.GroupEntitlementSDK;
import com.covisint.platform.group.core.group.GroupEntitlement;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.google.common.collect.ArrayListMultimap;

public final class GroupEntitlementSDKSamples {

    private static GroupEntitlementClient createGroupEntitlementClient() {
        return new GroupEntitlementSDK(ServiceUrl.GROUP_V1.getValue()).create();
    }

    /** Demonstrate CRUD operations (and also search) on group entitlements. */
    public static void groupEntitlementCRUD() {

        // First, get an instance of the group entitlement client.
        GroupEntitlementClient client = createGroupEntitlementClient();

        // Create a new entitlement.
        GroupEntitlement entitlement = new GroupEntitlement().setName("share");

        // And add it to a group.
        String groupId = "510c1ae4fbc1";
        GroupEntitlement createdEntitlement = client.add(groupId, entitlement, new BasicHttpContext()).checkedGet();

        System.out.println("Created entitlement " + createdEntitlement.getId());

        // Fetch the entitlement we just persisted.
        GroupEntitlement retrieved = client.get(groupId, createdEntitlement.getId(), new BasicHttpContext())
                .checkedGet();

        System.out.println("Retrieved entitlement: " + retrieved);

        // Search all entitlements that exist in the group.
        List<GroupEntitlement> entitlements = client.search(ArrayListMultimap.<String, String> create(),
                SortCriteria.NONE, Page.DEFAULT, groupId, new BasicHttpContext()).checkedGet();

        System.out.println("Found " + entitlements.size() + " entitlements in group " + groupId);

        // Now delete the entitlement we created earlier.
        client.delete(groupId, retrieved.getId(), new BasicHttpContext()).checkedGet();

        System.out.println("Successfully deleted entitlement.");
    }
}
