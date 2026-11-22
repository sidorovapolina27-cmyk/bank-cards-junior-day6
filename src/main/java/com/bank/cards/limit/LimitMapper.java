package com.bank.cards.limit;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LimitMapper {

    LimitDto toDto(CardLimit limit);

    List<LimitDto> toDtoList(List<CardLimit> limits);
}
