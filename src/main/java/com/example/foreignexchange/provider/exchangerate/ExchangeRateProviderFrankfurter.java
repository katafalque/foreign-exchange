package com.example.foreignexchange.provider.exchangerate;

import com.example.foreignexchange.model.response.FrankfurterResponseType;
import com.example.foreignexchange.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ExchangeRateProviderFrankfurter extends BaseExchangeRateProvider{

    private ExchangeRateService exchangeRateService;

    @Value("${provider.frankfurter.base.url}")
    private String baseUrl;

    @Autowired
    public ExchangeRateProviderFrankfurter(ExchangeRateService exchangeRateService){
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public double getExchangeRate(String fromCurrency, String toCurrency) {
        String url = buildGetExchangeRateUrl(fromCurrency, toCurrency);
        FrankfurterResponseType frankfurterResponseType = this.exchangeRateService.getExchangeRate(url, FrankfurterResponseType.class);
        return frankfurterResponseType.getRates().get(toCurrency);
    }

    private String buildGetExchangeRateUrl(String fromCurrency, String toCurrency) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("from", fromCurrency)
                .queryParam("to", toCurrency)
                .toUriString();
    }
}
