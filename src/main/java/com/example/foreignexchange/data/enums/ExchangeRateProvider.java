package com.example.foreignexchange.data.enums;

@SuppressWarnings("java:S115")
public enum ExchangeRateProvider {
    exchangeRateApi("ExchangeRateProviderExchangeRateApi"),
    frankfurter("ExchangeRateProviderFrankfurter");

    private final String value;

    ExchangeRateProvider(String value) { this.value = value; }

    public static ExchangeRateProvider fromValue(String value) {
        for (ExchangeRateProvider exchangeRateProvider : ExchangeRateProvider.values()) {
            if (exchangeRateProvider.getExchangeRateProvider().equalsIgnoreCase(value)) {
                return exchangeRateProvider;
            }
        }
        return null;
    }

    public String getExchangeRateProvider() {
        return value;
    }
}
