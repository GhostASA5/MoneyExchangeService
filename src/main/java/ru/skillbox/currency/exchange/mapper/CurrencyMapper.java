package ru.skillbox.currency.exchange.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.currency.exchange.dto.AllCurrencyDTO;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.CurrencyXML;
import ru.skillbox.currency.exchange.entity.Currency;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDto convertToDto(Currency currency);

    Currency convertToEntity(CurrencyDto currencyDto);

    AllCurrencyDTO convertToAllCurrencyDto(Currency currency);

    @Mapping(target = "value", source = "currencyXML.doubleValue")
    Currency toEntity(CurrencyXML currencyXML);
}
