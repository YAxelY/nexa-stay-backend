package com.nexa.payment_service.service;

import com.nexa.payment_service.dto.PaymentRequest;
import com.nexa.payment_service.exception.PaymentException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.api-key}")
    private String stripeKey;

    public PaymentIntent createPaymentIntent(PaymentRequest request) {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int)(request.getAmount() * 100));
        params.put("currency", request.getCurrency());
        params.put("payment_method_types", java.util.List.of("card"));

        try {
            return PaymentIntent.create(params);
        } catch (StripeException e) {
            throw new PaymentException("Stripe error: " + e.getMessage());
        }
    }
}
