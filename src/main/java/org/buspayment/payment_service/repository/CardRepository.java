package org.buspayment.payment_service.repository;

import org.buspayment.payment_service.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardNumberAndPinCode(String cardNumber, String pinCode);
}
