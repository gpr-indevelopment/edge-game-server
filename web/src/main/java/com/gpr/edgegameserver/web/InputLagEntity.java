package com.gpr.edgegameserver.web;

import com.gpr.edgegameserver.videostreamer.InputLag;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class InputLagEntity {

    @GeneratedValue
    @Id
    private Long id;

    private long sentTimestamp;

    private long receivedTimestamp;

    private long delta;

    public InputLagEntity(InputLag inputLag) {
        this.sentTimestamp = inputLag.getSentTimestamp();
        this.receivedTimestamp = inputLag.getReceivedTimestamp();
        this.delta = inputLag.getDelta();
    }

    protected InputLagEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
