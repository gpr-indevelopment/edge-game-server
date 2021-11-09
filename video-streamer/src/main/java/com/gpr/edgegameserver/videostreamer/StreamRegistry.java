package com.gpr.edgegameserver.videostreamer;

import java.util.HashMap;
import java.util.Map;

public class StreamRegistry {
    
    private final Map<String, Process> idToStream = new HashMap<>();

    public StreamRegistry() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Process stream : idToStream.values()) {
                if (stream != null) {
                    stream.destroy();
                }
            }
        }));
    }

    public void addStream(String identifier, Process streamProcess) {
        idToStream.put(identifier, streamProcess);
    }

    public void removeStream(String identifier) {
        Process removedStream = idToStream.remove(identifier);
        if (removedStream != null) {
            removedStream.destroy();
        }
    }
}
