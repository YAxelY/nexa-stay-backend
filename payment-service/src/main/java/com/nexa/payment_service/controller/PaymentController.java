package com.nexa.payment_service.controller;

import com.nexa.payment_service.dto.PaymentRequest;
import com.nexa.payment_service.dto.PaymentResponse;
import com.nexa.payment_service.entity.Payment;
import com.nexa.payment_service.service.PaymentService;
import com.nexa.payment_service.service.PayPalService;
import com.nexa.payment_service.service.StripeService;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final StripeService stripeService;
    private final PayPalService paypalService;
    private final PaymentService paymentService;

    @PostMapping("/stripe/create-intent")
    public ResponseEntity<PaymentResponse> createStripePayment(@RequestBody PaymentRequest request) {
        PaymentIntent intent = stripeService.createPaymentIntent(request);
        paymentService.savePayment(request, intent.getId(), intent.getStatus());
        return ResponseEntity.ok(PaymentResponse.builder().clientSecret(intent.getClientSecret()).build());
    }

    @PostMapping("/paypal/create-order")
    public ResponseEntity<PaymentResponse> createPayPalOrder(@RequestBody PaymentRequest request) {
        String approvalLink = paypalService.createPayPalOrder(request);
        paymentService.savePayment(request, "PAYPAL_ORDER_ID", "CREATED");
        return ResponseEntity.ok(PaymentResponse.builder().approvalLink(approvalLink).build());
    }
}
