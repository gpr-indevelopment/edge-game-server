package com.gpr.gotegameserver;

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

    private final MediaPipelineFactory mediaPipelineFactory;

    private final VideoStatsService videoStatsService;

    public StreamerRestController(MediaPipelineFactory mediaPipelineFactory, VideoStatsService videoStatsService) {
        this.mediaPipelineFactory = mediaPipelineFactory;
        this.videoStatsService = videoStatsService;
    }

    @PostMapping("/{peerId}")
    public void startStream(@PathVariable String peerId) {
        mediaPipelineFactory.buildPipeline(peerId);
    }

    @PostMapping("/video-stats")
    public void registerVideoStats(@RequestBody Map<String, String> payload) {
        videoStatsService.registerStats(payload);
    }
}
