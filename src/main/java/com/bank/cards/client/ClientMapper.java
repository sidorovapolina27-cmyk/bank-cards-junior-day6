package com.bank.cards.client;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Маппер клиентов.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    ClientDto toDto(Client client);
}
