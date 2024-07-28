package com.example.foreignexchange.integration;

import com.example.foreignexchange.model.response.CurrencyConversionResponseModel;
import com.example.foreignexchange.model.response.ErrorResponse;
import com.example.foreignexchange.model.response.FrankfurterResponseType;
import com.example.foreignexchange.model.response.GetExchangeRateResponseModel;
import com.example.foreignexchange.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CurrencyConversionControllerTest {
    private static Faker faker;
    @MockBean
    private ExchangeRateService exchangeRateService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        faker = new Faker();
    }

    @SneakyThrows
    private ResultActions sendMockRequest(String amount, String fromCurrency, String toCurrency) {
        return mockMvc.perform(get("/currency-conversion")
                .param("amount", amount)
                .param("fromCurrency", fromCurrency)
                .param("toCurrency", toCurrency)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
    }

    @Test
    void should_return_ok_and_conversion() throws Exception {
        //Arrange
        String fromCurrency = faker.currency().code();
        String toCurrency = faker.currency().code();
        double rate = faker.number().randomDouble(2, 10, 100);
        double amount = faker.number().randomDouble(0, 100, 1000);

        var rateMap = new HashMap<String, Double>();
        rateMap.put(toCurrency, rate);

        var mockResponse = FrankfurterResponseType.builder()
                .rates(rateMap)
                .date(faker.date().toString())
                .build();

        var expected = CurrencyConversionResponseModel.builder()
                .conversionResult(rate * amount)
                .conversionType(fromCurrency + toCurrency)
                .build();

        when(exchangeRateService.getExchangeRate(any(), any())).thenReturn(mockResponse);

        // Act & Assert
        var response = sendMockRequest(String.valueOf(amount), fromCurrency, toCurrency).andReturn().getResponse();


        assertEquals(objectMapper.writeValueAsString(expected), response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void should_return_error_response_when_status_code_exception() throws Exception {
        //Arrange
        String fromCurrency = faker.currency().code();
        String toCurrency = faker.currency().code();
        String message = faker.lorem().word();
        String exceptionMessage = "{\"message\":" + " \"" + message + "\""+ "}";
        double amount = faker.number().randomDouble(0, 100, 1000);

        var expected = ErrorResponse.builder()
                .message(message)
                .result("error")
                .build();

        when(exchangeRateService.getExchangeRate(any(), any()))
                .thenThrow(new HttpClientErrorException(
                        HttpStatus.BAD_REQUEST,
                        message,
                        exceptionMessage.getBytes(),
                        Charset.defaultCharset())
                );

        // Act & Assert
        var response = sendMockRequest(String.valueOf(amount), fromCurrency, toCurrency).andReturn().getResponse();


        assertEquals(objectMapper.writeValueAsString(expected), response.getContentAsString());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void should_return_error_response_when_unhandled_exception() throws Exception {
        //Arrange
        String fromCurrency = faker.currency().code();
        String toCurrency = faker.currency().code();
        String message = faker.lorem().word();
        double amount = faker.number().randomDouble(0, 100, 1000);

        var expected = ErrorResponse.builder()
                .message(message)
                .result("server failed")
                .build();

        when(exchangeRateService.getExchangeRate(any(), any()))
                .thenThrow(new RuntimeException(message));

        // Act & Assert
        var response = sendMockRequest(String.valueOf(amount), fromCurrency, toCurrency).andReturn().getResponse();


        assertEquals(objectMapper.writeValueAsString(expected), response.getContentAsString());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }
}
