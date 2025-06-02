package com.nexastay.notification.service;

import com.nexastay.notification.model.Notification;
import com.nexastay.notification.model.NotificationStatus;
import com.nexastay.notification.model.NotificationType;
import com.nexastay.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private final EmailTemplateService emailTemplateService;

    public Notification sendNotification(Notification notification) {
        try {
            if (notification.getType().name().equals("EMAIL")) {
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(notification.getRecipientEmail());
                mail.setSubject(notification.getSubject());
                mail.setText(notification.getMessage());
                mailSender.send(mail);
            }
            notification.setStatus(NotificationStatus.SENT);
        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
        }

        return notificationRepository.save(notification);
    }

    public Notification sendRegistrationEmail(String userEmail, String userName) {
        try {
            // Create notification entity
            Notification notification = Notification.builder()
                    .recipientEmail(userEmail)
                    .subject(emailTemplateService.getRegistrationEmailSubject())
                    .message(emailTemplateService.getRegistrationEmailTemplate(userName))
                    .type(NotificationType.EMAIL)
                    .status(NotificationStatus.PENDING)
                    .build();

            // Send the email
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(userEmail);
            mail.setSubject(notification.getSubject());
            mail.setText(notification.getMessage());
            mailSender.send(mail);

            // Update status and save
            notification.setStatus(NotificationStatus.SENT);
            return notificationRepository.save(notification);
        } catch (Exception e) {
            Notification failedNotification = Notification.builder()
                    .recipientEmail(userEmail)
                    .subject("Registration Email")
                    .message("Failed to send registration email")
                    .type(NotificationType.EMAIL)
                    .status(NotificationStatus.FAILED)
                    .build();
            return notificationRepository.save(failedNotification);
        }
    }
}
