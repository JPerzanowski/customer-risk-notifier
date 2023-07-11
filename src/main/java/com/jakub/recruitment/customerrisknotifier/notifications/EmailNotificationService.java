package com.jakub.recruitment.customerrisknotifier.notifications;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailNotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailNotification(String recipient, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(body, true);

            javaMailSender.send(message);

            System.out.println("Wysłano wiadomość email do: " + recipient);
        } catch (Exception e) {
            System.err.println("Wystąpił błąd podczas wysyłania wiadomości email: " + e.getMessage());
        }
    }
}
