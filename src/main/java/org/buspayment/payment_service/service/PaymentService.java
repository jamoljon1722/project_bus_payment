package org.buspayment.payment_service.service;

import org.buspayment.payment_service.entities.Card;
import org.buspayment.payment_service.entities.Payment;
import org.buspayment.payment_service.repository.CardRepository;
import org.buspayment.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    private final CardRepository cardRepository;
    private final PaymentRepository paymentRepository;
    private final TelegramBotService telegramBotService;

    private static final Double FARE = 1700.0;

    public PaymentService(CardRepository cardRepository, PaymentRepository paymentRepository, TelegramBotService telegramBotService) {
        this.cardRepository = cardRepository;
        this.paymentRepository = paymentRepository;
        this.telegramBotService = telegramBotService;
    }

    public String processPayment(String cardNumber, String pinCode) {
        Optional<Card> cardOptional = cardRepository.findByCardNumberAndPinCode(cardNumber, pinCode);
        if (cardOptional.isEmpty()) {
            return "Xato! Karta yoki PIN noto'g'ri";
        }

        Card card = cardOptional.get();
        if (card.getBalance() < FARE) {
            return "Xato! balans yetarli emas";
        }
        card.setBalance(card.getBalance() - FARE);
        cardRepository.save(card);

        Payment payment = new Payment();
        payment.setCardNumber(card.getCardNumber());
        payment.setOwnerName(card.getOwnerName());
        paymentRepository.save(payment);

        telegramBotService.sendPaymentNotification(payment);
        return "SUCCESS";
    }
}
