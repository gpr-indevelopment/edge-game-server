package com.gpr.edgegameserver.wrapper;

import com.gpr.edgegameserver.wrapper.websocketgame.RemoteCommandVoxelGame;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class GameStarter {

    private final RemoteCommandVoxelGame remoteCommandVoxelGame;

    public GameStarter(RemoteCommandVoxelGame remoteCommandVoxelGame) {
        this.remoteCommandVoxelGame = remoteCommandVoxelGame;
    }

    @PostConstruct
    private void startGame() {
        new Thread(remoteCommandVoxelGame, "GameThread").start();
    }
}
