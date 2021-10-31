package com.gpr.edgegameserver.videostreamer;

import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.kurento.client.RtpEndpoint;
import org.kurento.client.internal.server.KurentoServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class KmsConnectionService {

    Logger logger = LoggerFactory.getLogger(KmsConnectionService.class);

    private final KurentoClient kurentoClient;

    private final SdpParserService sdpParserService;

    private final MediaPipeline mediaPipeline;

    private RtpEndpoint rtpEndpoint;

    public KmsConnectionService(KurentoClient kurentoClient, SdpParserService sdpParserService) {
        this.kurentoClient = kurentoClient;
        this.sdpParserService = sdpParserService;
        this.mediaPipeline = this.kurentoClient.createMediaPipeline();
        this.rtpEndpoint = new RtpEndpoint.Builder(this.mediaPipeline).build();
    }

    public KmsStreamingInfo connectKms(File inputSdp) {
        try {
            return connectKms(Files.readString(inputSdp.toPath(), StandardCharsets.US_ASCII));
        } catch (IOException e) {
            logger.error("IOException while reading SDP file when connecting to KMS. File path: {}.", inputSdp.getAbsolutePath());
            throw new UnreadableSdpException("Unable to read SDP file: " + inputSdp.getAbsolutePath(), e);
        } catch (Exception e) {
            logger.error("An unexpected error was thrown while reading SDP file when connecting to KMS. File path: {}.", inputSdp.getAbsolutePath());
            throw e;
        }
    }

    public KmsStreamingInfo connectKms(String sdpString) {
        return sdpParserService.getStreamingInfo(processRtpOffer(sdpString));
    }

    private String processRtpOffer(String offerSdpString) {
        try {
            return rtpEndpoint.processOffer(offerSdpString);
        } catch (KurentoServerException e) {
            if (e.getCode() == 40208) {
                logger.warn("KMS threw {} error: {}. Restarting RTP endpoint.", e.getCode(), e.getData());
                rtpEndpoint.release();
                this.rtpEndpoint = new RtpEndpoint.Builder(this.mediaPipeline).build();
                return rtpEndpoint.processOffer(offerSdpString);
            }
            throw e;
        }
    }
}
