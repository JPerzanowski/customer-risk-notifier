package com.jakub.recruitment.customerrisknotifier.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(String recipient, String content) {
        // zamockowana funkcjonalność
        System.out.println("Sending email to: " + recipient);
        System.out.println("Email content:");
        System.out.println(content);
    }
}
