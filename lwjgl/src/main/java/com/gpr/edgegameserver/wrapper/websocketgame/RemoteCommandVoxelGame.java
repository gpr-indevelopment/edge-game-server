package com.gpr.edgegameserver.wrapper.websocketgame;

import com.gpr.edgegameserver.voxelgame.VoxelGameGL;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class RemoteCommandVoxelGame extends VoxelGameGL {

    public void processKeyUp(GameCommand gameCommand) {
        processIfValidCommand(gameCommand, (command) -> keydown[command.getGlKeyCode()] = false);
    }

    public void processKeyDown(GameCommand gameCommand) {
        processIfValidCommand(gameCommand, (command) -> keydown[command.getGlKeyCode()] = true);
    }

    private void processIfValidCommand(GameCommand gameCommand, Consumer<KeyCommand> keyCommandConsumer) {
        KeyCommand keyCommand = gameCommand.getKeyCommand();
        if (keyCommand.isMappedCommand()) {
            keyCommandConsumer.accept(keyCommand);
        }
    }
}
