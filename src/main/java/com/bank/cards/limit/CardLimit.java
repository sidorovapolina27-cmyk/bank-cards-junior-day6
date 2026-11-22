package com.bank.cards.limit;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "card_limits",
        uniqueConstraints = @UniqueConstraint(columnNames = {"card_id", "limit_type"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_id", nullable = false)
    private Long cardId;

    @Enumerated(EnumType.STRING)
    @Column(name = "limit_type", nullable = false)
    private LimitType limitType;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
}
