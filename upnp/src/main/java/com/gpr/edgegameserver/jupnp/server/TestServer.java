package com.gpr.edgegameserver.jupnp.server;

import org.jupnp.UpnpService;
import org.jupnp.UpnpServiceImpl;
import org.jupnp.model.ValidationException;
import org.jupnp.model.meta.DeviceIdentity;
import org.jupnp.model.meta.LocalDevice;
import org.jupnp.model.types.UDN;
import org.springframework.stereotype.Component;

@Component
public class TestServer {

    private final UpnpService upnpService;

    public TestServer() throws ValidationException {
        upnpService = new UpnpServiceImpl();
        Runtime.getRuntime().addShutdownHook(new Thread(upnpService::shutdown));
        upnpService.getRegistry().addDevice(createDevice());
    }

    private LocalDevice createDevice() throws ValidationException {
        DeviceIdentity deviceIdentity = new DeviceIdentity(new UDN("Edge game server"));
        return new LocalDevice(deviceIdentity);
    }
}

