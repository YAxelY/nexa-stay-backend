package com.nexastay.notification.controller;

import com.nexastay.notification.model.Notification;
import com.nexastay.notification.repository.NotificationRepository;
import com.nexastay.notification.service.NotificationService;
import com.nexastay.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class NotificationController {

    private final JavaMailSender mailSender;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final EmailService emailService;

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

    @PostMapping("/registration-email")
    public ResponseEntity<Notification> sendRegistrationEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String name = request.get("name");

        if (email == null || name == null) {
            return ResponseEntity.badRequest().build();
        }

        Notification notification = notificationService.sendRegistrationEmail(email, name);
        return ResponseEntity.ok(notification);
    }

    @PostMapping("/welcome-email")
    public ResponseEntity<String> sendWelcomeEmail(@RequestBody WelcomeEmailRequest request) {
        try {
            log.info("Received welcome email request for: {}", request.getEmail());
            emailService.sendWelcomeEmail(request.getEmail(), request.getName());
            return ResponseEntity.ok("Welcome email sent successfully");
        } catch (Exception e) {
            log.error("Failed to send welcome email", e);
            return ResponseEntity.internalServerError().body("Failed to send welcome email");
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

class WelcomeEmailRequest {
    private String email;
    private String name;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
