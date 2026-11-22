package com.bank.cards.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCardIdOrderByOccurredAtDescIdDesc(Long cardId);

    Page<Transaction> findByCardId(Long cardId, Pageable pageable);

    List<Transaction> findByCardIdAndOccurredAtBetweenOrderByOccurredAtDescIdDesc(
            Long cardId, LocalDateTime from, LocalDateTime to);
}
