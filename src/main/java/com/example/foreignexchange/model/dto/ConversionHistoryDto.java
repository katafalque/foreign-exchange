package com.example.foreignexchange.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ConversionHistoryDto {
    private UUID id;
    private String source;
    private String target;
    private double amount;
    private Date date;
}
