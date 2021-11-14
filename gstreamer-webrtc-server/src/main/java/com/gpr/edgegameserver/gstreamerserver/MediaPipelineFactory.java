package com.gpr.edgegameserver.gstreamerserver;

import com.gpr.edgegameserver.gstreamerserver.signaling.SignalingWebSocketClient;
import org.freedesktop.gstreamer.Bin;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.elements.WebRTCBin;
import org.springframework.stereotype.Component;

@Component
public class MediaPipelineFactory {

    private static final String VIDEO_BIN_DESCRIPTION = "dx9screencapsrc cursor=true ! videoconvert ! queue ! nvh264enc zerolatency=true ! rtph264pay ! queue ! capsfilter caps=application/x-rtp,media=video,encoding-name=H264,framerate=60/1,width=1920,height=1080,payload=97";

    private static final String AUDIO_BIN_DESCRIPTION = "audiotestsrc ! audioconvert ! audioresample ! queue ! opusenc ! rtpopuspay ! queue ! capsfilter caps=application/x-rtp,media=audio,encoding-name=OPUS,payload=96";

    private final SignalingWebSocketClient signalingWebSocketClient;

    public MediaPipelineFactory(SignalingWebSocketClient signalingWebSocketClient) {
        this.signalingWebSocketClient = signalingWebSocketClient;
    }

    public void buildPipeline(String peerId) {
        Gst.init();
        WebRTCBin webRTCBin = new WebRTCBin("gstreamer-webrtc-server");
        Bin video = Gst.parseBinFromDescription(VIDEO_BIN_DESCRIPTION, true);
        Bin audio = Gst.parseBinFromDescription(AUDIO_BIN_DESCRIPTION, true);
        Pipeline pipeline = new Pipeline();
        pipeline.addMany(webRTCBin, video, audio);
        video.link(webRTCBin);
        audio.link(webRTCBin);
        setMediaDirection(webRTCBin);
        signalingWebSocketClient.startWebSocketConnection(peerId, pipeline, webRTCBin);
    }

    private void setMediaDirection(WebRTCBin webRTCBin) {
        GstObject videoTransceiver = webRTCBin.emit(GstObject.class, "get-transceiver", 0);
        videoTransceiver.set("direction", 2);
        GstObject audioTransceiver = webRTCBin.emit(GstObject.class, "get-transceiver", 1);
        audioTransceiver.set("direction", 2);
    }
}
