package com.example.foreignexchange.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@SuppressWarnings({"java:S116", "java:S1104"})
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FrankfurterResponseType {
    @JsonProperty("date")
    private String date;

    @JsonProperty("rates")
    private Map<String, Double> rates;
}
