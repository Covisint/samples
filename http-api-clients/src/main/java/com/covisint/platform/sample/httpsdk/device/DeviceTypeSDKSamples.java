/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.device;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.covisint.core.http.service.core.Page;
import com.covisint.platform.device.client.sdk.DeviceTypeSDK;
import com.covisint.platform.device.client.sdk.DeviceTypeSDK.DeviceTypeClient;
import com.covisint.platform.device.client.sdk.DeviceTypeSDK.DeviceTypeClient.Sort;
import com.covisint.platform.device.client.sdk.DeviceTypeSDK.FilterSpec;
import com.covisint.platform.device.core.device.DeviceType;
import com.covisint.platform.device.core.device.DeviceTypeProperties;
import com.covisint.platform.device.core.device.InternationalizedString;
import com.covisint.platform.sample.httpsdk.ServiceUrl;

public final class DeviceTypeSDKSamples {

    public static void main(String[] args) {
        deviceTypeCRUD();
    }

    /** Creates the device type client. */
    private static DeviceTypeClient createDeviceTypeClient() {
        return new DeviceTypeSDK(ServiceUrl.DEVICE_V1.getValue()).newClient();
    }

    /**
     * Printing the device type.
     * 
     * @param deviceType The device type.
     */
    private static void printDeviceType(DeviceType deviceType) {
        System.out.println("Device type : " + deviceType);
        if (deviceType.getDeviceTypeProperties() != null) {
            System.out.println(String.format("Device type properties %s for the device type.",
                    deviceType.getDeviceTypeProperties()));
        }
    }

    /** Demonstrate CRUD operations (and also search) on device type resources. */
    public static void deviceTypeCRUD() {

        // Get an instance of the device type client.
        final DeviceTypeClient deviceTypeClient = createDeviceTypeClient();

        // Create a new device type.
        final DeviceType deviceTypeResource = new DeviceType();

        // Set the device type name (internationalized).
        final Set<InternationalizedString> name = new HashSet<InternationalizedString>();
        name.add(new InternationalizedString("en", "Device type creation"));
        name.add(new InternationalizedString("es", "Creación Tipo de equipo"));
        deviceTypeResource.setName(name);

        // Set the device type description (internationalized).
        final Set<InternationalizedString> description = new HashSet<InternationalizedString>();
        description.add(new InternationalizedString("en", "Device type description"));
        description.add(new InternationalizedString("es", "Descripción del tipo de dispositivo"));
        deviceTypeResource.setDescription(description);

        // Set the device type manufacturer.
        deviceTypeResource.setManufacturer("MANUFACTURER");

        // Set the device type model.
        deviceTypeResource.setModel("MODEL");

        // Set the device type properties (JSON string format).
        final DeviceTypeProperties properties = new DeviceTypeProperties();
        properties.setAdditionalProperties("{\"color\":\"blue\"}");
        deviceTypeResource.setDeviceTypeProperties(properties);

        // Create the device type.
        final DeviceType createdDeviceType = deviceTypeClient.add(deviceTypeResource).checkedGet();

        System.out.println("Created device type: ");
        printDeviceType(createdDeviceType);

        // Retrieve the device type we just created, from the server (with the properties included in response).
        final DeviceType retrievedDeviceType = deviceTypeClient.get(createdDeviceType.getId(), true).checkedGet();

        System.out.println("Retrieved device type: ");
        printDeviceType(retrievedDeviceType);

        // Update our device type by providing another translation of the device type name, description and model.
        final Set<InternationalizedString> newName = new HashSet<>(retrievedDeviceType.getName());
        newName.add(new InternationalizedString("it", "Creazione tipo di dispositivo"));
        retrievedDeviceType.setName(newName);

        final Set<InternationalizedString> newDesc = new HashSet<>(retrievedDeviceType.getDescription());
        newDesc.add(new InternationalizedString("it", "Descrizione del tipo di dispositivo"));
        retrievedDeviceType.setDescription(newDesc);

        retrievedDeviceType.setModel("MODEL CHANGED");

        // Persist the changes.
        final DeviceType updatedDeviceType = deviceTypeClient.update(retrievedDeviceType).checkedGet();

        System.out.println("Updated device type with new name/description and model, new resource version is "
                + updatedDeviceType.getVersion());

        System.out.println("Updated device type: ");
        printDeviceType(updatedDeviceType);

        // Now search for our device type based on the name.
        final String[] names = new String[1];
        names[0] = "Creazione tipo di dispositivo";

        final FilterSpec filterSpec = new FilterSpec();
        filterSpec.setNames(names);

        // Perform the device type search, sorting by creation time ascending order.
        final List<DeviceType> deviceTypes = deviceTypeClient.search(filterSpec, true, Page.DEFAULT, Sort.CREATION_ASC)
                .checkedGet();

        System.out.println("Device type search result size: " + deviceTypes.size());
        for (final DeviceType deviceType : deviceTypes) {
            printDeviceType(deviceType);
        }

        // Delete the device type created.
        deviceTypeClient.delete(createdDeviceType.getId()).checkedGet();

        System.out.println("Deleted device type: " + createdDeviceType.getId());
    }
}
