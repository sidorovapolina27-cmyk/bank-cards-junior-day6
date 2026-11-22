package com.bank.cards.card;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Банковская карта клиента.
 */
@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Идентификатор клиента-владельца (clients.id). */
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    /** Номер карты (PAN), 16 цифр. */
    @Column(name = "pan", nullable = false, length = 16)
    private String pan;

    /** Держатель карты — так напечатано на пластике. */
    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_system", nullable = false)
    private PaymentSystem paymentSystem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CardStatus status;

    /** Дата начала действия карты. */
    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    /** Дата окончания действия карты. */
    @Column(name = "valid_to", nullable = false)
    private LocalDate validTo;

    /** Маркетинговое название карточного продукта. */
    @Column(name = "product_name")
    private String productName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "card_design", length = 32)
    private String cardDesign;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;
}
