package com.example.foreignexchange.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings({"java:S116", "java:S1104"})
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeRateApiResponseType {
    @JsonProperty("conversion_rate")
    private Double conversionRate;

    @JsonProperty("time_last_update_unix")
    private Long timeLastUpdateUnix;

    @JsonProperty("result")
    private String result;

    @JsonProperty("error-type")
    private String errorType;
}
