package com.bank.cards.client;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Клиент для отображения внутри карточных дто.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ClientDto {

    private final Long id;
    private final String fullName;
    private final String phoneNumber;
    private final String email;
}
