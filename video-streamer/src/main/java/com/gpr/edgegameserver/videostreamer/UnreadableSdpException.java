package com.gpr.edgegameserver.videostreamer;

public class UnreadableSdpException extends RuntimeException {

    public UnreadableSdpException() {
    }

    public UnreadableSdpException(String message) {
        super(message);
    }

    public UnreadableSdpException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnreadableSdpException(Throwable cause) {
        super(cause);
    }

    public UnreadableSdpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
