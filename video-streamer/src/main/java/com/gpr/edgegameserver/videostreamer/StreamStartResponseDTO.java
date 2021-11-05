package com.gpr.edgegameserver.videostreamer;

public class StreamStartResponseDTO {

    private String id;

    private String kmsSdpAnswer;

    public StreamStartResponseDTO(String id, String kmsSdpAnswer) {
        this.id = id;
        this.kmsSdpAnswer = kmsSdpAnswer;
    }

    public String getId() {
        return id;
    }

    public String getKmsSdpAnswer() {
        return kmsSdpAnswer;
    }
}
