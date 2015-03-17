/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.group;

import java.util.List;

import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.ResourceReference;
import com.covisint.platform.group.client.sdk.GroupMembershipSDK;
import com.covisint.platform.group.client.sdk.GroupMembershipSDK.GroupMembershipClient;
import com.covisint.platform.group.client.sdk.GroupMembershipSDK.GroupMembershipClient.ResponseOption;
import com.covisint.platform.group.core.group.Group;
import com.covisint.platform.group.core.group.GroupMembership;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.google.common.collect.Iterables;

public final class GroupMembershipSDKSamples {

    private static GroupMembershipClient createGroupMembershipClient() {
        return new GroupMembershipSDK(ServiceUrl.GROUP_V1.getValue()).newClient();
    }

    private static Group getGroup() {
        return null;
    }

    /** Demonstrate CRUD operations (and also search) on group membership resources. */
    public static void groupMembershipCRUD() {

        // First, get an instance of the group membership client.
        GroupMembershipClient groupMembershipClient = createGroupMembershipClient();

        // Get the group to whom membership will be modified.
        Group group = getGroup();

        // Create the "member" of the group.
        String memberId = "ab10d1c4e32";
        String memberType = "person";
        ResourceReference member = new ResourceReference(memberId, memberType);

        // Now build the membership object.
        GroupMembership membership = new GroupMembership();
        membership.setMember(member);
        membership.setGroup(group);

        // Persist the membership to the group.
        GroupMembership createdMembership = groupMembershipClient.add(group.getId(), membership).checkedGet();

        System.out.println("Created membership " + createdMembership.getId());

        // Let's fetch the membership we just created.
        GroupMembership retrievedMembership = groupMembershipClient.get(group.getId(), createdMembership.getId(), ResponseOption.NONE)
                .checkedGet();

        System.out.println("Retrieved membership: " + retrievedMembership);

        // Search for memberships that this member holds.
        List<GroupMembership> results = groupMembershipClient.search(member.getId(), member.getType(), null, null, Page.DEFAULT,
                ResponseOption.INCLUDE_GROUP).checkedGet();

        System.out.println("Found " + results.size() + " memberships for member " + member.getId());

        // Just list all memberships to a given group id.
        String groupId = "1ba3401ac2a";
        Iterable<GroupMembership> groupMemberships = groupMembershipClient.listGroupMemberships(groupId, ResponseOption.INCLUDE_GROUP)
                .checkedGet();

        System.out.println("Found " + Iterables.size(groupMemberships) + " memberships for group " + groupId);

        // Now delete the membership from the group.
        groupMembershipClient.delete(group.getId(), createdMembership.getId()).checkedGet();

        System.out.println("Successfully deleted membership.");
    }
}
