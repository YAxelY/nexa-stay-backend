// controller/PaymentController.java
package com.nexastay.payment_service.controller;

import com.nexastay.payment_service.dto.PaymentRequest;
import com.nexastay.payment_service.dto.PaymentResponse;
import com.nexastay.payment_service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final StripeService stripeService;
    private final PayPalService paypalService;
    private final PaymentService paymentService;

    @PostMapping("/stripe/create-intent")
    public ResponseEntity<PaymentResponse> createStripePayment(@RequestBody PaymentRequest request) {
        var intent = stripeService.createPaymentIntent(request);
        paymentService.savePayment(request, intent.getId(), intent.getStatus());
        return ResponseEntity.ok(PaymentResponse.builder()
                .clientSecret(intent.getClientSecret())
                .paymentIntentId(intent.getId())
                .build());
    }

    @PostMapping("/paypal/create-order")
    public ResponseEntity<PaymentResponse> createPayPalOrder(@RequestBody PaymentRequest request) {
        String approvalLink = paypalService.createPayPalOrder(request);
        return ResponseEntity.ok(PaymentResponse.builder()
                .approvalLink(approvalLink)
                .build());
    }

    @PostMapping("/stripe/confirm-intent")
    public ResponseEntity<?> confirmStripePayment(@RequestParam String paymentIntentId) {
        var confirmedIntent = stripeService.confirmPaymentIntent(paymentIntentId);
        return ResponseEntity.ok(Map.of(
            "id", confirmedIntent.getId(),
            "status", confirmedIntent.getStatus()
        ));
    }
}