package com.gpr.edgegameserver.videostreamer;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SdpParserService {

    public Integer getKmsVideoPort(String kmsSdp) {
        Pattern pattern = Pattern.compile("m=video (\\d+) RTP");
        Matcher matcher = pattern.matcher(kmsSdp);
        boolean found = matcher.find();
        if (found) {
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    public Integer getKmsAudioPort(String kmsSdp) {
        Pattern pattern = Pattern.compile("m=audio (\\d+) RTP");
        Matcher matcher = pattern.matcher(kmsSdp);
        boolean found = matcher.find();
        if (found) {
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    public KmsStreamingInfo getStreamingInfo(String kmsSdp) {
        return new KmsStreamingInfo(getKmsVideoPort(kmsSdp), getKmsAudioPort(kmsSdp), kmsSdp);
    }
}
