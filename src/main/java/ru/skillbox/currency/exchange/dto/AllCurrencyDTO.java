package ru.skillbox.currency.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllCurrencyDTO {

    private String name;

    private Double value;
}
