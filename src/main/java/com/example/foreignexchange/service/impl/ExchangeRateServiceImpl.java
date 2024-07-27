package com.example.foreignexchange.service.impl;

import com.example.foreignexchange.http.RestTemplateWrapper;
import com.example.foreignexchange.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class ExchangeRateServiceImpl implements ExchangeRateService {
    private RestTemplateWrapper restTemplateWrapper;

    @Autowired
    public ExchangeRateServiceImpl(RestTemplateWrapper restTemplateWrapper){
        this.restTemplateWrapper = restTemplateWrapper;
    }
    @Override
    public <T> T getExchangeRate(String url, Class<T> responseType) {
        ResponseEntity<T> responseEntity = this.restTemplateWrapper.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, responseType);
        return responseEntity.getBody();
    }
}
