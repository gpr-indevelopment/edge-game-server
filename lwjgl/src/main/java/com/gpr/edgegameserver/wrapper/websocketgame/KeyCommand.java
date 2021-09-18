package com.gpr.edgegameserver.wrapper.websocketgame;

import com.fasterxml.jackson.annotation.JsonCreator;

import static org.lwjgl.glfw.GLFW.*;

public enum KeyCommand {
    KEY_W("KeyW", GLFW_KEY_W),
    KEY_A("KeyA", GLFW_KEY_A),
    KEY_S("KeyS", GLFW_KEY_S),
    KEY_D("KeyD", GLFW_KEY_D),
    UNMAPPED_COMMAND("invalid", 0);

    private final String alias;

    private final int glKeyCode;

    KeyCommand(String alias, int glKeyCode) {
        this.alias = alias;
        this.glKeyCode = glKeyCode;
    }

    public int getGlKeyCode() {
        return glKeyCode;
    }

    @JsonCreator
    public static KeyCommand fromAlias(String alias) {
        for (KeyCommand value : KeyCommand.values()) {
            if (value.alias.equals(alias)) {
                return value;
            }
        }
        return UNMAPPED_COMMAND;
    }

    public boolean isMappedCommand() {
        return !this.equals(UNMAPPED_COMMAND);
    }
}
