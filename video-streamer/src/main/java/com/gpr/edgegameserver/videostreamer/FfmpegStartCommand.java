package com.gpr.edgegameserver.videostreamer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FfmpegStartCommand {

    private final String sdpFilePath;

    private final String rtpOutputAddress;

    private final List<String> fullCommand;

    // Linux
//    public FfmpegStartCommand(File outputSdpFile, Integer videoStreamPort) {
//        String rtpAddress = "rtp://127.0.0.1:" + videoStreamPort;
//        this.rtpOutputAddress = rtpAddress;
//        String sdpFilePath = outputSdpFile.getAbsolutePath();
//        this.sdpFilePath = sdpFilePath;
//        List<String> fullCommand = new ArrayList<>();
//        fullCommand.add("ffmpeg");
//        fullCommand.add("-video_size");
//        fullCommand.add("1366x768");
//        fullCommand.add("-f");
//        fullCommand.add("x11grab");
//        fullCommand.add("-i");
//        fullCommand.add(":0.0");
//        fullCommand.add("-c:v");
//        fullCommand.add("libx264");
//        fullCommand.add("-preset");
//        fullCommand.add("ultrafast");
//        fullCommand.add("-sdp_file");
//        fullCommand.add(this.sdpFilePath);
//        fullCommand.add("-f");
//        fullCommand.add("rtp");
//        fullCommand.add(this.rtpOutputAddress);
//        this.fullCommand = fullCommand;
//    }

    // Windows
    public FfmpegStartCommand(File outputSdpFile, Integer videoStreamPort) {
        String rtpAddress = "rtp://127.0.0.1:" + videoStreamPort;
        this.rtpOutputAddress = rtpAddress;
        String sdpFilePath = outputSdpFile.getAbsolutePath();
        this.sdpFilePath = sdpFilePath;
        List<String> fullCommand = new ArrayList<>();
        fullCommand.add("D:\\ffmpeg-n4.4.1-win64-gpl-4.4\\windows-build\\ffmpeg\\ffmpeg.exe");
        fullCommand.add("-y");
        fullCommand.add("-vsync");
        fullCommand.add("0");
        fullCommand.add("-hwaccel");
        fullCommand.add("cuda");
        fullCommand.add("-hwaccel_output_format");
        fullCommand.add("cuda");
        fullCommand.add("-f");
        fullCommand.add("gdigrab");
        fullCommand.add("-framerate");
        fullCommand.add("60");
        fullCommand.add("-i");
        fullCommand.add("title=\"Path");
        fullCommand.add("of");
        fullCommand.add("Exile\"");
        fullCommand.add("-c:v");
        fullCommand.add("h264_nvenc");
        fullCommand.add("-preset");
        fullCommand.add("p2");
        fullCommand.add("-b:v");
        fullCommand.add("5M");
        fullCommand.add("-bufsize");
        fullCommand.add("5M");
        fullCommand.add("-maxrate");
        fullCommand.add("10M");
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

    public String getCommandString() {
        StringBuilder sb = new StringBuilder();
        for (String commandPart : fullCommand) {
            sb.append(commandPart).append(" ");
        }
        return sb.toString();
    }
}
