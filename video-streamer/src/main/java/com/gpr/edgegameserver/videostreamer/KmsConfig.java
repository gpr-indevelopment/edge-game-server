package com.gpr.edgegameserver.videostreamer;

import org.kurento.client.KurentoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KmsConfig {

    @Bean
    public KurentoClient kurentoClient()
    {
        return KurentoClient.create();
    }
}
