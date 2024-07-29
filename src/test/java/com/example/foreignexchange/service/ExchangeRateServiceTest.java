package com.example.foreignexchange.service;

import com.example.foreignexchange.http.RestTemplateWrapper;
import com.example.foreignexchange.service.impl.ExchangeRateServiceImpl;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExchangeRateServiceTest {
    private static Faker faker;
    @Mock
    private RestTemplateWrapper restTemplateWrapper;

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    void should_get_exchange_rate() {
        // Arrange
        String url = "http://example.com/api";
        String expectedResponse = faker.numerify("##,##");
        Class<String> responseType = String.class;

        // Act
        ResponseEntity<String> responseEntity = ResponseEntity.ok(expectedResponse);
        when(restTemplateWrapper.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, responseType))
                .thenReturn(responseEntity);

        String actualResponse = exchangeRateServiceImpl.getExchangeRate(url, responseType);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }
}
