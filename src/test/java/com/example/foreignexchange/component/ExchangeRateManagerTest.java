package com.example.foreignexchange.component;

import com.example.foreignexchange.component.impl.ExchangeRateManagerImpl;
import com.example.foreignexchange.provider.exchangerate.BaseExchangeRateProvider;
import com.example.foreignexchange.service.factory.ExchangeRateProviderFactory;
import com.example.foreignexchange.data.enums.ExchangeRateProvider;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExchangeRateManagerTest {
    private static Faker faker;
    @Mock
    private ExchangeRateProviderFactory exchangeRateProviderFactory;

    @Mock
    private BaseExchangeRateProvider mockExchangeRateProvider;

    @InjectMocks
    private ExchangeRateManagerImpl exchangeRateManager;

    @Value("${exchange.rate.provider}")
    private String exchangeRateProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exchangeRateManager = new ExchangeRateManagerImpl(exchangeRateProviderFactory);
        faker = new Faker();
    }

    @Test
    void should_get_exchange_rate_frankfurter() {
        // Arrange
        String fromCurrency = faker.currency().code();
        String toCurrency = faker.currency().code();
        double expectedRate = faker.number().randomDouble(2,10,100);

        // Act

        ReflectionTestUtils.setField(exchangeRateManager, "exchangeRateProvider", "ExchangeRateProviderFrankfurter");
        when(exchangeRateProviderFactory.getService(ExchangeRateProvider.frankfurter))
                .thenReturn(mockExchangeRateProvider);
        when(mockExchangeRateProvider.getExchangeRate(fromCurrency, toCurrency))
                .thenReturn(expectedRate);


        double actualRate = exchangeRateManager.getExchangeRate(fromCurrency, toCurrency);

        // Assert
        assertEquals(expectedRate, actualRate);
    }

    @Test
    void should_get_exchange_rate_exchange_rate_api() {
        // Arrange
        String fromCurrency = faker.currency().code();
        String toCurrency = faker.currency().code();
        double expectedRate = faker.number().randomDouble(2,10,100);

        // Act

        ReflectionTestUtils.setField(exchangeRateManager, "exchangeRateProvider", "ExchangeRateProviderExchangeRateApi");
        when(exchangeRateProviderFactory.getService(ExchangeRateProvider.exchangeRateApi))
                .thenReturn(mockExchangeRateProvider);
        when(mockExchangeRateProvider.getExchangeRate(fromCurrency, toCurrency))
                .thenReturn(expectedRate);


        double actualRate = exchangeRateManager.getExchangeRate(fromCurrency, toCurrency);

        // Assert
        assertEquals(expectedRate, actualRate);
    }

}
