package com.example.foreignexchange.controller;

import com.example.foreignexchange.component.ExchangeRateManager;
import com.example.foreignexchange.model.response.ErrorResponse;
import com.example.foreignexchange.model.response.GetExchangeRateResponseModel;
import com.example.foreignexchange.utils.ErrorResponseParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
public class ExchangeRateController {
    private ExchangeRateManager exchangeRateManager;

    @Autowired
    public ExchangeRateController(ExchangeRateManager exchangeRateManager){
        this.exchangeRateManager = exchangeRateManager;
    }

    @GetMapping("/exchange-rate")
    public ResponseEntity getExchangeRate(@RequestParam() String fromCurrency,
                                          @RequestParam() String toCurrency){
        try{
            double exchangeRate = this.exchangeRateManager.getExchangeRate(fromCurrency, toCurrency);

            GetExchangeRateResponseModel getExchangeRateResponseModel = GetExchangeRateResponseModel.builder()
                    .exchangeRate(exchangeRate)
                    .conversionType(fromCurrency + toCurrency)
                    .build();
            return new ResponseEntity<GetExchangeRateResponseModel>(getExchangeRateResponseModel, HttpStatus.OK);
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
