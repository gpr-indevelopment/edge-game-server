package com.gpr.edgegameserver.wrapper.websocketsample;

import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Profile("websocketsample")
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) {
        return new OutputMessage(message.getFrom(), message.getText(), System.currentTimeMillis());
    }
}
