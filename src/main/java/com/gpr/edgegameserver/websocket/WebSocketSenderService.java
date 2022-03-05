package com.gpr.edgegameserver.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

@Service
public class WebSocketSenderService {

    @Autowired
    private SimpMessagingTemplate template;

    public void sendMessage(String message) {
        this.template.convertAndSend("/topic/message", new TextMessage(message));
    }
}
