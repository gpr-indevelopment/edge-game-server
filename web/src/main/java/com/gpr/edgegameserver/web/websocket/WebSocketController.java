package com.gpr.edgegameserver.web.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gpr.edgegameserver.videostreamer.InputLagService;
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

    private final KmsConnectionService kmsConnectionService;

    private final InputLagService inputLagService;

    public WebSocketController(KmsConnectionService kmsConnectionService, InputLagService inputLagService) {
        this.kmsConnectionService = kmsConnectionService;
        this.inputLagService = inputLagService;
    }

    private final Gson gson = new Gson();

    @MessageMapping("/message")
    public void addIceCandidate(String message) {
        kmsConnectionService.addWebRtcIceCandidate(buildIceCandidate(message));
    }

    @MessageMapping("/inputLag")
    public void saveInputLag(long sentTimestamp) {
        inputLagService.saveInputLag(sentTimestamp);
    }

    private IceCandidate buildIceCandidate(String message) {
        JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
        return new IceCandidate(jsonMessage.get("candidate").getAsString(),
                jsonMessage.get("sdpMid").getAsString(),
                jsonMessage.get("sdpMLineIndex").getAsInt());
    }
}
