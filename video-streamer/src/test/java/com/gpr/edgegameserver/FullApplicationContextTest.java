package com.gpr.edgegameserver;

import com.gpr.edgegameserver.videostreamer.KmsConnectionService;
import com.gpr.edgegameserver.videostreamer.KmsStreamingInfo;
import com.gpr.edgegameserver.videostreamer.StreamStarterService;
import com.gpr.edgegameserver.videostreamer.VideoStreamerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = VideoStreamerConfig.class)
public class FullApplicationContextTest {

    @Autowired
    private KmsConnectionService kmsConnectionService;

    @Autowired
    private StreamStarterService streamStarterService;

    @Test
    public void whenParseSdpWithNullAudioThenCorrectInformationReturns() throws IOException {
        KmsStreamingInfo streamingInfo = kmsConnectionService
                .connectRtp(new ClassPathResource("sdp/sdp-video.sdp").getFile());
        assertThat(streamingInfo.getAudioPort()).isNull();
        assertThat(streamingInfo.getVideoPort()).isNotNull();
    }

    @Test
    public void whenParseSdpWithNullVideoThenCorrectInformationReturns() throws IOException {
        KmsStreamingInfo streamingInfo = kmsConnectionService
                .connectRtp(new ClassPathResource("sdp/sdp-audio.sdp").getFile());
        assertThat(streamingInfo.getAudioPort()).isNotNull();
        assertThat(streamingInfo.getVideoPort()).isNull();
    }

    @Test
    public void whenParseSdpWithAudioAndVideoThenCorrectInformationReturns() throws IOException {
        KmsStreamingInfo streamingInfo = kmsConnectionService
                .connectRtp(new ClassPathResource("sdp/sdp-audio-video.sdp").getFile());
        assertThat(streamingInfo.getAudioPort()).isNotNull();
        assertThat(streamingInfo.getVideoPort()).isNotNull();
    }

    @Test
    public void whenStartStreamThenStreamStarts() {
        //streamStarterService.startStream();
    }
}
