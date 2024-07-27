package com.example.foreignexchange.provider.exchangerate;

import com.example.foreignexchange.model.response.ExchangeRateApiResponseType;
import com.example.foreignexchange.service.ExchangeRateService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExchangeRateProviderExchangeRateApiTest {
    private static Faker faker;
    @InjectMocks
    private ExchangeRateProviderExchangeRateApi exchangeRateProvider;

    @Mock
    private ExchangeRateService exchangeRateService;

    @Value("${provider.exchange.rate.api.base.url}")
    private String baseUrl;

    @Value("${provider.exchange.rate.api.api.key}")
    private String apiKey;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    void should_get_exchange_rate() {
        // Arrange
        String fromCurrency = faker.currency().code();
        String toCurrency = faker.currency().code();
        String url = String.format("%s%s/pair/%s/%s", baseUrl, apiKey, fromCurrency, toCurrency);

        System.out.println(url);

        ExchangeRateApiResponseType expected = ExchangeRateApiResponseType.builder()
                .conversionRate(faker.number().randomDouble(2,10,100))
                .result(Boolean.toString(true))
                .build();

        when(exchangeRateService.getExchangeRate(url, ExchangeRateApiResponseType.class)).thenReturn(expected);

        // Act
        double conversionRate = exchangeRateProvider.getExchangeRate(fromCurrency, toCurrency);

        // Assert
        assertEquals(conversionRate, expected.getConversionRate());
    }

    @Test
    void should_throw_exception() {
        // Arrange
        String fromCurrency = faker.currency().code();
        String toCurrency = faker.currency().code();
        String url = String.format("%s%s/pair/%s/%s", baseUrl, apiKey, fromCurrency, toCurrency);

        when(exchangeRateService.getExchangeRate(url, ExchangeRateApiResponseType.class))
                .thenThrow(new RuntimeException("API Error"));

        // Act & Assert
        assertThatThrownBy(() -> exchangeRateProvider.getExchangeRate(fromCurrency, toCurrency))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("API Error");
    }
}
