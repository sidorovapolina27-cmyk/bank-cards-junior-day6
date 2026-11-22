package com.bank.cards.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByClientIdOrderByIdAsc(Long clientId);

    List<Card> findByClientIdAndStatusOrderByIdAsc(Long clientId, CardStatus status);

    List<Card> findAllByOrderByCreatedAtDescIdDesc();

    /**
     * Карты клиента, истекающие в течение заданного периода,
     * включая «живые» статусы (NEW, ACTIVE, BLOCKED), по возрастанию validTo.
     */
    @Query("""
            select c from Card c
            where c.clientId = :clientId
              and c.validTo between :today and :limitDate
              and c.status in (
                  com.bank.cards.card.CardStatus.NEW,
                  com.bank.cards.card.CardStatus.ACTIVE,
                  com.bank.cards.card.CardStatus.BLOCKED)
            order by c.validTo asc
            """)
    List<Card> findExpiringCards(@Param("clientId") long clientId,
                                 @Param("today") LocalDate today,
                                 @Param("limitDate") LocalDate limitDate);
}
