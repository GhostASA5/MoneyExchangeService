package ru.skillbox.currency.exchange.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skillbox.currency.exchange.dto.CurrencyList;
import ru.skillbox.currency.exchange.dto.CurrencyXML;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Service
public class XMLParser {

    @Value("${spring.my.api.url}")
    private String url;
    private final CurrencyMapper mapper;
    private final CurrencyRepository repository;

    @Autowired
    public XMLParser(CurrencyMapper mapper, CurrencyRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public void updateDB(){
        try {
            Connection connection = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("https://www.google.com")
                    .ignoreContentType(true);
            String xml = connection.get().outerHtml();

            JAXBContext jaxbContext = JAXBContext.newInstance(CurrencyList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(xml);
            CurrencyList currencyList = (CurrencyList) unmarshaller.unmarshal(reader);

            currencyList.getCurrencyXMLS().forEach(this::updateCurrency);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void updateCurrency(CurrencyXML currencyXML){
        currencyXML.setId(null);
        currencyXML.setDoubleValue(currencyXML.getValueAsDouble());
        Currency currency = repository.findByIsoCharCode(currencyXML.getIsoCharCode());
        if (currency == null){
            repository.save(mapper.toEntity(currencyXML));
        } else {
            currency.setValue(currencyXML.getDoubleValue());
            currency.setNominal(currencyXML.getNominal());
            repository.save(currency);
        }
    }
}
