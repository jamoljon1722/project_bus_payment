package org.buspayment.payment_service.repository;

import org.buspayment.payment_service.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
