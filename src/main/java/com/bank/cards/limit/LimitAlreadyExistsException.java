package com.bank.cards.limit;

public class LimitAlreadyExistsException extends RuntimeException {

    public LimitAlreadyExistsException(long cardId, LimitType limitType) {
        super("Лимит " + limitType + " для карты " + cardId + " уже задан");
    }
}
