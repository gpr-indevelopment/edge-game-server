package com.gpr.edgegameserver.web;

import com.google.gson.JsonObject;
import com.gpr.edgegameserver.videostreamer.KmsConnectionService;
import com.gpr.edgegameserver.web.websocket.WebSocketSenderService;
import org.kurento.client.EventListener;
import org.kurento.client.IceCandidateFoundEvent;
import org.kurento.jsonrpc.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class IceCandidateEventListener implements EventListener<IceCandidateFoundEvent> {

    Logger logger = LoggerFactory.getLogger(KmsConnectionService.class);

    private final WebSocketSenderService webSocketSenderService;

    public IceCandidateEventListener(WebSocketSenderService webSocketSenderService) {
        this.webSocketSenderService = webSocketSenderService;
    }

    @Override
    public void onEvent(IceCandidateFoundEvent ev) {
        logger.info("Sending IceCandidate to frontend source: {}, timestamp: {}, tags: {}, candidate: {}",
                ev.getSource().getName(), ev.getTimestamp(),
                ev.getTags(), JsonUtils.toJsonObject(ev.getCandidate()));

        JsonObject message = new JsonObject();
        message.addProperty("id", "ADD_ICE_CANDIDATE");
        message.add("candidate", JsonUtils.toJsonObject(ev.getCandidate()));
        webSocketSenderService.sendMessage(message.toString());
    }
}
