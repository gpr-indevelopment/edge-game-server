package com.gpr.edgegameserver.gstreamerserver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.PadDirection;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.elements.DecodeBin;
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
        this.webRTCBin.connect(onIncomingStream);
    }

    private Element.PAD_ADDED onIncomingDecodebinStream = (element, pad) -> {
        logger.info("onIncomingDecodebinStream");
        if (!pad.hasCurrentCaps()) {
            logger.info("Pad has no caps, ignoring: {}", pad.getName());
            return;
        }
        Structure caps = pad.getCaps().getStructure(0);
        String name = caps.getName();
        if (name.startsWith("video")) {
            logger.info("onIncomingDecodebinStream video");
            Element queue = ElementFactory.make("queue", "my-videoqueue");
            Element videoconvert = ElementFactory.make("videoconvert", "my-videoconvert");
            Element autovideosink = ElementFactory.make("autovideosink", "my-autovideosink");
            this.pipeline.addMany(queue, videoconvert, autovideosink);
            queue.syncStateWithParent();
            videoconvert.syncStateWithParent();
            autovideosink.syncStateWithParent();
            pad.link(queue.getStaticPad("sink"));
            queue.link(videoconvert);
            videoconvert.link(autovideosink);
        }
        if (name.startsWith("audio")) {
            logger.info("onIncomingDecodebinStream audio");
            Element queue = ElementFactory.make("queue", "my-audioqueue");
            Element audioconvert = ElementFactory.make("audioconvert", "my-audioconvert");
            Element audioresample = ElementFactory.make("audioresample", "my-audioresample");
            Element autoaudiosink = ElementFactory.make("autoaudiosink", "my-autoaudiosink");
            this.pipeline.addMany(queue, audioconvert, audioresample, autoaudiosink);
            queue.syncStateWithParent();
            audioconvert.syncStateWithParent();
            audioresample.syncStateWithParent();
            autoaudiosink.syncStateWithParent();
            pad.link(queue.getStaticPad("sink"));
            queue.link(audioconvert);
            audioconvert.link(audioresample);
            audioresample.link(autoaudiosink);
        }
    };

    private Element.PAD_ADDED onIncomingStream = (element, pad) -> {
        if (pad.getDirection() != PadDirection.SRC) {
            logger.info("Pad is not source, ignoring: {}", pad.getDirection());
            return;
        }
        logger.info("Receiving stream! Element: {} Pad: {}", element.getName(), pad.getName());
        DecodeBin decodebin = new DecodeBin("my-decoder-" + pad.getName());
        decodebin.connect(onIncomingDecodebinStream);
        this.pipeline.add(decodebin);
        decodebin.syncStateWithParent();
        webRTCBin.link(decodebin);
    };

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
