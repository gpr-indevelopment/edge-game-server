package com.gpr.edgegameserver.gstreamerserver.websocket;

import com.gpr.edgegameserver.gstreamerserver.InputLagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    private final InputLagService inputLagService;

    public WebSocketController(InputLagService inputLagService) {
        this.inputLagService = inputLagService;
    }

    @MessageMapping("/input-lag")
    public void saveInputLag(Map<String, Object> payload) {
        inputLagService.register((Long) payload.get("sentTimestamp"));
    }
}
