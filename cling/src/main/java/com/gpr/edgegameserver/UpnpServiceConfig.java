package com.gpr.edgegameserver;

import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.model.meta.LocalDevice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpnpServiceConfig extends UpnpServiceImpl {

    private final List<LocalDevice> localDevices;

    public UpnpServiceConfig(List<LocalDevice> localDevices) {
        this.localDevices = localDevices;
        this.localDevices.forEach(device -> getRegistry().addDevice(device));
    }
}
