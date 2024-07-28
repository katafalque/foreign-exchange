package com.example.foreignexchange.utils;

import com.example.foreignexchange.model.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
class ErrorResponseParserTest {
    @ParameterizedTest
    @CsvSource({
            "'{\"error-type\": \"invalid-key\"}', invalid-key",
            "'{\"message\": \"not found\"}', not found"
    })
    void should_create_error_response_with_various_inputs(String errorResponseBody, String expectedMessage) {
        ErrorResponse errorResponse = ErrorResponseParser.parseErrorResponse(errorResponseBody);

        assertEquals(expectedMessage, errorResponse.getMessage());
        assertEquals("error", errorResponse.getResult());
    }

    @Test
    void should_create_error_response_without_error_type_or_key() {
        String errorResponseBody = "{}";

        ErrorResponse errorResponse = ErrorResponseParser.parseErrorResponse(errorResponseBody);

        assertEquals("Unknown error", errorResponse.getMessage());
        assertEquals("error", errorResponse.getResult());
    }

    @Test
    void should_create_error_response_when_io_exception_thrown() {
        String errorResponseBody = "{error-type: invalid-key"; // Malformed JSON

        ErrorResponse errorResponse = ErrorResponseParser.parseErrorResponse(errorResponseBody);

        assertEquals("Error processing the response.", errorResponse.getMessage());
        assertEquals("failed", errorResponse.getResult());
    }
}

