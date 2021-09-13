package com.gpr.edgegameserver.switchpower;

import com.gpr.edgegameserver.common.LocalServiceWrapper;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.meta.LocalService;
import org.springframework.stereotype.Service;

@Service
public class SwitchPowerService implements LocalServiceWrapper {

    private static final LocalService<SwitchPower> service = new AnnotationLocalServiceBinder().read(SwitchPower.class);

    public SwitchPowerService() {
        service.setManager(new DefaultServiceManager(service, SwitchPower.class));
    }

    public LocalService<SwitchPower> getService() {
        return service;
    }
}
