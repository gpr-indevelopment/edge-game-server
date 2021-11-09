package com.gpr.edgegameserver.web;

import com.gpr.edgegameserver.videostreamer.InputLagRepository;
import com.gpr.edgegameserver.videostreamer.InputLagService;
import com.gpr.edgegameserver.videostreamer.KmsConnectionService;
import com.gpr.edgegameserver.videostreamer.PortAllocator;
import com.gpr.edgegameserver.videostreamer.SdpParserService;
import com.gpr.edgegameserver.videostreamer.StreamRegistry;
import com.gpr.edgegameserver.videostreamer.StreamStarterService;
import org.kurento.client.KurentoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class WebConfiguration {

    @Bean
    public StreamStarterService streamStarterService(KmsConnectionService kmsConnectionService,
                                                     @Value("${sdp-root-folder}") String sdpRootFolderOpt,
                                                     StreamRegistry streamRegistry,
                                                     PortAllocator portAllocator) {
        return new StreamStarterService(kmsConnectionService, Optional.of(sdpRootFolderOpt), streamRegistry, portAllocator);
    }

    @Bean
    public KmsConnectionService kmsConnectionService(KurentoClient kurentoClient,
                                                     SdpParserService sdpParserService,
                                                     IceCandidateEventListener iceCandidateEventListener) {
        return new KmsConnectionService(kurentoClient, sdpParserService, iceCandidateEventListener);
    }

    @Bean
    public SdpParserService sdpParserService() {
        return new SdpParserService();
    }

    @Bean
    public KurentoClient kurentoClient() {
        return KurentoClient.create();
    }

    @Bean
    public StreamRegistry streamRegistry() {
        return new StreamRegistry();
    }

    @Bean
    public InputLagService inputLagService(InputLagRepository inputLagRepository) {
        return new InputLagService(inputLagRepository);
    }
}
