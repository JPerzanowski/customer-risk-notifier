package com.jakub.recruitment.customerrisknotifier.service.job;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Component
public class CustomerRiskCalculationJob {
    @Value("${app.file.location}")
    private String uploadFileLocation;

    @Scheduled(cron = "${app.job.cron-customer-risk-calculation}")
    public void customerRiskCalculationJob() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            File uploadDir = new File(uploadFileLocation);
            if (!uploadDir.exists())
                Files.createDirectory(uploadDir.toPath());
            uploadDir.toPath().register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key;
            try {
                while ((key = watchService.take()) != null) {
                    key.pollEvents().get(0);
                    Files.list(Paths.get(uploadFileLocation))
                            .map(p -> p.getFileName().toFile().getName())
                            .toList().forEach(System.out::println);
                    key.reset();
                }
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
