package com.gpr.edgegameserver.videostreamer;

public class KmsStreamingInfo {

    private final Integer videoPort;

    private final Integer audioPort;

    private final String kmsSdpAnswer;

    public KmsStreamingInfo(Integer videoPort, Integer audioPort, String kmsSdpAnswer) {
        this.videoPort = videoPort;
        this.audioPort = audioPort;
        this.kmsSdpAnswer = kmsSdpAnswer;
    }

    public Integer getVideoPort() {
        return videoPort;
    }

    public Integer getAudioPort() {
        return audioPort;
    }

    public String getKmsSdpAnswer() {
        return kmsSdpAnswer;
    }
}
