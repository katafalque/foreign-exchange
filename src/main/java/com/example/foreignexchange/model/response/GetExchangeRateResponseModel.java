package com.example.foreignexchange.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Setter;

@SuppressWarnings({"java:S116", "java:S1104"})
@Setter
@Builder
public class GetExchangeRateResponseModel {
    @JsonProperty("conversion_type")
    public String conversionType;
    @JsonProperty("exchange_rate")
    public double exchangeRate;
}
