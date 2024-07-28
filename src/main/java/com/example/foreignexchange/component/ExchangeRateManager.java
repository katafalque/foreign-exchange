package com.example.foreignexchange.component;

public interface ExchangeRateManager {
    double getExchangeRate(String fromCurrency, String toCurrency);
}
