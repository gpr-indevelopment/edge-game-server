package com.gpr.edgegameserver.web;

import com.gpr.edgegameserver.videostreamer.StreamStartResponseDTO;
import com.gpr.edgegameserver.videostreamer.StreamStarterService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class WebController {

    private final StreamStarterService streamStarterService;

    public WebController(StreamStarterService streamStarterService) {
        this.streamStarterService = streamStarterService;
    }

    @PostMapping("/start-stream")
    public StreamStartResponseDTO startStream(@RequestBody String clientSdpOffer) {
        return streamStarterService.startStream(clientSdpOffer);
    }

    @PostMapping("/{id}/stop-stream")
    public void stopStream(@PathVariable String id) {
        streamStarterService.stopStream(id);
    }
}
