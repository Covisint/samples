/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.ResourceReference;
import com.covisint.platform.group.client.sdk.GroupSDK;
import com.covisint.platform.group.client.sdk.GroupSDK.GroupClient;
import com.covisint.platform.group.client.sdk.GroupSDK.GroupClient.Sort;
import com.covisint.platform.group.core.group.Group;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.google.common.collect.ArrayListMultimap;

public final class GroupSDKSamples {

    public static void main(String[] args) {
        groupCRUD();
    }

    private static GroupClient createGroupClient() {
        return new GroupSDK(ServiceUrl.GROUP_V1.getValue()).newClient();
    }

    /** Demonstrate CRUD operations (and also search) on group resources. */
    public static void groupCRUD() {

        // First, get an instance of the group client.
        GroupClient groupClient = createGroupClient();

        // Start by creating a new group.
        Group group = new Group();

        // Set the group name (internationalized).
        Map<String, String> name = new HashMap<>();
        name.put("en", "Sam's iTunes Media Library");
        name.put("es", "iTunes Media Library de Sam");
        group.setName(name);

        // Set the group description (internationalized).
        Map<String, String> description = new HashMap<>();
        description.put("en", "This is Sam's iTunes Media Library");
        description.put("es", "Esta iTunes Media Library de Sam");
        group.setDescription(description);

        // The id of the group's owner.
        String groupOwnerId = "10ce2aae53bc";

        // Set the owner.
        group.setOwner(new ResourceReference(groupOwnerId, "person"));

        // Create the group.
        Group createdGroup = groupClient.add(group).checkedGet();

        System.out.println("Created group " + createdGroup.getId());

        // Let's retrieve the group we just created, from the server.
        Group retrievedGroup = groupClient.get(createdGroup.getId(), true).checkedGet();

        System.out.println("Retrieved group: " + retrievedGroup);

        // Let's update our group by providing another translation of the group name and description.
        Map<String, String> newName = new HashMap<>(createdGroup.getName());
        newName.put("it", "ITunes Media Library di Sam");
        retrievedGroup.setName(newName);

        Map<String, String> newDesc = new HashMap<>(createdGroup.getDescription());
        newDesc.put("it", "Questo è iTunes Media Library di Sam");
        retrievedGroup.setDescription(newDesc);

        // Persist the changes.
        Group updatedGroup = groupClient.update(retrievedGroup).checkedGet();

        System.out.println("Updated group with new name/description, new resource version is "
                + updatedGroup.getVersion());

        // Now search for our groups based on the name.
        ArrayListMultimap<String, String> filter = ArrayListMultimap.<String, String> create();
        filter.put("name", "Questo è iTunes Media Library di Sam");

        // Perform the search, sorting by creation time descending order.
        List<Group> results = groupClient.search(new ArrayList<String>(), new ArrayList<String>(), true, Page.DEFAULT,
                Sort.CREATION_DESC).checkedGet();

        System.out.println("Group search result size: " + results.size());
    }

}
