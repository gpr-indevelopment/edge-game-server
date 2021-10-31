package com.gpr.edgegameserver.web;

import com.gpr.edgegameserver.videostreamer.StreamStarterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    private final StreamStarterService streamStarterService;

    public WebController(StreamStarterService streamStarterService) {
        this.streamStarterService = streamStarterService;
    }

    @GetMapping
    public String test() {
        return streamStarterService.startStream();
    }
}
