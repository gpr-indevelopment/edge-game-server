package com.gpr.edgegameserver;

import com.gpr.edgegameserver.common.LocalServiceWrapper;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpnpServerDevice extends LocalDevice {

    private final List<LocalServiceWrapper> serviceWrappers;

    private static final DeviceIdentity DEVICE_IDENTITY = new DeviceIdentity(
            UDN.uniqueSystemIdentifier("Demo Binary Light")
    );

    private static final DeviceType DEVICE_TYPE = new UDADeviceType(
            "BinaryLight",
            1
    );

    private static final DeviceDetails DEVICE_DETAILS = new DeviceDetails(
            "Teste Conexão",
            new ManufacturerDetails("ACME"),
            new ModelDetails(
                    "BinLight2000",
                    "A demo light with on/off switch.",
                    "v1"
            )
    );

    public UpnpServerDevice(List<LocalServiceWrapper> serviceWrappers) throws ValidationException {
        super(DEVICE_IDENTITY,
                DEVICE_TYPE,
                DEVICE_DETAILS,
                (Icon) null,
                serviceWrappers.stream().map(LocalServiceWrapper::getService).toArray(LocalService[]::new));
        this.serviceWrappers = serviceWrappers;
    }
}
