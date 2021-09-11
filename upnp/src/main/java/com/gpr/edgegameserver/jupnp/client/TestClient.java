package com.gpr.edgegameserver.jupnp.client;

import org.jupnp.DefaultUpnpServiceConfiguration;
import org.jupnp.UpnpService;
import org.jupnp.UpnpServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class TestClient {

    private UpnpService upnpService;

    public TestClient(TestRegsitryListener registryListener) {
        upnpService = new UpnpServiceImpl(new DefaultUpnpServiceConfiguration());
        upnpService.startup();
        upnpService.getRegistry().addListener(registryListener);
    }

    public void search() {
        upnpService.getControlPoint().search();
    }
}
