package com.example.foreignexchange.provider.exchangerate;

import com.example.foreignexchange.model.response.FrankfurterResponseType;
import com.example.foreignexchange.service.ExchangeRateService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExchangeRateProviderFrankfurterTest {
    private static Faker faker;
    @InjectMocks
    private ExchangeRateProviderFrankfurter exchangeRateProvider;
    @Mock
    private ExchangeRateService exchangeRateService;

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
        String url = buildGetExchangeRateUrl(fromCurrency, toCurrency);

        HashMap<String, Double> expectedRates = new HashMap<String, Double>();
        expectedRates.put(toCurrency, faker.number().randomDouble(2,10,100));

        FrankfurterResponseType expected = FrankfurterResponseType.builder()
                .rates(expectedRates)
                .build();

        when(exchangeRateService.getExchangeRate(url, FrankfurterResponseType.class)).thenReturn(expected);
        ReflectionTestUtils.setField(exchangeRateProvider, "baseUrl", "https://www.example.com/api");

        // Act
        double conversionRate = exchangeRateProvider.getExchangeRate(fromCurrency, toCurrency);

        // Assert
        assertEquals(conversionRate, expected.getRates().get(toCurrency));
    }

    private String buildGetExchangeRateUrl(String fromCurrency, String toCurrency) {
        return UriComponentsBuilder.fromHttpUrl("https://www.example.com/api")
                .queryParam("from", fromCurrency)
                .queryParam("to", toCurrency)
                .toUriString();
    }
}
