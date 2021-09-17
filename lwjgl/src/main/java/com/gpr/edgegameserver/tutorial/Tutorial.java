package com.gpr.edgegameserver.tutorial;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Tutorial implements Runnable {

    private GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);

    private GLFWKeyCallback keyCallback = new KeyCallback();

    @Override
    public void run() {
        glfwSetErrorCallback(errorCallback);
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to init GLFW");
        }
        long window = glfwCreateWindow(640, 480, "Meu tutorial", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        glfwSetKeyCallback(window, keyCallback);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        while (!glfwWindowShouldClose(window)) {
            double time = glfwGetTime();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        glfwDestroyWindow(window);
        keyCallback.free();
        // Libera todas as callbacks associadas a window
        Callbacks.glfwFreeCallbacks(window);
        glfwTerminate();
        errorCallback.free();
    }

    public static void main(String[] args) {
        new Tutorial().run();
    }
}
