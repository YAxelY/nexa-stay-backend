package com.nexastay.notification.service;

import com.nexastay.notification.model.Notification;
import com.nexastay.notification.model.NotificationStatus;
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
}
