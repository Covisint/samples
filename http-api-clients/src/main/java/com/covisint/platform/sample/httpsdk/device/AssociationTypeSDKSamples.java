/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.device;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.covisint.core.http.service.core.Page;
import com.covisint.platform.device.client.sdk.AssociationTypeSDK;
import com.covisint.platform.device.client.sdk.AssociationTypeSDK.AssociationTypeClient;
import com.covisint.platform.device.client.sdk.AssociationTypeSDK.AssociationTypeClient.Sort;
import com.covisint.platform.device.client.sdk.AssociationTypeSDK.FilterSpec;
import com.covisint.platform.device.core.device.AssociationType;
import com.covisint.platform.device.core.device.DeviceType;
import com.covisint.platform.device.core.device.InternationalizedString;
import com.covisint.platform.sample.httpsdk.ServiceUrl;

public final class AssociationTypeSDKSamples {

    public static void main(String[] args) {
        associationTypeCRUD();
    }

    /** Creates the association type client. */
    private static AssociationTypeClient createAssociationTypeClient() {
        return new AssociationTypeSDK(ServiceUrl.DEVICE_V1.getValue()).newClient();
    }

    /**
     * Gets the device type.
     * 
     * @return The device type.
     */
    private static DeviceType getDeviceType() {
        // Get the required device type.
        return null;
    }

    /**
     * Printing association type.
     * 
     * @param associationType The {@link AssociationType}.
     */
    private static void printAssociationType(AssociationType associationType) {
        System.out.println("Association type : " + associationType);
        if (associationType.getDeviceType() != null) {
            System.out.println(String.format("Device type %s for the association type.",
                    associationType.getDeviceType()));
        }
    }

    /** Demonstrate CRUD operations (and also search) on association type resources. */
    public static void associationTypeCRUD() {

        final DeviceType deviceType = getDeviceType();

        // Get an instance of the association type client.
        final AssociationTypeClient associationTypeClient = createAssociationTypeClient();

        // Create an association type for the created device type.
        final AssociationType associationTypeResource = new AssociationType();

        // Set the association type name (internationalized).
        final Set<InternationalizedString> associationTypeName = new HashSet<InternationalizedString>();
        associationTypeName.add(new InternationalizedString("en", "Association type name"));
        associationTypeName.add(new InternationalizedString("de", "Verband Typ Name"));
        associationTypeResource.setName(associationTypeName);

        // Set the association type description (internationalized).
        final Set<InternationalizedString> associationTypeDescription = new HashSet<InternationalizedString>();
        associationTypeDescription.add(new InternationalizedString("en", "Association type description"));
        associationTypeDescription.add(new InternationalizedString("de", "Descripción tipo de asociación"));
        associationTypeResource.setDescription(associationTypeDescription);

        // Set the device type for the association type being created.
        associationTypeResource.setDeviceType(deviceType);

        // Create the association type.
        final AssociationType createdAssociationType = associationTypeClient.add(deviceType.getId(),
                associationTypeResource).checkedGet();

        System.out.println("Created association type: ");
        printAssociationType(createdAssociationType);

        // Retrieve the association type we just created, from the server.
        final AssociationType retrievedAssociationType = associationTypeClient.get(deviceType.getId(),
                createdAssociationType.getId(), true).checkedGet();

        System.out.println("Retrieved device type: ");
        printAssociationType(retrievedAssociationType);

        // Update our association type by providing another translation of the association type name and description.
        final Set<InternationalizedString> newName = new HashSet<>(retrievedAssociationType.getName());
        newName.add(new InternationalizedString("fr", "Nombre tipo de asociación"));
        retrievedAssociationType.setName(newName);

        final Set<InternationalizedString> newDesc = new HashSet<>(retrievedAssociationType.getDescription());
        newDesc.add(new InternationalizedString("fr", "Description type d'association"));
        retrievedAssociationType.setDescription(newDesc);

        // Persist the changes.
        final AssociationType updatedAssociationType = associationTypeClient.update(deviceType.getId(),
                retrievedAssociationType).checkedGet();

        System.out.println("Updated association type with new name/description, new resource version is "
                + updatedAssociationType.getVersion());
        printAssociationType(updatedAssociationType);

        // Now search for our association type based on the name and description.
        final String[] names = new String[1];
        names[0] = "Association type name";

        final String[] descriptions = new String[1];
        descriptions[0] = "Descripción tipo de asociación";

        final FilterSpec filterSpec = new FilterSpec();
        filterSpec.setNames(names);
        filterSpec.setDescriptions(descriptions);

        // Perform the association type search, sorting by creation time descending order.
        final List<AssociationType> associationTypes = associationTypeClient.search(deviceType.getId(), filterSpec,
                false, Page.ALL, Sort.CREATION_DESC).checkedGet();

        System.out.println("Association type search result size: " + associationTypes.size());

        for (final AssociationType associationType : associationTypes) {
            printAssociationType(associationType);
        }

        // Delete the association type created.
        associationTypeClient.delete(deviceType.getId(), createdAssociationType.getId()).checkedGet();

        System.out.println("Deleted associaton type: " + createdAssociationType.getId());

    }
}
