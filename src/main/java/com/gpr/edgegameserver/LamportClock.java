package com.gpr.edgegameserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LamportClock {

    private static Logger logger = LoggerFactory.getLogger(InputLagService.class);

    private static Long referenceDeltaTime = 0L;

    private static void setReferenceTime(Long timestamp) {
        if (timestamp > nowWithReference()) {
            logger.warn("Provided sender timestamp is less than current timestamp. Adjusting according to Lamport logical clock.");
            referenceDeltaTime = 1 + timestamp - System.currentTimeMillis();
        }
    }

    public static Long nowWithReference() {
        return System.currentTimeMillis() + referenceDeltaTime;
    }

    public static Long now(Long senderTimestamp) {
        setReferenceTime(senderTimestamp);
        return nowWithReference();
    }
}
