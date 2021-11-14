package com.gpr.edgegameserver.gstreamerserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
public class VideoStatsService {

    Logger logger = LoggerFactory.getLogger(VideoStatsService.class);

    private final VideoStatsRepository videoStatsRepository;

    public VideoStatsService(VideoStatsRepository videoStatsRepository) {
        this.videoStatsRepository = videoStatsRepository;
    }

    @PreDestroy
    private void dumpInputLag() throws IOException {
        Path tempFile = Files.createTempFile("video-stats-dump", ".csv");
        StringBuilder sb = new StringBuilder();
        sb.append("timestamp").append(",");
        sb.append("jitter").append(",");
        sb.append("packetsLost").append(",");
        sb.append("packetsReceived").append(",");
        sb.append("bytesReceived").append(",");
        sb.append("headerBytesReceived").append(",");
        sb.append("framesReceived").append(",");
        sb.append("frameWidth").append(",");
        sb.append("frameHeight").append(",");
        sb.append("framesPerSecond").append(",");
        sb.append("framesDropped").append(",");
        sb.append("totalDecodeTime").append("\n");
        videoStatsRepository.findAll().forEach(videoStat -> {
            sb
                    .append(videoStat.getTimestamp())
                    .append(",")
                    .append(videoStat.getJitter())
                    .append(",")
                    .append(videoStat.getPacketsLost())
                    .append(",")
                    .append(videoStat.getPacketsReceived())
                    .append(",")
                    .append(videoStat.getBytesReceived())
                    .append(",")
                    .append(videoStat.getHeaderBytesReceived())
                    .append(",")
                    .append(videoStat.getFramesReceived())
                    .append(",")
                    .append(videoStat.getFrameWidth())
                    .append(",")
                    .append(videoStat.getFrameHeight())
                    .append(",")
                    .append(videoStat.getFramesPerSecond())
                    .append(",")
                    .append(videoStat.getFramesDropped())
                    .append(",")
                    .append(videoStat.getTotalDecodeTime())
                    .append("\n");
        });
        Files.write(tempFile, sb.toString().getBytes(StandardCharsets.UTF_8));
        logger.info("Saved video-stats dump to path: {}", tempFile);
    }

    public void registerStats(Map<String, String> videoStats) {
        try {
            VideoStatsEntity videoStatsEntity = new VideoStatsEntity();
            String timestamp = videoStats.get("timestamp");
            videoStatsEntity.setTimestamp(timestamp.contains(".") ? Long.valueOf(timestamp.split("\\.")[0]): Long.valueOf(timestamp));
            videoStatsEntity.setJitter(Double.valueOf(videoStats.get("jitter")));
            videoStatsEntity.setPacketsLost(Long.valueOf(videoStats.get("packetsLost")));
            videoStatsEntity.setPacketsReceived(Long.valueOf(videoStats.get("packetsReceived")));
            videoStatsEntity.setBytesReceived(Long.valueOf(videoStats.get("bytesReceived")));
            videoStatsEntity.setHeaderBytesReceived(Long.valueOf(videoStats.get("headerBytesReceived")));
            videoStatsEntity.setFramesReceived(Long.valueOf(videoStats.get("framesReceived")));
            videoStatsEntity.setFrameWidth(videoStats.get("frameWidth") == null ? null : Long.valueOf(videoStats.get("frameWidth")));
            videoStatsEntity.setFrameHeight(videoStats.get("frameHeight") == null ? null : Long.valueOf(videoStats.get("frameHeight")));
            videoStatsEntity.setFramesPerSecond(videoStats.get("framesPerSecond") == null ? null : Long.valueOf(videoStats.get("framesPerSecond")));
            videoStatsEntity.setFramesDropped(Long.valueOf(videoStats.get("framesDropped")));
            videoStatsEntity.setTotalDecodeTime(Double.valueOf(videoStats.get("totalDecodeTime")));
            videoStatsRepository.save(videoStatsEntity);
        } catch (DataIntegrityViolationException ignored) { }
        catch (Exception e) {
            logger.error("Unable to register video stats with exception type: {}. message: {}", e.getClass().getSimpleName(), e.getMessage());
        }
    }
}
