package com.gpr.edgegameserver.wrapper;

import java.awt.*;
import java.awt.event.KeyEvent;

public class OutraCoisa {

    public static void main(String[] args) throws AWTException, InterruptedException {
        while (true) {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_A);
            Thread.sleep(2000);
            r.keyPress(KeyEvent.VK_D);
        }
    }
}
