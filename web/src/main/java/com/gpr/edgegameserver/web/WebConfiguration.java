package com.gpr.edgegameserver.web;

import com.gpr.edgegameserver.videostreamer.VideoStreamerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(VideoStreamerConfig.class)
public class WebConfiguration {
}
