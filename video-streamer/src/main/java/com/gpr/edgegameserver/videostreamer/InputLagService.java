package com.gpr.edgegameserver.videostreamer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputLagService {

    Logger logger = LoggerFactory.getLogger(KmsConnectionService.class);

    private final InputLagRepository inputLagRepository;

    public InputLagService(InputLagRepository inputLagRepository) {
        this.inputLagRepository = inputLagRepository;
    }

    public void saveInputLag(long sentTimestamp) {
        logger.info("Received InputLag with sentTimestamp: {}", sentTimestamp);
        inputLagRepository.save(new InputLag(sentTimestamp, System.currentTimeMillis()));
    }
}
