package com.nexastay.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String name) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(to);
            helper.setSubject("Welcome to NexaStay!");
            
            String htmlContent = generateWelcomeEmailContent(name);
            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            log.info("Welcome email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send welcome email to: {}", to, e);
            throw new RuntimeException("Failed to send welcome email", e);
        }
    }

    private String generateWelcomeEmailContent(String name) {
        StringBuilder content = new StringBuilder();
        content.append("<html><body>");
        content.append("<h2>Welcome to NexaStay, ").append(name).append("!</h2>");
        content.append("<p>Thank you for joining NexaStay. We're excited to have you on board!</p>");
        content.append("<p>With your new account, you can:</p>");
        content.append("<ul>");
        content.append("<li>Browse and book rooms</li>");
        content.append("<li>Manage your reservations</li>");
        content.append("<li>Write reviews</li>");
        content.append("<li>And much more!</li>");
        content.append("</ul>");
        content.append("<p>If you have any questions, feel free to contact our support team.</p>");
        content.append("<p>Best regards,<br>The NexaStay Team</p>");
        content.append("</body></html>");
        return content.toString();
    }
} 