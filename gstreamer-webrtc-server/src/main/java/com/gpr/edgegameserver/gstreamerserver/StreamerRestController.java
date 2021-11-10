package com.gpr.edgegameserver.gstreamerserver;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stream")
@CrossOrigin("*")
public class StreamerRestController {

    private final MediaPipelineFactory mediaPipelineFactory;

    public StreamerRestController(MediaPipelineFactory mediaPipelineFactory) {
        this.mediaPipelineFactory = mediaPipelineFactory;
    }

    @PostMapping("/{peerId}")
    public void startStream(@PathVariable String peerId) {
        mediaPipelineFactory.buildPipeline(peerId);
    }
}
