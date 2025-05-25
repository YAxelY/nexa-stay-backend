package com.nexastay.notification.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "notifications")
@Data
// @NoArgsConstructor
@AllArgsConstructor
@Builder

public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_email")
    private String recipientEmail;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private LocalDateTime sentAt;

    public Notification() {}

    public Notification(String recipient, String subject, String message, NotificationType type) {
        this.recipientEmail = recipient;
        this.subject = subject;
        this.message = message;
        this.type = type;
        this.status = NotificationStatus.PENDING;
        this.sentAt = LocalDateTime.now();
    }

    // Getters and Setters
    // Lombok's data integrated
}

