package com.jakub.recruitment.customerrisknotifier.service;

import com.jakub.recruitment.customerrisknotifier.model.entity.CustomerEntity;
import com.jakub.recruitment.customerrisknotifier.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerRiskService {

    private final CustomerRepository customerRepository;

    public boolean checkIfRiskHasChanged(Integer customerId) {
        boolean isChanged = false;
        List<CustomerEntity> customerEntities = customerRepository.findTop2ByCustomerIdOrderByInfoAsOfDateDesc(customerId);
        int sizeOfCustomers = customerEntities.size();
        if (sizeOfCustomers < 2 || customerEntities.get(0).getCustomerRiskClass().equals(customerEntities.get(1).getCustomerRiskClass()))
            return isChanged;
        return true;
    }

    public CustomerEntity getCustomerById(Integer customerId) throws Exception {
        CustomerEntity customerEntity = customerRepository.findFirstByCustomerIdOrderByInfoAsOfDateDesc(customerId);

        if (customerEntity == null)
            throw new Exception("User with id " + customerId + " not found.");

        return customerEntity;
    }
}
