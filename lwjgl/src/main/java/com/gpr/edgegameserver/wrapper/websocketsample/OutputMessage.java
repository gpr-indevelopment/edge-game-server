package com.gpr.edgegameserver.wrapper.websocketsample;

public class OutputMessage {

    public OutputMessage() {
    }

    public OutputMessage(String from, String text, long timestamp) {
        this.from = from;
        this.text = text;
        this.timestamp = timestamp;
    }

    private String from;

    private String text;

    private long timestamp;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
