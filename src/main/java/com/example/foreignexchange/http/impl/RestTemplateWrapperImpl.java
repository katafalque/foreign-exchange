package com.example.foreignexchange.http.impl;


import com.example.foreignexchange.http.RestTemplateWrapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateWrapperImpl implements RestTemplateWrapper {
    private RestTemplate restTemplate;

    public RestTemplateWrapperImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, HttpEntity<?> requestEntity, Class<T> responseType) {
        return restTemplate.postForEntity(url, requestEntity, responseType);
    }

    @Override
    public <T> T postForObject(String url, HttpEntity<?> requestEntity, Class<T> responseType) {
        return restTemplate.postForObject(url, requestEntity, responseType);
    }

    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        return restTemplate.exchange(url, method, requestEntity, responseType);
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
