package com.example.foreignexchange.component.impl;

import com.example.foreignexchange.component.ExchangeRateManager;
import com.example.foreignexchange.data.enums.ExchangeRateProvider;
import com.example.foreignexchange.provider.exchangerate.BaseExchangeRateProvider;
import com.example.foreignexchange.service.factory.ExchangeRateProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateManagerImpl implements ExchangeRateManager {
    @Value("${exchange.rate.provider}")
    private String exchangeRateProvider;

    private ExchangeRateProviderFactory exchangeRateProviderFactory;

    @Autowired
    public ExchangeRateManagerImpl(ExchangeRateProviderFactory exchangeRateProviderFactory){
        this.exchangeRateProviderFactory = exchangeRateProviderFactory;
    }

    @Override
    @Cacheable(value = "exchangeRates", key = "{#fromCurrency, #toCurrency}")
    public double getExchangeRate(String fromCurrency, String toCurrency) {
        BaseExchangeRateProvider provider = getProvider();
        return provider.getExchangeRate(fromCurrency, toCurrency);
    }

    @Override
    @Cacheable(value = "exchangeRates", key = "{#amount, #fromCurrency, #toCurrency}")
    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        double rate = this.getExchangeRate(fromCurrency, toCurrency);
        return amount * rate;
    }

    private BaseExchangeRateProvider getProvider(){
        ExchangeRateProvider exchangeRateProviderEnum = ExchangeRateProvider.fromValue(exchangeRateProvider);
        return exchangeRateProviderFactory.getService(exchangeRateProviderEnum);
    }
}
