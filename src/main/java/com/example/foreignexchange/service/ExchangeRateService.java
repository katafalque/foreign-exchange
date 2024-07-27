package com.example.foreignexchange.service;

public interface ExchangeRateService {
    <T> T getExchangeRate(String url, Class<T> responseType);
}
