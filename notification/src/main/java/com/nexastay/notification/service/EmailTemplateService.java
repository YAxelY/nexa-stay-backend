package com.nexastay.notification.service;

import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {

    public String getRegistrationEmailTemplate(String userName) {
        return String.format("""
                Dear %s,

                Welcome to NexaStay! We're thrilled to have you as a member of our community.

                Your registration has been successfully completed. You can now access all our services and features:
                - Browse and book rooms
                - Manage your reservations
                - Access exclusive offers
                - And much more!

                If you have any questions or need assistance, don't hesitate to contact our support team.

                Best regards,
                The NexaStay Team
                """, userName);
    }

    public String getRegistrationEmailSubject() {
        return "Welcome to NexaStay - Registration Successful!";
    }
}