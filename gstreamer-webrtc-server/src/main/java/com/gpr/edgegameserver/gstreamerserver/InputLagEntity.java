package com.gpr.edgegameserver.gstreamerserver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INPUT_LAG")
public class InputLagEntity {

    @GeneratedValue
    @Id
    private Long id;

    private long sentTimestamp;

    private long receivedTimestamp;

    private long delta;

    protected InputLagEntity() {

    }

    public InputLagEntity(long sentTimestamp, long receivedTimestamp) {
        this.sentTimestamp = sentTimestamp;
        this.receivedTimestamp = receivedTimestamp;
        this.delta = receivedTimestamp - sentTimestamp;
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
