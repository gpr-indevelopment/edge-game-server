package com.gpr.edgegameserver.videostreamer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FfmpegStartCommand {

    private final String sdpFilePath;

    private final String rtpOutputAddress;

    private final List<String> fullCommand;

    public FfmpegStartCommand(File outputSdpFile, Integer videoStreamPort) {
        String rtpAddress = "rtp://127.0.0.1:" + videoStreamPort;
        this.rtpOutputAddress = rtpAddress;
        String sdpFilePath = outputSdpFile.getAbsolutePath();
        this.sdpFilePath = sdpFilePath;
        List<String> fullCommand = new ArrayList<>();
        fullCommand.add("ffmpeg");
        fullCommand.add("-video_size");
        fullCommand.add("1366x768");
        fullCommand.add("-f");
        fullCommand.add("x11grab");
        fullCommand.add("-i");
        fullCommand.add(":0.0");
        fullCommand.add("-c:v");
        fullCommand.add("libx264");
        fullCommand.add("-preset");
        fullCommand.add("ultrafast");
        fullCommand.add("-sdp_file");
        fullCommand.add(this.sdpFilePath);
        fullCommand.add("-f");
        fullCommand.add("rtp");
        fullCommand.add(this.rtpOutputAddress);
        this.fullCommand = fullCommand;
    }

    public String getSdpFilePath() {
        return sdpFilePath;
    }

    public String getRtpOutputAddress() {
        return rtpOutputAddress;
    }

    public List<String> getFullCommand() {
        return fullCommand;
    }
}
