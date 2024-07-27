package com.example.foreignexchange.provider.exchangerate;

import com.example.foreignexchange.model.response.ExchangeRateApiResponseType;
import com.example.foreignexchange.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateProviderExchangeRateApi extends BaseExchangeRateProvider{
    @Value("${provider.exchange.rate.api.base.url}")
    private String baseUrl;

    @Value("${provider.exchange.rate.api.api.key}")
    private String apiKey;

    private ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateProviderExchangeRateApi(ExchangeRateService exchangeRateService){
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public double getExchangeRate(String fromCurrency, String toCurrency) {
        String url = buildGetExchangeRateUrl(fromCurrency, toCurrency);
        ExchangeRateApiResponseType exchangeRateApiResponseType = this.exchangeRateService.getExchangeRate(url, ExchangeRateApiResponseType.class);
        return exchangeRateApiResponseType.getConversionRate();
    }

    private String buildGetExchangeRateUrl(String fromCurrency, String toCurrency){
        return String.format("%s%s/pair/%s/%s", baseUrl, apiKey, fromCurrency, toCurrency);
    }
}
