package com.jakub.recruitment.customerrisknotifier.service;

import com.jakub.recruitment.customerrisknotifier.model.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationsService {
    @Value("${email.address.directors}")
    private String emailAddressToDirectors;
    @Value("${email.address.coordinators}")
    private String emailAddressToCoordinators;
    @Value("${email.message.typeA5-type1}")
    private String emailMessageTypeA5AndType1;
    @Value("${email.message.type2}")
    private String emailMessageType2;

    private final CustomerRiskService customerAndNoteService;
    private final EmailService emailService;

    public void sendRiskClassChangeNotification(Integer customerId) throws Exception {
        CustomerEntity customer = customerAndNoteService.getCustomerById(customerId);
        if (customerAndNoteService.checkIfRiskHasChanged(customerId)) {
            if (customer.getCustomerType().equals("TYPE_1") || customer.getCustomerType().equals("TYPE_A5")) {
                sendEmailToDirectors(customerId);
            } else if (customer.getCustomerType().equals("TYPE_2") && customer.getCustomerBusinessType().equals("BR2")) {
                sendEmailToCoordinator(customerId);
            }
        }
    }

    private void sendEmailToDirectors(Integer customerId) {
        String emailContent = emailMessageTypeA5AndType1 + " " + customerId;
        emailService.sendEmail(emailAddressToDirectors, emailContent);
        emailService.sendEmail(emailAddressToCoordinators, emailContent);
    }

    private void sendEmailToCoordinator(Integer customerId) {
        String emailContent = emailMessageType2 + " " + customerId;
        emailService.sendEmail(emailAddressToCoordinators, emailContent);
    }
}
