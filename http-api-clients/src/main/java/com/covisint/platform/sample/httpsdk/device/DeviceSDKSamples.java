/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.device;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.ResourceReference;
import com.covisint.platform.device.client.sdk.DeviceSDK;
import com.covisint.platform.device.client.sdk.DeviceSDK.DeviceClient;
import com.covisint.platform.device.client.sdk.DeviceSDK.DeviceClient.Sort;
import com.covisint.platform.device.client.sdk.DeviceSDK.FilterSpec;
import com.covisint.platform.device.core.device.Device;
import com.covisint.platform.device.core.device.DeviceProperties;
import com.covisint.platform.device.core.device.DeviceType;
import com.covisint.platform.device.core.device.InternationalizedString;
import com.covisint.platform.sample.httpsdk.ServiceUrl;

public final class DeviceSDKSamples {

    public static void main(String[] args) {
        deviceCRUD();
        verifyClaim();
        claimDevice();
    }

    /** Creates the device client. */
    private static DeviceClient createDeviceClient() {
        return new DeviceSDK(ServiceUrl.DEVICE_V1.getValue()).newClient();
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
     * Printing the device.
     * 
     * @param device The device.
     */
    private static void printDevice(Device device) {
        System.out.println("Device : " + device);

        if (device.getDeviceProperties() != null) {
            System.out.println(String.format("Device properties %s for the device.", device.getDeviceProperties()));
        }

        if (device.getDeviceType() != null) {
            System.out.println(String.format("Device type %s for the device.", device.getDeviceType()));
        }
    }

    /** Demonstrate CRUD operations (and also search) on device resources. */
    public static void deviceCRUD() {

        // Get an instance of the device client.
        final DeviceClient deviceClient = createDeviceClient();

        // Create a device for the created device type.
        final Device deviceResource = new Device();

        // Set the device name (internationalized).
        final Set<InternationalizedString> deviceName = new HashSet<InternationalizedString>();
        deviceName.add(new InternationalizedString("en", "Device name"));
        deviceName.add(new InternationalizedString("id", "Nama Perangkat"));
        deviceResource.setName(deviceName);

        // Set the device description (internationalized).
        final Set<InternationalizedString> deviceDescription = new HashSet<InternationalizedString>();
        deviceDescription.add(new InternationalizedString("en", "Device description"));
        deviceDescription.add(new InternationalizedString("id", "Deskripsi Perangkat"));
        deviceResource.setDescription(deviceDescription);

        // Set the device type for the device being created.
        deviceResource.setDeviceType(getDeviceType());

        // Set the deviceUuid.
        final String deviceUuid = "sample-device-uuid" + Math.random();
        deviceResource.setDeviceUuid(deviceUuid);

        // Set the device owner.
        final String ownerId = "OWNER" + Math.random();
        final String ownerType = "person";
        final ResourceReference deviceOwner = new ResourceReference(ownerId, ownerType);
        deviceResource.setOwner(deviceOwner);

        // Set the device properties (JSON string format).
        final DeviceProperties properties = new DeviceProperties();
        properties.setAdditionalProperties("{\"color\":\"white\"}");
        deviceResource.setDeviceProperties(properties);

        // Create the device.
        final Device createdDevice = deviceClient.add(deviceResource).checkedGet();

        System.out.println("Created device: ");
        printDevice(createdDevice);

        // Retrieve the device we just created, from the server (with the properties and device type included in
        // response).
        final Device retrievedDevice = deviceClient.get(createdDevice.getId(), true, true).checkedGet();

        System.out.println("Retrieved device: ");
        printDevice(retrievedDevice);

        // Update our device by providing additional properties.
        final DeviceProperties updatedDeviceProperties = new DeviceProperties();
        updatedDeviceProperties.setAdditionalProperties("{\"color\":\"white\", \"weight\":\"100gm\"}");
        retrievedDevice.setDeviceProperties(updatedDeviceProperties);

        // Persist the changes.
        final Device updatedDevice = deviceClient.update(retrievedDevice).checkedGet();

        System.out.println("Updated device with properties, new resource version is " + updatedDevice.getVersion());
        System.out.println("Updated device: ");
        printDevice(updatedDevice);

        // Now search for our device based on the description and device owner.
        final String[] descriptions = new String[1];
        descriptions[0] = "Deskripsi Perangkat";

        final FilterSpec filterSpec = new FilterSpec();
        filterSpec.setDescriptions(descriptions);
        filterSpec.setOwnerId("OWNER" + Math.random());

        // Perform the device search, sorting by creation time descending order.
        final List<Device> devices = deviceClient.search(filterSpec, false, false, Page.DEFAULT, Sort.CREATION_DESC)
                .checkedGet();

        System.out.println("Device search result size: " + devices.size());
        for (final Device device : devices) {
            printDevice(device);
        }

        // Delete the device created.
        deviceClient.delete(createdDevice.getId()).checkedGet();

        System.out.println("Deleted device: " + createdDevice.getId());

    }

    /** Demonstrates claim verification. */
    public static void verifyClaim() {
        // Get an instance of the device client.
        final DeviceClient deviceClient = createDeviceClient();

        // Verify the claim.
        deviceClient.verifyClaim("hdjsanfjsdhfj643", "ndfsjkadnfjd1231209830291").checkedGet();

        System.out.println("Device claim verified");
    }

    /** Demonstrates device claim. */
    public static void claimDevice() {
        // Get an instance of the device client.
        final DeviceClient deviceClient = createDeviceClient();

        // Now, change the owner of the device i.e. claim the device.
        final String claimOwner = "OWNER" + Math.random();
        deviceClient.claimDevice("hasdfjadkwsdhf3123", claimOwner, "application").checkedGet();

        System.out.println("Device claimed for owner: " + claimOwner);
    }

}
