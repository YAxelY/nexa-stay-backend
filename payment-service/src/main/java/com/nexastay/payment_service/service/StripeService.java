
// service/StripeService.java
package com.nexastay.payment_service.service;

import com.nexastay.payment_service.dto.PaymentRequest;
import com.nexastay.payment_service.exception.PaymentException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {
    @Value("${stripe.api-key}") private String stripeKey;

    public PaymentIntent createPaymentIntent(PaymentRequest request) {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int) (request.getAmount() * 100));
        params.put("currency", request.getCurrency());
        params.put("payment_method_types", java.util.List.of("card"));
        
        try {
            return PaymentIntent.create(params);
        } catch (StripeException e) {
            throw new PaymentException("Stripe error: " + e.getMessage());
        }
    }

    public PaymentIntent confirmPaymentIntent(String paymentIntentId) {
        Stripe.apiKey = stripeKey;
        try {
            PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
            return intent.confirm();
        } catch (StripeException e) {
            throw new PaymentException("Stripe confirmation error: " + e.getMessage());
        }
    }
}
