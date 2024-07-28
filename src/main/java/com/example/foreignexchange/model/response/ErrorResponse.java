package com.example.foreignexchange.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {
    @JsonProperty
    public String message;
    @JsonProperty
    public String result;
}
