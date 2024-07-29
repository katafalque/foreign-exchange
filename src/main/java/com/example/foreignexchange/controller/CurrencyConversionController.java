package com.example.foreignexchange.controller;

import com.example.foreignexchange.component.ExchangeRateManager;
import com.example.foreignexchange.data.entity.ConversionHistory;
import com.example.foreignexchange.model.response.CurrencyConversionResponseModel;
import com.example.foreignexchange.model.response.ErrorResponse;
import com.example.foreignexchange.service.ConversionHistoryService;
import com.example.foreignexchange.utils.ErrorResponseParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.sql.Date;

@RestController
public class CurrencyConversionController {
    private final ExchangeRateManager exchangeRateManager;
    private final ConversionHistoryService conversionHistoryService;

    @Autowired
    public CurrencyConversionController(ExchangeRateManager exchangeRateManager,
                                        ConversionHistoryService conversionHistoryService){
        this.exchangeRateManager = exchangeRateManager;
        this.conversionHistoryService = conversionHistoryService;
    }

    @GetMapping("/currency-conversion")
    public ResponseEntity currencyConversion(
            @RequestParam() double amount,
            @RequestParam() String fromCurrency,
            @RequestParam() String toCurrency){
        try{
            double conversion = this.exchangeRateManager.convertCurrency(amount, fromCurrency, toCurrency);

            CurrencyConversionResponseModel currencyConversionResponseModel = CurrencyConversionResponseModel.builder()
                    .conversionResult(conversion)
                    .conversionType(fromCurrency + toCurrency)
                    .build();

            this.conversionHistoryService.saveConversionHistory(
                    ConversionHistory.builder()
                            .date(new Date(System.currentTimeMillis()))
                            .amount(amount)
                            .source(fromCurrency)
                            .target(toCurrency)
                            .build()
            );

            return new ResponseEntity(currencyConversionResponseModel, HttpStatus.OK);
        } catch (HttpStatusCodeException e){
            ErrorResponse errorResponse = ErrorResponseParser.parseErrorResponse(e.getResponseBodyAsString());
            return new ResponseEntity<ErrorResponse>(errorResponse, e.getStatusCode());
        } catch (Exception e){
            return new ResponseEntity<ErrorResponse>(
                    ErrorResponse.builder().message(e.getMessage()).result("server failed").build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }
}
