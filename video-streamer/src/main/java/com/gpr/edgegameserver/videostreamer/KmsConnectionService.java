package com.gpr.edgegameserver.videostreamer;

import org.kurento.client.EventListener;
import org.kurento.client.IceCandidate;
import org.kurento.client.IceCandidateFoundEvent;
import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.kurento.client.RtpEndpoint;
import org.kurento.client.WebRtcEndpoint;
import org.kurento.client.internal.server.KurentoServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class KmsConnectionService {

    Logger logger = LoggerFactory.getLogger(KmsConnectionService.class);

    private final KurentoClient kurentoClient;

    private final SdpParserService sdpParserService;

    private final MediaPipeline mediaPipeline;

    private final EventListener<IceCandidateFoundEvent> iceCandidateListener;

    private RtpEndpoint rtpEndpoint;

    private WebRtcEndpoint webRtcEndpoint;

    public KmsConnectionService(KurentoClient kurentoClient, SdpParserService sdpParserService, EventListener<IceCandidateFoundEvent> iceCandidateListener) {
        this.kurentoClient = kurentoClient;
        this.sdpParserService = sdpParserService;
        this.mediaPipeline = this.kurentoClient.createMediaPipeline();
        this.iceCandidateListener = iceCandidateListener;
        this.restartPipeline();
    }

    public KmsStreamingInfo connectRtp(File inputSdp) {
        try {
            return connectRtp(Files.readString(inputSdp.toPath(), StandardCharsets.US_ASCII));
        } catch (IOException e) {
            logger.error("IOException while reading SDP file when connecting to KMS. File path: {}.", inputSdp.getAbsolutePath());
            throw new UnreadableSdpException("Unable to read SDP file: " + inputSdp.getAbsolutePath(), e);
        } catch (Exception e) {
            logger.error("An unexpected error was thrown while reading SDP file when connecting to KMS. File path: {}.", inputSdp.getAbsolutePath());
            throw e;
        }
    }

    public KmsStreamingInfo connectRtp(String sdpString) {
        return sdpParserService.getStreamingInfo(processRtpOffer(sdpString));
    }

    public KmsStreamingInfo connectWebRtc(String clientSdp) {
        KmsStreamingInfo kmsStreamingInfo = sdpParserService.getStreamingInfo(processWebRtcOffer(clientSdp));
        this.webRtcEndpoint.gatherCandidates();
        return kmsStreamingInfo;
    }

    public void addWebRtcIceCandidate(IceCandidate iceCandidate) {
        logger.info("Adding IceCandidate: {}.", iceCandidate.getCandidate());
        this.webRtcEndpoint.addIceCandidate(iceCandidate);
    }

    private String processWebRtcOffer(String clientSdp) {
        return webRtcEndpoint.processOffer(clientSdp);
    }

    private String processRtpOffer(String offerSdpString) {
        try {
            return rtpEndpoint.processOffer(offerSdpString);
        } catch (KurentoServerException e) {
            if (e.getCode() == 40208) {
                logger.warn("KMS threw {} error: {}. Restarting RTP endpoint.", e.getCode(), e.getData());
                restartPipeline();
                return rtpEndpoint.processOffer(offerSdpString);
            }
            throw e;
        }
    }

    private void restartPipeline() {
        if (this.rtpEndpoint != null) {
            this.rtpEndpoint.release();
        }
        if (this.webRtcEndpoint != null) {
            this.webRtcEndpoint.release();
        }
        this.rtpEndpoint = new RtpEndpoint.Builder(this.mediaPipeline).build();
        this.webRtcEndpoint = new WebRtcEndpoint.Builder(this.mediaPipeline).build();
        this.rtpEndpoint.connect(webRtcEndpoint);
        this.webRtcEndpoint.addIceCandidateFoundListener(iceCandidateListener);
        this.webRtcEndpoint.gatherCandidates();
    }
}
