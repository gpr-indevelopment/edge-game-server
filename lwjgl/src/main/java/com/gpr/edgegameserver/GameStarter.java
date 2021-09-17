package com.gpr.edgegameserver;

import com.gpr.edgegameserver.voxelgame.VoxelGameGL;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class GameStarter {

    @PostConstruct
    private void startGame() {
        new Thread(new VoxelGameGL(), "GameThread").start();
    }
}
