package com.example.foreignexchange.service.factory;

import com.example.foreignexchange.provider.exchangerate.BaseExchangeRateProvider;

public class MockExchangeRateProviderExistingInIoc extends BaseExchangeRateProvider {
    @Override
    public double getExchangeRate(String fromCurrency, String toCurrency) {
        return 0;
    }
}
