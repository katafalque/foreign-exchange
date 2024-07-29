package com.example.foreignexchange.model.request;

import com.example.foreignexchange.utils.validators.ValidateConversionHistoryRequestModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@ValidateConversionHistoryRequestModel
public class ConversionHistoryRequestModel {
    private UUID id;
    private Date date;
    private int size;
    private int page;
}
