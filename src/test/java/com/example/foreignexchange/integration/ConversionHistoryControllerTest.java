package com.example.foreignexchange.integration;

import com.example.foreignexchange.data.entity.ConversionHistory;
import com.example.foreignexchange.model.request.ConversionHistoryRequestModel;
import com.example.foreignexchange.model.response.ErrorResponse;
import com.example.foreignexchange.repository.ConversionHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConversionHistoryControllerTest {
    private static Faker faker;
    private ConversionHistory conversionHistory;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JdbcTemplate tmrwJdbcTemplate;
    @SpyBean
    private ConversionHistoryRepository conversionHistoryRepository;

    @SneakyThrows
    @BeforeAll
    void setupDb() {
        MockitoAnnotations.initMocks(this);
        faker = new Faker();

        conversionHistory = ConversionHistory.builder()
                .date(new Date(System.currentTimeMillis()))
                .amount(faker.number().randomDouble(0, 1000, 5000))
                .target(faker.currency().code())
                .source(faker.currency().code())
                .build();

        conversionHistoryRepository.save(conversionHistory);
    }

    @BeforeEach
    void reset() {
        Mockito.reset(conversionHistoryRepository);
    }

    @SneakyThrows
    private ResultActions sendMockRequest(ConversionHistoryRequestModel requestModel) {
        return mockMvc.perform(post("/conversion-history")
                        .content(objectMapper.writeValueAsString(requestModel))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
    }

    @Test
    void should_return_ok_and_history_with_id() throws Exception {
        //Arrange
        ConversionHistoryRequestModel requestModel = ConversionHistoryRequestModel.builder()
                .id(conversionHistory.getId())
                .page(0)
                .size(10)
                .build();

        // Act & Assert
        var response = sendMockRequest(requestModel).andReturn().getResponse();

        assertNotNull(response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void should_return_ok_and_history_with_date() throws Exception {
        //Arrange
        ConversionHistoryRequestModel requestModel = ConversionHistoryRequestModel.builder()
                .id(conversionHistory.getId())
                .date(new Date(System.currentTimeMillis()))
                .page(0)
                .size(10)
                .build();

        // Act & Assert
        var response = sendMockRequest(requestModel).andReturn().getResponse();

        assertNotNull(response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void should_return_bad_request_with_error_response() throws Exception {
        //Arrange
        ConversionHistoryRequestModel requestModel = ConversionHistoryRequestModel.builder()
                .page(0)
                .size(10)
                .build();

        // Act & Assert
        var response = sendMockRequest(requestModel).andReturn().getResponse();
        var errorResponse = getErrorResponseFromString(response.getContentAsString());

        assertNotNull(response.getContentAsString());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("invalid-request", errorResponse.getMessage());
        assertEquals("failed", errorResponse.getResult());
    }

    @Test
    void should_return_internal_server_error_with_error_response() throws Exception {
        //Arrange
        ConversionHistoryRequestModel requestModel = ConversionHistoryRequestModel.builder()
                .id(conversionHistory.getId())
                .date(new Date(System.currentTimeMillis()))
                .page(10)
                .size(10)
                .build();

        doThrow(new RuntimeException("Thrown for testing purposes"))
                .when(conversionHistoryRepository)
                .findAll((Specification<ConversionHistory>) any(), (Pageable) any());

        // Act & Assert
        var response = sendMockRequest(requestModel).andReturn().getResponse();
        var errorResponse = getErrorResponseFromString(response.getContentAsString());

        assertNotNull(response.getContentAsString());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("server-error", errorResponse.getMessage());
        assertEquals("failed", errorResponse.getResult());
    }

    private ErrorResponse getErrorResponseFromString(String errorResponse) throws JsonProcessingException {
        return objectMapper.readValue(errorResponse, ErrorResponse.class);
    }
}
