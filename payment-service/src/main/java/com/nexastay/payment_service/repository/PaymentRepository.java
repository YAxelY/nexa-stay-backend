// repository/PaymentRepository.java
package com.nexa.payment_service.repository;

import com.nexa.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}