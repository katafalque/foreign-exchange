package com.example.foreignexchange.service.impl;

import com.example.foreignexchange.data.entity.ConversionHistory;
import com.example.foreignexchange.data.specification.ConversionHistorySpec;
import com.example.foreignexchange.mapper.ConversionHistoryMapper;
import com.example.foreignexchange.model.dto.ConversionHistoryDto;
import com.example.foreignexchange.model.pagination.ConversionHistoryPageResponse;
import com.example.foreignexchange.model.request.ConversionHistoryRequestModel;
import com.example.foreignexchange.repository.ConversionHistoryRepository;
import com.example.foreignexchange.service.ConversionHistoryService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConversionHistoryServiceImpl implements ConversionHistoryService {
    private final ConversionHistoryRepository conversionHistoryRepository;
    private final ConversionHistoryMapper conversionHistoryMapper;

    @Autowired
    public ConversionHistoryServiceImpl(ConversionHistoryRepository conversionHistoryRepository){
        this.conversionHistoryRepository = conversionHistoryRepository;
        this.conversionHistoryMapper = Mappers.getMapper(ConversionHistoryMapper.class);

    }
    @Override
    public void saveConversionHistory(ConversionHistory conversionHistory) {
        this.conversionHistoryRepository.save(conversionHistory);
    }

    @Override
    public ConversionHistory getById(UUID id) {
        return this.conversionHistoryRepository.findById(id).orElse(null);
    }

    @Override
    public ConversionHistoryPageResponse<ConversionHistoryDto> getByFilter(ConversionHistoryRequestModel requestModel, int page, int size) {
        Specification<ConversionHistory> spec =
                ConversionHistorySpec.filterBy(requestModel);
        Page<ConversionHistory> result = this.conversionHistoryRepository
                .findAll(spec, PageRequest.of(page, size));
        return conversionHistoryMapper.toConversionHistoryPageResponse(result);
    }
}
