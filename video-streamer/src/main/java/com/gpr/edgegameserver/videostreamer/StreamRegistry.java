package com.gpr.edgegameserver.videostreamer;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
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
        idToStream.remove(identifier);
    }
}
