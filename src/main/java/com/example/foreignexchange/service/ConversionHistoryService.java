package com.example.foreignexchange.service;

import com.example.foreignexchange.data.entity.ConversionHistory;
import com.example.foreignexchange.model.dto.ConversionHistoryDto;
import com.example.foreignexchange.model.pagination.ConversionHistoryPageResponse;
import com.example.foreignexchange.model.request.ConversionHistoryRequestModel;

import java.util.UUID;

public interface ConversionHistoryService {
    void saveConversionHistory(ConversionHistory conversionHistory);
    ConversionHistory getById(UUID id);
    ConversionHistoryPageResponse<ConversionHistoryDto> getByFilter(ConversionHistoryRequestModel requestModel);
}
