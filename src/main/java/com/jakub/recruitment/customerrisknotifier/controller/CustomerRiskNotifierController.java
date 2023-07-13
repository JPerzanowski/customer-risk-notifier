package com.jakub.recruitment.customerrisknotifier.controller;

import com.jakub.recruitment.customerrisknotifier.model.dto.CustomerDto;
import com.jakub.recruitment.customerrisknotifier.service.CustomerRiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CustomerRiskNotifierController {

    private final CustomerRiskService customerRiskService;

    @GetMapping("/api/customer/{customer_id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customer_id") Integer customerId) throws Exception {
        return ResponseEntity.ok(customerRiskService.getLatestCustomerData(customerId));
    }

    @GetMapping("/api/customer/{customer_id}/date")
    public List<CustomerDto> getCustomer(@PathVariable("customer_id") Integer customerId,
                                         @PathVariable("date") Date date) {
        return ResponseEntity.ok(customerRiskService.findCustomerByIdAndDate(customerId, date)).getBody();
    }
}
