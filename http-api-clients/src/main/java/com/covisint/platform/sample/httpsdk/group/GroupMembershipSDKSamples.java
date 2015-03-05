/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.group;

import java.util.List;

import org.apache.http.protocol.BasicHttpContext;

import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.ResourceReference;
import com.covisint.core.http.service.core.SortCriteria;
import com.covisint.platform.group.client.group.membership.GroupMembershipClient;
import com.covisint.platform.group.client.group.membership.GroupMembershipSDK;
import com.covisint.platform.group.core.group.Group;
import com.covisint.platform.group.core.group.GroupMembership;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

public final class GroupMembershipSDKSamples {

    private static GroupMembershipClient createGroupMembershipClient() {
        return new GroupMembershipSDK(ServiceUrl.GROUP_V1.getValue()).create();
    }

    private static Group getGroup() {
        return null;
    }

    /** Demonstrate CRUD operations (and also search) on group membership resources. */
    public static void groupMembershipCRUD() {

        // First, get an instance of the group membership client.
        GroupMembershipClient client = createGroupMembershipClient();

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
        GroupMembership createdMembership = client.add(group.getId(), membership, new BasicHttpContext()).checkedGet();

        System.out.println("Created membership " + createdMembership.getId());

        // Let's fetch the membership we just created.
        GroupMembership retrievedMembership = client.get(group.getId(), createdMembership.getId(),
                new BasicHttpContext()).checkedGet();

        System.out.println("Retrieved membership: " + retrievedMembership);

        // Search for memberships that this member holds.
        Multimap<String, String> filter = ArrayListMultimap.<String, String> create();
        filter.put("member.id", member.getId());
        filter.put("member.type", member.getType());
        filter.put("member.realmId", member.getRealm());

        // Run the search.
        List<GroupMembership> results = client.search(filter, SortCriteria.NONE, Page.DEFAULT, new BasicHttpContext())
                .checkedGet();

        System.out.println("Found " + results.size() + " memberships for member " + member.getId());

        // Just list all memberships to a given group id.
        String groupId = "1ba3401ac2a";
        Iterable<GroupMembership> groupMemberships = client.listGroupMemberships(groupId, false, false,
                new BasicHttpContext()).checkedGet();

        System.out.println("Found " + Iterables.size(groupMemberships) + " memberships for group " + groupId);

        // Now delete the membership from the group.
        client.delete(group.getId(), createdMembership.getId(), new BasicHttpContext()).checkedGet();

        System.out.println("Successfully deleted membership.");
    }

}
