package com.example.foreignexchange.data.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateProviderTest {

    @Test
    void should_return_valid_from_value() {
        assertEquals(ExchangeRateProvider.exchangeRateApi, ExchangeRateProvider.fromValue("ExchangeRateProviderExchangeRateApi"));
        assertEquals(ExchangeRateProvider.frankfurter, ExchangeRateProvider.fromValue("ExchangeRateProviderFrankfurter"));
    }

    @Test
    void should_return_invalid_from_value() {
        assertNull(ExchangeRateProvider.fromValue("InvalidProvider"));
    }

    @Test
    void should_return_value() {
        assertEquals("ExchangeRateProviderExchangeRateApi", ExchangeRateProvider.exchangeRateApi.getExchangeRateProvider());
        assertEquals("ExchangeRateProviderFrankfurter", ExchangeRateProvider.frankfurter.getExchangeRateProvider());
    }
}

