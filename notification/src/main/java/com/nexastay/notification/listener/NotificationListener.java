package com.nexastay.notification.rabbit;

import com.nexastay.notification.model.Notification;
import com.nexastay.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "notification.queue")
    public void receiveNotification(Notification notification) {
        notificationService.sendNotification(notification);
    }
}

