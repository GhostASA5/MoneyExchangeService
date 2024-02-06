package ru.skillbox.currency.exchange.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyXML {

    @XmlAttribute(name = "ID")
    private String id;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Nominal")
    private Long nominal;

    @XmlElement(name = "Value")
    private String value;

    @XmlElement(name = "NumCode")
    private Long isoNumCode;

    @XmlElement(name = "CharCode")
    private String isoCharCode;

    private Double doubleValue;

    public Double getValueAsDouble() {
        return Double.parseDouble(value.replace(',', '.'));
    }
}

