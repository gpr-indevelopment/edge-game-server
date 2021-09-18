package com.gpr.edgegameserver.wrapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/is-alive")
    public String isAlive() {
        return "I am alive!";
    }
}
