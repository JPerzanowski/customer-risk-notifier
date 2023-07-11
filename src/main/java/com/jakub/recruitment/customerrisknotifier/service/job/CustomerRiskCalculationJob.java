package com.jakub.recruitment.customerrisknotifier.service.job;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CustomerRiskCalculationJob {
    @Value("${app.file.location}")
    private String uploadFileLocation;

    @Scheduled(cron = "${app.job.cron-customer-risk-calculation}")
    public void customerRiskCalculationJob() {

    }
}
