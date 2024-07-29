package com.example.foreignexchange.mapper;

import com.example.foreignexchange.data.entity.ConversionHistory;
import com.example.foreignexchange.model.dto.ConversionHistoryDto;
import com.example.foreignexchange.model.pagination.ConversionHistoryPageResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConversionHistoryMapper {
    ConversionHistoryDto toConversionHistoryDto(ConversionHistory conversionHistory);

    List<ConversionHistoryDto> toConversionHistoryDtos(List<ConversionHistory> conversionHistoryDtos);

    default ConversionHistoryPageResponse<ConversionHistoryDto> toConversionHistoryPageResponse(Page<ConversionHistory> page){
        ConversionHistoryPageResponse<ConversionHistoryDto> conversionHistoryPageResponse = new ConversionHistoryPageResponse<>();
        conversionHistoryPageResponse.setContent(toConversionHistoryDtos(page.getContent()));
        conversionHistoryPageResponse.setPage(page.getNumber());
        conversionHistoryPageResponse.setSize(page.getSize());
        conversionHistoryPageResponse.setTotalPages(page.getTotalPages());
        conversionHistoryPageResponse.setTotalSize(page.getTotalElements());
        return conversionHistoryPageResponse;
    }
}
