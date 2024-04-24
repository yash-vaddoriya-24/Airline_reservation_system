package org.example.ar.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JavaSmtpGmailSenderApplication {
    @Autowired
    private JavaSmtpGmailSenderService senderService;

    public static void main(String[] args) {
        SpringApplication.run(JavaSmtpGmailSenderApplication.class, args);
    }


    public void sendMail(String email, String subject, String body) {

        // Send the email
        senderService.sendEmail(email, subject, body);
    }
}

