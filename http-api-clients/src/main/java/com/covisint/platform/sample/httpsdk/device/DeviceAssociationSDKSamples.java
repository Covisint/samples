/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.device;

import java.util.List;

import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.ResourceReference;
import com.covisint.platform.device.client.sdk.DeviceAssociationSDK;
import com.covisint.platform.device.client.sdk.DeviceAssociationSDK.DeviceAssociationClient;
import com.covisint.platform.device.client.sdk.DeviceAssociationSDK.DeviceAssociationClient.Sort;
import com.covisint.platform.device.client.sdk.DeviceAssociationSDK.FilterSpec;
import com.covisint.platform.device.core.device.AssociationType;
import com.covisint.platform.device.core.device.Device;
import com.covisint.platform.device.core.device.DeviceAssociation;
import com.covisint.platform.sample.httpsdk.ServiceUrl;

public final class DeviceAssociationSDKSamples {

    public static void main(String[] args) {
        deviceAssociationCRUD();
    }

    /** Creates the device association client. */
    private static DeviceAssociationClient createDeviceAssociationClient() {
        return new DeviceAssociationSDK(ServiceUrl.DEVICE_V1.getValue()).newClient();
    }

    /**
     * Gets the device.
     * 
     * @return The device.
     */
    private static Device getDevice() {
        // Get the required device.
        return null;
    }

    /**
     * Gets the association type.
     * 
     * @return The association type.
     */
    private static AssociationType getAssociationType() {
        // Get the required association type.
        return null;
    }

    /**
     * Printing device association.
     * 
     * @param deviceAssociation The device association}.
     */
    private static void printDeviceAssociation(DeviceAssociation deviceAssociation) {
        System.out.println("Device association : " + deviceAssociation);
        System.out.println(String.format("Device %s for the device association.", deviceAssociation.getDevice()));
        System.out.println(String.format("Association type %s for the device association.",
                deviceAssociation.getAssociationType()));

    }

    /** Demonstrate CRUD operations (and also search) on device association resources. */
    public static void deviceAssociationCRUD() {

        final Device device = getDevice();

        // Get an instance of device association client.
        final DeviceAssociationClient deviceAssociationClient = createDeviceAssociationClient();

        // Create a device association.
        final DeviceAssociation deviceAssociationResource = new DeviceAssociation();

        // Set the device and association type for the device association.
        deviceAssociationResource.setDevice(device).setAssociationType(getAssociationType());

        // Set the subject of the device association.
        String subjectId = "SUBJECT" + Math.random();
        final String subjectType = "person";
        ResourceReference subject = new ResourceReference(subjectId, subjectType);
        deviceAssociationResource.setSubject(subject);

        // Create the device association.
        final DeviceAssociation createdDeviceAssociation = deviceAssociationClient.add(deviceAssociationResource)
                .checkedGet();

        System.out.println("Created device association: ");
        printDeviceAssociation(createdDeviceAssociation);

        // Retrieve the device association we just created, from the server.
        final DeviceAssociation retrievedDeviceAssociation = deviceAssociationClient.get(
                createdDeviceAssociation.getId()).checkedGet();

        System.out.println("Retrieved device association: ");
        printDeviceAssociation(retrievedDeviceAssociation);

        // Update the subject of device association.
        subjectId = "PQRS";
        subject = new ResourceReference(subjectId, subjectType);
        retrievedDeviceAssociation.setSubject(subject);

        // Persist the changes.
        final DeviceAssociation updatedDeviceAssociation = deviceAssociationClient.update(retrievedDeviceAssociation)
                .checkedGet();

        System.out.println("Updated subject of device association, new resource version is "
                + updatedDeviceAssociation.getVersion());
        printDeviceAssociation(updatedDeviceAssociation);

        // Search for the device association based on device owner, deviceUUid and subject of the device association.
        final FilterSpec filterSpec = new FilterSpec();
        filterSpec.setDeviceOwnerId(device.getOwner().getId());
        filterSpec.setDeviceUuid(device.getDeviceUuid());
        filterSpec.setSubjectId(subject.getId());

        // Perform the device association search, sorting by creation time ascending order.
        final List<DeviceAssociation> deviceAssociations = deviceAssociationClient.search(filterSpec, Page.ALL,
                Sort.CREATION_ASC).checkedGet();

        System.out.println("Device association search result size: " + deviceAssociations.size());
        for (final DeviceAssociation deviceAssociation : deviceAssociations) {
            printDeviceAssociation(deviceAssociation);
        }

        // Delete the device association created.
        deviceAssociationClient.delete(createdDeviceAssociation.getId()).checkedGet();

        System.out.println("Deleted device associaton: " + createdDeviceAssociation.getId());
    }
}
