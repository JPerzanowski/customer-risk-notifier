package com.jakub.recruitment.customerrisknotifier.service.job;

import com.jakub.recruitment.customerrisknotifier.entity.CustomerDetails;
import com.jakub.recruitment.customerrisknotifier.entity.CustomerRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Component
public class CustomerRiskCalculationJob {
    @Value("${app.file.location}")
    private String uploadFileLocation;

    private final CustomerRepository customerRepository;

    public CustomerRiskCalculationJob(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Scheduled(cron = "${app.job.cron-customer-risk-calculation}")
    public void customerRiskCalculationJob() throws IOException {
        importCsvFilesFromFolder(uploadFileLocation);
    }

    private void importCsvFilesFromFolder(String folderPath) throws IOException {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
                    importCsv(file.getPath());
                }
            }
        }
    }

    private void importCsv(String filePath) throws IOException {
        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                String infoAsOfDate = csvRecord.get("info_as_of_date");
                Integer customerId = Integer.valueOf(csvRecord.get("customer_id"));
                String customerName = csvRecord.get("customer_name");
                String customerStartDate = csvRecord.get("customer_start_date");
                String customerType = csvRecord.get("customer_type");
                Float customerIncome = Float.valueOf(csvRecord.get("customer_income"));
                String customerRiskClass = csvRecord.get("customer_risk_class");
                String customerBusinessType = csvRecord.get("customer_business_type");

                CustomerDetails customerDetails = new CustomerDetails(infoAsOfDate, customerId, customerName,
                        customerStartDate, customerType, customerIncome, customerRiskClass, customerBusinessType);
                customerRepository.save(customerDetails);
            }
        }
    }
}
