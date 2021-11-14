package com.gpr.edgegameserver.gstreamerserver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.elements.WebRTCBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebRtcCallbackManager {

    Logger logger = LoggerFactory.getLogger(WebRtcCallbackManager.class);

    private WebRTCBin webRTCBin;

    private WebSocketSession webSocketSession;

    private Pipeline pipeline;

    private final ObjectMapper mapper = new ObjectMapper();

    public WebRtcCallbackManager(WebRTCBin webRTCBin, WebSocketSession webSocketSession, Pipeline pipeline) {
        this.webRTCBin = webRTCBin;
        this.webSocketSession = webSocketSession;
        this.pipeline = pipeline;
        setupWebRtc();
    }

    private void setupWebRtc() {
        this.webRTCBin.connect(onNegotiationNeeded);
        this.webRTCBin.connect(onIceCandidate);
    }

    private WebRTCBin.ON_ICE_CANDIDATE onIceCandidate = (sdpMLineIndex, candidate) -> {
        JsonNode rootNode = mapper.createObjectNode();
        JsonNode iceNode = mapper.createObjectNode();
        ((ObjectNode) iceNode).put("candidate", candidate);
        ((ObjectNode) iceNode).put("sdpMLineIndex", sdpMLineIndex);
        ((ObjectNode) rootNode).set("ice", iceNode);

        try {
            String json = mapper.writeValueAsString(rootNode);
            logger.info("ON_ICE_CANDIDATE: " + json);
            this.webSocketSession.sendMessage(new TextMessage(json));
        } catch (IOException e) {
            logger.error("Couldn't write JSON", e);
        }
    };

    private WebRTCBin.CREATE_OFFER onOfferCreated = offer -> {
        webRTCBin.setLocalDescription(offer);
        try {
            JsonNode rootNode = mapper.createObjectNode();
            JsonNode sdpNode = mapper.createObjectNode();
            ((ObjectNode) sdpNode).put("type", "offer");
            ((ObjectNode) sdpNode).put("sdp", offer.getSDPMessage().toString());
            ((ObjectNode) rootNode).set("sdp", sdpNode);
            String json = mapper.writeValueAsString(rootNode);
            logger.info("Sending offer:\n{}", json);
            this.webSocketSession.sendMessage(new TextMessage(json));
        } catch (IOException e) {
            logger.error("Couldn't write JSON", e);
        }
    };

    private WebRTCBin.ON_NEGOTIATION_NEEDED onNegotiationNeeded = elem -> {
        logger.info("onNegotiationNeeded: " + elem.getName());
        this.webRTCBin.createOffer(onOfferCreated);
    };
}
