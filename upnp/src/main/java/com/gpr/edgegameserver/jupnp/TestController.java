package com.gpr.edgegameserver.jupnp;

import com.gpr.edgegameserver.jupnp.client.TestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final TestClient testClient;

    public TestController(TestClient testClient) {
        this.testClient = testClient;
    }

    @GetMapping("/devices")
    public void showAvailableDevices() {
        testClient.search();
    }
}
