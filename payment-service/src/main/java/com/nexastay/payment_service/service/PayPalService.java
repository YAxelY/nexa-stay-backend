package com.nexa.payment_service.service;

import com.nexa.payment_service.dto.PaymentRequest;
import com.nexa.payment_service.exception.PaymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayPalService {

    @Value("${paypal.client-id}")
    private String clientId;
    
    @Value("${paypal.client-secret}")
    private String clientSecret;
    
    private final String paypalBaseUrl = "https://api-m.sandbox.paypal.com";
    private final RestTemplate restTemplate = new RestTemplate();

    public String createPayPalOrder(PaymentRequest request) {
        String accessToken = getPayPalAccessToken();
        return createOrder(accessToken, request);
    }

    private String getPayPalAccessToken() {
        String auth = clientId + ":" + clientSecret;
        String base64Auth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + base64Auth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=client_credentials";

        ResponseEntity<Map> response = restTemplate.postForEntity(
            paypalBaseUrl + "/v1/oauth2/token",
            new HttpEntity<>(body, headers),
            Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        }
        throw new PaymentException("Failed to get PayPal access token");
    }

    private String createOrder(String accessToken, PaymentRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("intent", "CAPTURE");
        
        Map<String, Object> purchaseUnit = new HashMap<>();
        purchaseUnit.put("amount", Map.of(
            "currency_code", request.getCurrency(),
            "value", request.getAmount()
        ));
        
        orderRequest.put("purchase_units", new Map[] { purchaseUnit });

        ResponseEntity<Map> response = restTemplate.postForEntity(
            paypalBaseUrl + "/v2/checkout/orders",
            new HttpEntity<>(orderRequest, headers),
            Map.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            Map<String, Object> responseBody = response.getBody();
            String orderId = (String) responseBody.get("id");
            
            // Save the PayPal order ID in your database
            paymentService.savePayment(request, orderId, "CREATED");
            
            // Extract approval link from response
            return ((Map<String, Map<String, String>>) responseBody.get("links")).values().stream()
                .filter(link -> "approve".equals(link.get("rel")))
                .findFirst()
                .orElseThrow(() -> new PaymentException("No approval link found"))
                .get("href");
        }
        throw new PaymentException("Failed to create PayPal order");
    }
}