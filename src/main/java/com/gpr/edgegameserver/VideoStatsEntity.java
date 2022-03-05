package com.gpr.edgegameserver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        name="VIDEO_STATS",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"TIMESTAMP"})
)
public class VideoStatsEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long timestamp;

    private Double jitter;

    private Long packetsLost;

    private Long packetsReceived;

    private Long bytesReceived;

    private Long headerBytesReceived;

    private Long framesReceived;

    private Long frameWidth;

    private Long frameHeight;

    private Long framesPerSecond;

    private Long framesDropped;

    private Double totalDecodeTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getJitter() {
        return jitter;
    }

    public void setJitter(Double jitter) {
        this.jitter = jitter;
    }

    public Long getPacketsLost() {
        return packetsLost;
    }

    public void setPacketsLost(Long packetsLost) {
        this.packetsLost = packetsLost;
    }

    public Long getPacketsReceived() {
        return packetsReceived;
    }

    public void setPacketsReceived(Long packetsReceived) {
        this.packetsReceived = packetsReceived;
    }

    public Long getBytesReceived() {
        return bytesReceived;
    }

    public void setBytesReceived(Long bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public Long getHeaderBytesReceived() {
        return headerBytesReceived;
    }

    public void setHeaderBytesReceived(Long headerBytesReceived) {
        this.headerBytesReceived = headerBytesReceived;
    }

    public Long getFramesReceived() {
        return framesReceived;
    }

    public void setFramesReceived(Long framesReceived) {
        this.framesReceived = framesReceived;
    }

    public Long getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(Long frameWidth) {
        this.frameWidth = frameWidth;
    }

    public Long getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(Long frameHeight) {
        this.frameHeight = frameHeight;
    }

    public Long getFramesPerSecond() {
        return framesPerSecond;
    }

    public void setFramesPerSecond(Long framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }

    public Long getFramesDropped() {
        return framesDropped;
    }

    public void setFramesDropped(Long framesDropped) {
        this.framesDropped = framesDropped;
    }

    public Double getTotalDecodeTime() {
        return totalDecodeTime;
    }

    public void setTotalDecodeTime(Double totalDecodeTime) {
        this.totalDecodeTime = totalDecodeTime;
    }
}
