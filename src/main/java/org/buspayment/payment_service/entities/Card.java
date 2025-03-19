package org.buspayment.payment_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cards")
public class Card {
    @Id
    private String cardNumber;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String pinCode;
    //---------\\
    private Double balance;
}
