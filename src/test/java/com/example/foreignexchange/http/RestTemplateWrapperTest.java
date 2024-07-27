package com.example.foreignexchange.http;

import com.example.foreignexchange.http.impl.RestTemplateWrapperImpl;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestTemplateWrapperTest {
    @Mock
    private RestTemplate restTemplate;

    private final RestTemplateWrapperImpl restTemplateWrapper;

    public RestTemplateWrapperTest() {
        MockitoAnnotations.openMocks(this);
        restTemplateWrapper = new RestTemplateWrapperImpl();
    }

    @Test
    void post_for_entity_should_return_response_entity() {
        // Arrange
        String url = "http://example.com/api";
        HttpEntity<?> requestEntity = createRequestEntity();
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Test response");

        when(restTemplate.postForEntity(url, requestEntity, String.class)).thenReturn(expectedResponse);
        restTemplateWrapper.setRestTemplate(restTemplate);

        // Act
        ResponseEntity<String> response = restTemplateWrapper.postForEntity(url, requestEntity, String.class);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void exchange_should_return_response_entity() {
        // Arrange
        String url = "http://example.com/api";
        HttpMethod method = HttpMethod.PUT;
        HttpEntity<?> requestEntity = createRequestEntity();
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Test response");

        when(restTemplate.exchange(url, method, requestEntity, String.class)).thenReturn(expectedResponse);
        restTemplateWrapper.setRestTemplate(restTemplate);

        // Act
        ResponseEntity<String> response = restTemplateWrapper.exchange(url, method, requestEntity, String.class);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void post_for_object_should_return_response_entity() {
        // Arrange
        String url = "http://example.com/api";
        HttpEntity<?> requestEntity = createRequestEntity();
        String expectedResponse = "Test response";

        when(restTemplate.postForObject(url, requestEntity, String.class)).thenReturn(expectedResponse);
        restTemplateWrapper.setRestTemplate(restTemplate);

        // Act
        String response = restTemplateWrapper.postForObject(url, requestEntity, String.class);

        // Assert
        assertEquals(expectedResponse, response);
    }


    private HttpEntity<?> createRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }
}
