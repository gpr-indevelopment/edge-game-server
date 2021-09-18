package com.gpr.edgegameserver.wrapper.websocketgame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebsocketController {

    private final RemoteCommandVoxelGame remoteCommandVoxelGame;

    public GameWebsocketController(RemoteCommandVoxelGame remoteCommandVoxelGame) {
        this.remoteCommandVoxelGame = remoteCommandVoxelGame;
    }

    private static final Logger logger = LoggerFactory.getLogger(GameWebsocketController.class);

    @MessageMapping("/key-down")
    public void keyDown(GameCommand message) {
        remoteCommandVoxelGame.processKeyDown(message);
    }

    @MessageMapping("/key-up")
    public void keyUp(GameCommand message) {
        remoteCommandVoxelGame.processKeyUp(message);
    }
}
