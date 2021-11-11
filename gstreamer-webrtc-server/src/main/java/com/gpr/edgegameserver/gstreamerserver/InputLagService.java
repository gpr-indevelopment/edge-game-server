package com.gpr.edgegameserver.gstreamerserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class InputLagService {

    Logger logger = LoggerFactory.getLogger(InputLagService.class);

    private final InputLagRepository inputLagRepository;

    public InputLagService(InputLagRepository inputLagRepository) {
        this.inputLagRepository = inputLagRepository;
    }

    @PreDestroy
    private void dumpInputLag() throws IOException {
        Path tempFile = Files.createTempFile("input-lag-dump", ".csv");
        StringBuilder sb = new StringBuilder();
        sb.append("sentTimestamp,").append("receivedTimestamp,").append("delta").append("\n");
        inputLagRepository.findAll().forEach(inputLagEntity -> {
            sb
                    .append(inputLagEntity.getSentTimestamp())
                    .append(",")
                    .append(inputLagEntity.getReceivedTimestamp())
                    .append(",")
                    .append(inputLagEntity.getDelta())
                    .append("\n");
        });
        Files.write(tempFile, sb.toString().getBytes(StandardCharsets.UTF_8));
        logger.info("Saved input-lag dump to path: {}", tempFile);
    }

    public void register(Long sentTimestamp) {
        InputLagEntity savedEntity = inputLagRepository.save(new InputLagEntity(sentTimestamp, System.currentTimeMillis()));
        logger.info("Registered InputLag with delta: {} ms", savedEntity.getDelta());
    }
}
