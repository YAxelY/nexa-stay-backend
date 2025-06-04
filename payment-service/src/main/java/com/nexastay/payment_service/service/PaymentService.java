// service/PaymentService.java
package com.nexastay.payment_service.service;

import com.nexastay.payment_service.dto.PaymentRequest;
import com.nexastay.payment_service.entity.Payment;
import com.nexastay.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment savePayment(PaymentRequest request, String transactionId, String status) {
        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .paymentMethod(request.getPaymentMethod())
                .transactionId(transactionId)
                .status(status)
                .createdAt(LocalDateTime.now())
                .build();
        return paymentRepository.save(payment);
    }
}
