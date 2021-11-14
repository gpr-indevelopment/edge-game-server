package com.gpr.edgegameserver.gstreamerserver.signaling;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpr.edgegameserver.gstreamerserver.WebRtcCallbackManager;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.SDPMessage;
import org.freedesktop.gstreamer.WebRTCSDPType;
import org.freedesktop.gstreamer.WebRTCSessionDescription;
import org.freedesktop.gstreamer.elements.WebRTCBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class SignalingWebsocketHandler implements WebSocketHandler {

    Logger logger = LoggerFactory.getLogger(SignalingWebsocketHandler.class);

    private final String peerId;

    private final Pipeline pipeline;

    private final WebRTCBin webRTCBin;

    private final ObjectMapper mapper = new ObjectMapper();

    private WebRtcCallbackManager webRtcCallbackManager;

    public SignalingWebsocketHandler(String peerId, Pipeline pipeline, WebRTCBin webRTCBin) {
        this.peerId = peerId;
        this.pipeline = pipeline;
        this.webRTCBin = webRTCBin;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("HELLO 564322"));
        this.webRtcCallbackManager = new WebRtcCallbackManager(webRTCBin, session, pipeline);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = ((TextMessage) message).getPayload();
        if (payload.equals("HELLO")) {
            session.sendMessage(new TextMessage("SESSION " + peerId));
        } else if (payload.equals("SESSION_OK")) {
            this.pipeline.play();
        } else if (payload.startsWith("ERROR")) {
            logger.error(payload);
            Gst.quit();
        } else {
            handleSdp(payload);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.info("Some error occurred on transport");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.info("Websocket connection closed.");
        Gst.quit();
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void handleSdp(String payload) {
        try {
            JsonNode answer = mapper.readTree(payload);
            if (answer.has("sdp")) {
                String sdpStr = answer.get("sdp").get("sdp").textValue();
                logger.info("answer SDP:\n{}", sdpStr);
                SDPMessage sdpMessage = new SDPMessage();
                sdpMessage.parseBuffer(sdpStr);
                WebRTCSessionDescription description = new WebRTCSessionDescription(WebRTCSDPType.ANSWER, sdpMessage);
                webRTCBin.setRemoteDescription(description);
            }
            else if (answer.has("ice")) {
                String candidate = answer.get("ice").get("candidate").textValue();
                int sdpMLineIndex = answer.get("ice").get("sdpMLineIndex").intValue();
                logger.info("Adding ICE candidate: {}", candidate);
                webRTCBin.addIceCandidate(sdpMLineIndex, candidate);
            }
        } catch (IOException e) {
            logger.error("Problem reading payload", e);
        }
    }
}
