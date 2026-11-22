package com.bank.cards.card;

import com.bank.cards.client.Client;
import com.bank.cards.client.ClientDto;
import com.bank.cards.client.ClientMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Маппер карт (MapStruct).
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ClientMapper.class)
public interface CardMapper {

    @Mapping(target = "createdDate", expression = "java(card.getCreatedAt() == null ? null : card.getCreatedAt().toLocalDate())")
    CardDto toDto(Card card);

    List<CardDto> toDtoList(List<Card> cards);

    @Mapping(target = "id", source = "card.id")
    @Mapping(target = "client", source = "client")
    CardDetailsDto toDetailsDto(Card card, Client client);

    @Mapping(target = "maskedPan", expression = "java(\"**** \" + card.getPan().substring(card.getPan().length() - 4))")
    @Mapping(target = "expired", expression = "java(card.getValidTo().isBefore(java.time.LocalDate.now()))")
    @Mapping(target = "daysToExpiry", expression = "java(java.time.temporal.ChronoUnit.DAYS.between(java.time.LocalDate.now(), card.getValidTo()))")
    @Mapping(target = "statusDisplayName", source = "status", qualifiedByName = "statusDisplayName")
    @Mapping(target = "validFromText", source = "validFrom", qualifiedByName = "validFromText")
    CardListItemDto toListItemDto(Card card);

    List<CardListItemDto> toListItemDtoList(List<Card> cards);

    @Mapping(target = "maskedPan", expression = "java(\"**** \" + card.getPan().substring(card.getPan().length() - 4))")
    @Mapping(target = "statusDisplayName", source = "status", qualifiedByName = "statusDisplayName")
    CardShortDto toShortDto(Card card);

    List<CardShortDto> toShortDtoList(List<Card> cards);

    @Mapping(target = "internalRiskScore", expression = "java(Math.abs(card.getPan().hashCode()) % 100)")
    CardExportDto toExportDto(Card card);

    @Named("statusDisplayName")
    default String statusDisplayName(CardStatus status) {
        if (status == null) {
            return "";
        }
        return switch (status) {
            case NEW -> "Новая";
            case ACTIVE -> "Активна";
            case BLOCKED -> "Заблокирована";
            case EXPIRED -> "Истекла";
            case CLOSED -> "Закрыта";
        };
    }

    @Named("validFromText")
    default String validFromText(LocalDate validFrom) {
        return validFrom == null ? "" : validFrom.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
