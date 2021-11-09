package com.gpr.edgegameserver.videostreamer;

public class InputLag {

    private long sentTimestamp;

    private long receivedTimestamp;

    private long delta;

    public InputLag(long sentTimestamp, long receivedTimestamp) {
        this.sentTimestamp = sentTimestamp;
        this.receivedTimestamp = receivedTimestamp;
        this.delta = receivedTimestamp - sentTimestamp;
    }

    public long getSentTimestamp() {
        return sentTimestamp;
    }

    public void setSentTimestamp(long sentTimestamp) {
        this.sentTimestamp = sentTimestamp;
    }

    public long getReceivedTimestamp() {
        return receivedTimestamp;
    }

    public void setReceivedTimestamp(long receivedTimestamp) {
        this.receivedTimestamp = receivedTimestamp;
    }

    public long getDelta() {
        return delta;
    }

    public void setDelta(long delta) {
        this.delta = delta;
    }
}
