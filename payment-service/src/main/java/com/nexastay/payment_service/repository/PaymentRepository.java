// repository/PaymentRepository.java
package com.nexastay.payment_service.repository;

import com.nexastay.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}