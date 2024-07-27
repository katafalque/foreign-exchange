package com.example.foreignexchange.provider.exchangerate;

public abstract class BaseExchangeRateProvider {
    public abstract double getExchangeRate(String fromCurrency, String toCurrency);
}
