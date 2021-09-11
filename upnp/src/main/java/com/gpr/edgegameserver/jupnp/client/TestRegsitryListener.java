package com.gpr.edgegameserver.jupnp.client;

import org.jupnp.model.meta.RemoteDevice;
import org.jupnp.registry.DefaultRegistryListener;
import org.jupnp.registry.Registry;
import org.springframework.stereotype.Component;

@Component
public class TestRegsitryListener extends DefaultRegistryListener {

    @Override
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
        System.out.println("teste added");
    }

    @Override
    public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
        System.out.println("teste removed");
    }
}
