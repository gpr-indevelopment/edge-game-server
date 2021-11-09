package com.gpr.edgegameserver.web;

import com.gpr.edgegameserver.videostreamer.PortAllocator;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;

@Component
public class PortAllocatorImpl implements PortAllocator {
    @Override
    public Integer findAvailableUdpPort() {
        return SocketUtils.findAvailableUdpPort();
    }
}
