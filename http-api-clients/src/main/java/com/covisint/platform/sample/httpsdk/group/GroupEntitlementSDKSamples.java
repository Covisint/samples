/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.group;

import java.util.List;

import com.covisint.core.http.service.core.Page;
import com.covisint.platform.group.client.sdk.GroupEntitlementSDK;
import com.covisint.platform.group.client.sdk.GroupEntitlementSDK.GroupEntitlementClient;
import com.covisint.platform.group.core.group.GroupEntitlement;
import com.covisint.platform.sample.httpsdk.ServiceUrl;

public final class GroupEntitlementSDKSamples {

    private static GroupEntitlementClient createGroupEntitlementClient() {
        return new GroupEntitlementSDK(ServiceUrl.GROUP_V1.getValue()).newClient();
    }

    /** Demonstrate CRUD operations (and also search) on group entitlements. */
    public static void groupEntitlementCRUD() {

        // First, get an instance of the group entitlement client.
        GroupEntitlementClient groupEntitlementClient = createGroupEntitlementClient();

        // Create a new entitlement.
        GroupEntitlement entitlement = new GroupEntitlement().setName("share");

        // And add it to a group.
        String groupId = "510c1ae4fbc1";
        GroupEntitlement createdEntitlement = groupEntitlementClient.add(groupId, entitlement).checkedGet();

        System.out.println("Created entitlement " + createdEntitlement.getId());

        // Fetch the entitlement we just persisted.
        GroupEntitlement retrieved = groupEntitlementClient.get(groupId, createdEntitlement.getId()).checkedGet();

        System.out.println("Retrieved entitlement: " + retrieved);

        // Search all entitlements that exist in the group.
        List<GroupEntitlement> entitlements = groupEntitlementClient.listEntitlements(groupId, Page.DEFAULT).checkedGet();

        System.out.println("Found " + entitlements.size() + " entitlements in group " + groupId);

        // Now delete the entitlement we created earlier.
        groupEntitlementClient.delete(groupId, retrieved.getId()).checkedGet();

        System.out.println("Successfully deleted entitlement.");
    }
}
