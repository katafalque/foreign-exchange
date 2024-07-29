package com.example.foreignexchange.mapper;

import com.example.foreignexchange.data.entity.ConversionHistory;
import com.example.foreignexchange.model.dto.ConversionHistoryDto;
import com.example.foreignexchange.model.pagination.ConversionHistoryPageResponse;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConversionHistoryMapperTest {

    private ConversionHistoryMapper mapper;
    private static Faker faker;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(ConversionHistoryMapper.class);
        faker = new Faker();
    }

    @Test
    void should_map_conversion_history_to_dto() {
        ConversionHistory conversionHistory = ConversionHistory.builder()
                .id(UUID.randomUUID())
                .target(faker.currency().code())
                .source(faker.currency().code())
                .date(new Date(System.currentTimeMillis()))
                .build();

        ConversionHistoryDto dto = mapper.toConversionHistoryDto(conversionHistory);

        assertNotNull(dto);
        assertEquals(conversionHistory.getId(), dto.getId());
        assertEquals(conversionHistory.getSource(), dto.getSource());
        assertEquals(conversionHistory.getTarget(), dto.getTarget());
        assertEquals(conversionHistory.getAmount(), dto.getAmount());
        assertEquals(conversionHistory.getDate(), dto.getDate());
    }

    @Test
    void should_map_conversion_histories_to_dtos() {
        ConversionHistory conversionHistory1 = ConversionHistory.builder()
                .id(UUID.randomUUID())
                .target(faker.currency().code())
                .source(faker.currency().code())
                .date(new Date(System.currentTimeMillis()))
                .build();

        ConversionHistory conversionHistory2 = ConversionHistory.builder()
                .id(UUID.randomUUID())
                .target(faker.currency().code())
                .source(faker.currency().code())
                .date(new Date(System.currentTimeMillis()))
                .build();

        List<ConversionHistory> conversionHistories = Arrays.asList(conversionHistory1, conversionHistory2);

        List<ConversionHistoryDto> dtos = mapper.toConversionHistoryDtos(conversionHistories);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        ConversionHistoryDto dto1 = dtos.get(0);
        assertEquals(conversionHistory1.getId(), dto1.getId());
        assertEquals(conversionHistory1.getSource(), dto1.getSource());
        assertEquals(conversionHistory1.getTarget(), dto1.getTarget());
        assertEquals(conversionHistory1.getAmount(), dto1.getAmount());
        assertEquals(conversionHistory1.getDate(), dto1.getDate());

        ConversionHistoryDto dto2 = dtos.get(1);
        assertEquals(conversionHistory2.getId(), dto2.getId());
        assertEquals(conversionHistory2.getSource(), dto2.getSource());
        assertEquals(conversionHistory2.getTarget(), dto2.getTarget());
        assertEquals(conversionHistory2.getAmount(), dto2.getAmount());
        assertEquals(conversionHistory2.getDate(), dto2.getDate());
    }

    @Test
    void should_map_page_to_page_response() {
        ConversionHistory conversionHistory1 = ConversionHistory.builder()
                .id(UUID.randomUUID())
                .target(faker.currency().code())
                .source(faker.currency().code())
                .date(new Date(System.currentTimeMillis()))
                .build();

        ConversionHistory conversionHistory2 = ConversionHistory.builder()
                .id(UUID.randomUUID())
                .target(faker.currency().code())
                .source(faker.currency().code())
                .date(new Date(System.currentTimeMillis()))
                .build();

        List<ConversionHistory> conversionHistories = Arrays.asList(conversionHistory1, conversionHistory2);
        Page<ConversionHistory> page = new PageImpl<>(conversionHistories, PageRequest.of(0, 10, Sort.by("date")), conversionHistories.size());

        ConversionHistoryPageResponse<ConversionHistoryDto> pageResponse = mapper.toConversionHistoryPageResponse(page);

        assertNotNull(pageResponse);
        assertEquals(2, pageResponse.getContent().size());
        assertEquals(0, pageResponse.getPage());
        assertEquals(10, pageResponse.getSize());
        assertEquals(1, pageResponse.getTotalPages());
        assertEquals(2, pageResponse.getTotalSize());
    }
}

