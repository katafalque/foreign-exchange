package com.example.foreignexchange.component;

public interface ExchangeRateManager {
    double getExchangeRate(String fromCurrency, String toCurrency);

    double convertCurrency(double amount, String fromCurrency, String toCurrency);
}
