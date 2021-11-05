package com.gpr.edgegameserver.web.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Toda mensagem websocket com o prefixo /game vai para o servidor
        registry.setApplicationDestinationPrefixes("/server");
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint para os clients configurarem o SockJS
        registry.addEndpoint("/web-socket").setAllowedOrigins("http://localhost:3000");
        registry.addEndpoint("/web-socket").setAllowedOrigins("http://localhost:3000").withSockJS();
    }
}
