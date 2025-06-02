// dto/PaymentResponse.java
package com.nexa.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String clientSecret;
     private String paymentIntentId; 
    private String approvalLink;
}