package com.gpr.edgegameserver.videostreamer;

public class FfmpegCommandException extends RuntimeException {

    public FfmpegCommandException() {
    }

    public FfmpegCommandException(String message) {
        super(message);
    }

    public FfmpegCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public FfmpegCommandException(Throwable cause) {
        super(cause);
    }

    public FfmpegCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
