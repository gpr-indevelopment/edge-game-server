package com.gpr.edgegameserver.gstreamerserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/stream")
@CrossOrigin("*")
public class StreamerRestController {

    Logger logger = LoggerFactory.getLogger(StreamerRestController.class);

    private final MediaPipelineFactory mediaPipelineFactory;

    private final InputLagRepository inputLagRepository;

    public StreamerRestController(MediaPipelineFactory mediaPipelineFactory, InputLagRepository inputLagRepository) {
        this.mediaPipelineFactory = mediaPipelineFactory;
        this.inputLagRepository = inputLagRepository;
    }

    @PostMapping("/{peerId}")
    public void startStream(@PathVariable String peerId) {
        mediaPipelineFactory.buildPipeline(peerId);
    }

    @PostMapping("/input-lag")
    public void registerInputLag(@RequestBody Map<String, Object> payload) {
        InputLagEntity savedEntity = inputLagRepository.save(new InputLagEntity((Long) payload.get("sentTimestamp"), System.currentTimeMillis()));
        logger.info("Registered InputLag with delta: {} ms", savedEntity.getDelta());
    }
}
