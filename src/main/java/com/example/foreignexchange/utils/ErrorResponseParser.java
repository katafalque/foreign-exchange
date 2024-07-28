package com.example.foreignexchange.utils;

import com.example.foreignexchange.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ErrorResponseParser {

    private ErrorResponseParser() {
        throw new IllegalStateException("Utility class");
    }

    public static ErrorResponse parseErrorResponse(String errorResponseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(errorResponseBody);
            String message;

            if (jsonNode.has("error-type")) {
                message = jsonNode.get("error-type").asText();
            } else if (jsonNode.has("message")) {
                message = jsonNode.get("message").asText();
            } else {
                message = "Unknown error";
            }

            return ErrorResponse.builder().message(message).result("error").build();
        } catch (IOException e) {
            return ErrorResponse.builder().message("Error processing the response.").result("failed").build();
        }
    }
}
