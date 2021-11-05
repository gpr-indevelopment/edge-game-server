package com.gpr.edgegameserver.web.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gpr.edgegameserver.videostreamer.KmsConnectionService;
import org.kurento.client.IceCandidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private KmsConnectionService kmsConnectionService;

    private final Gson gson = new Gson();

    @MessageMapping("/message")
    public void addIceCandidate(String message) {
        kmsConnectionService.addWebRtcIceCandidate(buildIceCandidate(message));
    }

    private IceCandidate buildIceCandidate(String message) {
        JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
        JsonObject jsonCandidate = jsonMessage.get("candidate").getAsJsonObject();
        return new IceCandidate(jsonCandidate.get("candidate").getAsString(),
                        jsonCandidate.get("sdpMid").getAsString(),
                        jsonCandidate.get("sdpMLineIndex").getAsInt());
    }
}
