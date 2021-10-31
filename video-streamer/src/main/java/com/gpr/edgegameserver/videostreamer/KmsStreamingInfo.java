package com.gpr.edgegameserver.videostreamer;

public class KmsStreamingInfo {

    private final Integer videoPort;

    private final Integer audioPort;

    public KmsStreamingInfo(Integer videoPort, Integer audioPort) {
        this.videoPort = videoPort;
        this.audioPort = audioPort;
    }

    public Integer getVideoPort() {
        return videoPort;
    }

    public Integer getAudioPort() {
        return audioPort;
    }
}
