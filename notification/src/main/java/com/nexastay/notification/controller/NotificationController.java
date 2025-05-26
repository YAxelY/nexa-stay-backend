package com.nexastay.notification.controller;

import com.nexastay.notification.model.Notification;
import com.nexastay.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final JavaMailSender mailSender;
    private final NotificationRepository notificationRepository;

    // Endpoint pour envoyer un e-mail manuellement (ex: via POSTMAN)
    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String message) {

        try {
            // 1. Envoyer l'e-mail
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailSender.send(mailMessage);

            // 2. Sauvegarder la notification dans la base de données
            Notification notification = new Notification();
            notification.setRecipientEmail(to);
            notification.setMessage(message);
            notification.setSentAt(LocalDateTime.now());
            notificationRepository.save(notification);

            return ResponseEntity.ok("E-mail envoyé avec succès à " + to);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur d'envoi : " + e.getMessage());
        }
    }

    // Récupérer toutes les notifications
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Récupérer les notifications par destinataire
    @GetMapping("/search")
    public List<Notification> getNotificationsByEmail(@RequestParam String email) {
        return notificationRepository.findByRecipientEmail(email);
    }
}
