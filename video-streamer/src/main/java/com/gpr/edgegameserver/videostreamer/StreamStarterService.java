package com.gpr.edgegameserver.videostreamer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.SocketUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class StreamStarterService {

    Logger logger = LoggerFactory.getLogger(StreamStarterService.class);

    private final KmsConnectionService kmsConnectionService;

    private final String sdpRootFolder;

    private final StreamRegistry streamRegistry;

    public StreamStarterService(KmsConnectionService kmsConnectionService, Optional<String> sdpRootFolderOpt, StreamRegistry streamRegistry) {
        this.kmsConnectionService = kmsConnectionService;
        this.sdpRootFolder = sdpRootFolderOpt.orElse("");
        this.streamRegistry = streamRegistry;
    }

    public String startStream() {
        return startStreamThread(retrieveKmsStreamingInfo());
    }

    private KmsStreamingInfo retrieveKmsStreamingInfo() {
        logger.info("Starting fake stream to get SDP info from ffmpeg.");
        Process ffmpegProcess = startFfmpegStream(getSdpFile(), SocketUtils.findAvailableUdpPort());
        KmsStreamingInfo kmsStreamingInfo = kmsConnectionService.connectKms(getSdpFile());
        ffmpegProcess.destroy();
        return kmsStreamingInfo;
    }

    private String startStreamThread(KmsStreamingInfo kmsStreamingInfo) {
        String streamId = UUID.randomUUID().toString();
        logger.info("Starting managed ffmpeg stream.");
        Process process = startFfmpegStream(getSdpFile(), kmsStreamingInfo.getVideoPort());
        streamRegistry.addStream(streamId, process);
        logger.info("Stream created successfully with identifier: {}.", streamId);
        return streamId;
    }

    private Process startFfmpegStream(File outputSdpFile, Integer videoStreamPort) {
        try {
            FfmpegStartCommand command = new FfmpegStartCommand(outputSdpFile, videoStreamPort);
            ProcessBuilder builder = new ProcessBuilder(command.getFullCommand());
            builder.inheritIO();
            logger.info("Starting ffmpeg process with output SDP file path: {}. Video stream RTP address: {}.", command.getSdpFilePath(), command.getRtpOutputAddress());
            Process process = builder.start();
            Thread.sleep(3000);
            return process;
        } catch (Exception e) {
            throw new FfmpegCommandException("Unable to start ffmpeg process", e);
        }
    }

    private File getSdpFile() {
        return new File(Paths.get(sdpRootFolder, "video-streamer.sdp").toString());
    }
}
