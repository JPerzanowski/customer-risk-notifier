package com.jakub.recruitment.customerrisknotifier.service;

import com.jakub.recruitment.customerrisknotifier.model.dto.CustomerDto;
import com.jakub.recruitment.customerrisknotifier.model.entity.CustomerEntity;
import com.jakub.recruitment.customerrisknotifier.repository.CustomerRepository;
import com.jakub.recruitment.customerrisknotifier.service.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerRiskService {

    private final CustomerRepository customerRepository;

    public CustomerDto getLatestCustomerData(Integer customerId) throws Exception {
        CustomerDto customerDto = new CustomerDto();
        CustomerEntity customerEntity = customerRepository.findFirstByCustomerIdOrderByInfoAsOfDateDesc(customerId);

        if (customerEntity == null)
            throw new Exception("User with id " + customerId + " not found.");

        BeanUtils.copyProperties(customerEntity, customerDto);
        customerDto.setCustomerStartDate(Utils.convertToLocalDateViaInstant(customerEntity.getCustomerStartDate()).atStartOfDay());
        customerDto.setInfoAsOfDate(Utils.convertToLocalDateViaInstant(customerEntity.getInfoAsOfDate()).atStartOfDay());

        return customerDto;
    }

    public CustomerEntity getCustomerById(Integer customerId) throws Exception {
        CustomerEntity customerEntity = customerRepository.findFirstByCustomerIdOrderByInfoAsOfDateDesc(customerId);

        if (customerEntity == null)
            throw new Exception("User with id " + customerId + " not found.");

        return customerEntity;
    }

    public List<CustomerDto> findCustomerByIdAndDate(Integer customerId, Date date) {
        return customerRepository.findCustomerByIdAndDate(customerId, date).stream()
                .map(customerEntity -> {
                    CustomerDto customerDto = new CustomerDto();
                    BeanUtils.copyProperties(customerEntity, customerDto);
                    return customerDto;
                }).collect(Collectors.toList());
    }

    public boolean checkIfRiskHasChanged(Integer customerId) {
        boolean isChanged = false;
        List<CustomerEntity> customerEntities = customerRepository.findTop2ByCustomerIdOrderByInfoAsOfDateDesc(customerId);
        int sizeOfCustomers = customerEntities.size();
        if (sizeOfCustomers < 2 || customerEntities.get(0).getCustomerRiskClass().equals(customerEntities.get(1).getCustomerRiskClass()))
            return isChanged;
        return true;
    }
}
