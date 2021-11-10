package com.gpr.edgegameserver.gstreamerserver.signaling;

import com.gpr.edgegameserver.gstreamerserver.WebRtcCallbackManager;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.elements.WebRTCBin;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Component
public class SignalingWebSocketClient {

    private WebSocketConnectionManager webSocketConnectionManager;

    private WebRtcCallbackManager webRtcCallbackManager;

    public void startWebSocketConnection(String peerId, Pipeline pipeline, WebRTCBin webRTCBin) {
        webSocketConnectionManager = new WebSocketConnectionManager(
                new StandardWebSocketClient(),
                new SignalingWebsocketHandler(peerId, pipeline, webRTCBin),
                "wss://webrtc.nirbheek.in:8443");
        webSocketConnectionManager.setAutoStartup(true);
        webSocketConnectionManager.start();
    }
}
