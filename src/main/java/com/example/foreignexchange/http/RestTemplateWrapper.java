package com.example.foreignexchange.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface RestTemplateWrapper {
    <T> ResponseEntity<T> postForEntity(String url, HttpEntity<?> requestEntity, Class<T> responseType);

    <T> T postForObject(String url, HttpEntity<?> requestEntity, Class<T> responseType);

    <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType);
}