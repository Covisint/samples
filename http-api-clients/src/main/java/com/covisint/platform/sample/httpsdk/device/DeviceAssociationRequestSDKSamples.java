/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.device;

import java.util.List;

import com.covisint.core.http.service.core.Page;
import com.covisint.platform.device.client.sdk.DeviceAssociationRequestSDK;
import com.covisint.platform.device.client.sdk.DeviceAssociationRequestSDK.DeviceAssociationRequestClient;
import com.covisint.platform.device.client.sdk.DeviceAssociationRequestSDK.DeviceAssociationRequestClient.Sort;
import com.covisint.platform.device.client.sdk.DeviceAssociationRequestSDK.FilterSpec;
import com.covisint.platform.device.core.device.AssociationType;
import com.covisint.platform.device.core.device.Device;
import com.covisint.platform.device.core.device.DeviceAssociationRequest;
import com.covisint.platform.device.core.device.RequestStatus;
import com.covisint.platform.sample.httpsdk.ServiceUrl;

public class DeviceAssociationRequestSDKSamples {

    public static void main(String[] args) {
        deviceAssociationRequestCRUD();
        acceptDeviceAssociationRequest();
        approveDeviceAssociationRequest();
        rejectDeviceAssociationRequest();
    }

    /** Creates the device association request client. */
    private static DeviceAssociationRequestClient createDeviceAssociationRequestClient() {
        return new DeviceAssociationRequestSDK(ServiceUrl.DEVICE_V1.getValue()).newClient();
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
     * Printing device association request.
     * 
     * @param deviceAssociationRequest The device association request.
     */
    private static void printDeviceAssociationRequest(DeviceAssociationRequest deviceAssociationRequest) {
        System.out.println("Device association request : " + deviceAssociationRequest);
        System.out.println(String.format("Device %s for the device association request.",
                deviceAssociationRequest.getDevice()));
        System.out.println(String.format("Association type %s for the device association request.",
                deviceAssociationRequest.getAssociationType()));
    }

    /** Demonstrate CRUD operations (and also search) on device association request resources. */
    public static void deviceAssociationRequestCRUD() {
        final Device device = getDevice();

        // Get an instance of device association request client.
        final DeviceAssociationRequestClient deviceAssociationRequestClient = createDeviceAssociationRequestClient();

        // Create a device association request.
        final DeviceAssociationRequest deviceAssociationRequestResource = new DeviceAssociationRequest();

        // Set the device and association type for the device association request.
        deviceAssociationRequestResource.setDevice(device).setAssociationType(getAssociationType());

        // Create the device association request.
        final DeviceAssociationRequest createdDeviceAssociationRequest = deviceAssociationRequestClient.add(
                deviceAssociationRequestResource).checkedGet();

        System.out.println("Created device association request: ");
        printDeviceAssociationRequest(createdDeviceAssociationRequest);

        // Retrieve the device association request we just created, from the server.
        final DeviceAssociationRequest retrievedDeviceAssociationRequest = deviceAssociationRequestClient.get(
                createdDeviceAssociationRequest.getId()).checkedGet();

        System.out.println("Retrieved device association request: ");
        printDeviceAssociationRequest(retrievedDeviceAssociationRequest);

        // Search for the device association request based on device id and status of the request.
        final FilterSpec filterSpec = new FilterSpec();
        filterSpec.setDeviceId(device.getId());
        filterSpec.setStatus(RequestStatus.OPEN.toString());

        // Perform the device association search, sorting by creation time ascending order.
        final List<DeviceAssociationRequest> deviceAssociationRequests = deviceAssociationRequestClient.search(
                filterSpec, Page.ALL, Sort.CREATION_ASC).checkedGet();

        System.out.println("Device association request search result size: " + deviceAssociationRequests.size());

        for (final DeviceAssociationRequest deviceAssociationRequest : deviceAssociationRequests) {
            printDeviceAssociationRequest(deviceAssociationRequest);
        }

        // Delete the device association request created.
        deviceAssociationRequestClient.delete(createdDeviceAssociationRequest.getId()).checkedGet();

        System.out.println("Deleted device associaton request: " + createdDeviceAssociationRequest.getId());

    }

    /** Demonstrates accept device association request. */
    public static void acceptDeviceAssociationRequest() {
        // Get an instance of device association request client.
        final DeviceAssociationRequestClient deviceAssociationRequestClient = createDeviceAssociationRequestClient();

        // The invitee accept the request.
        final String inviteeId = "INVITEE" + Math.random();
        deviceAssociationRequestClient.accept("hwdflhwa123321", inviteeId, "person").checkedGet();

        System.out.println("Accepted device association request.");
    }

    /** Demonstrates approve device association request. */
    public static void approveDeviceAssociationRequest() {
        // Get an instance of device association request client.
        final DeviceAssociationRequestClient deviceAssociationRequestClient = createDeviceAssociationRequestClient();

        // The owner approve the request.
        final String ownerId = "OWNER" + Math.random();
        deviceAssociationRequestClient.approve("sadfsadjhfsadhk232", ownerId).checkedGet();

        System.out.println("Approved device association request.");
    }

    /** Demonstrates reject device association request. */
    public static void rejectDeviceAssociationRequest() {
        // Get an instance of device association request client.
        final DeviceAssociationRequestClient deviceAssociationRequestClient = createDeviceAssociationRequestClient();

        // The owner reject the request.
        final String ownerId = "OWNER" + Math.random();
        deviceAssociationRequestClient.reject("sadfsadjhfsadhk232", ownerId).checkedGet();

        System.out.println("Rejected device association request.");
    }
}
