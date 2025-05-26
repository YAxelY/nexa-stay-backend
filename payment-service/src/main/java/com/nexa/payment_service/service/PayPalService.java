package com.nexa.payment_service.service;

import com.nexa.payment_service.dto.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PayPalService {

    public String createPayPalOrder(PaymentRequest request) {
        // Simplified fake link for demo (replace with real PayPal SDK later)
        return "https://www.sandbox.paypal.com/checkoutnow?token=EXAMPLE";
    }
}
