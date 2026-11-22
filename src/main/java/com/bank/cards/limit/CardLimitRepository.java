package com.bank.cards.limit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardLimitRepository extends JpaRepository<CardLimit, Long> {

    List<CardLimit> findByCardIdOrderByIdAsc(Long cardId);

    boolean existsByCardIdAndLimitType(Long cardId, LimitType limitType);
}
