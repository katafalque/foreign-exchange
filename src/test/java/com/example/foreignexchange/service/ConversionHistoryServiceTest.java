package com.example.foreignexchange.service;

import com.example.foreignexchange.data.entity.ConversionHistory;
import com.example.foreignexchange.mapper.ConversionHistoryMapper;
import com.example.foreignexchange.model.dto.ConversionHistoryDto;
import com.example.foreignexchange.model.pagination.ConversionHistoryPageResponse;
import com.example.foreignexchange.model.request.ConversionHistoryRequestModel;
import com.example.foreignexchange.repository.ConversionHistoryRepository;
import com.example.foreignexchange.service.impl.ConversionHistoryServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConversionHistoryServiceTest {

    @Mock
    private ConversionHistoryRepository conversionHistoryRepository;

    private ConversionHistoryServiceImpl conversionHistoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.conversionHistoryService = new ConversionHistoryServiceImpl(conversionHistoryRepository);
    }

    @Test
    void should_save_conversion_history() {
        ConversionHistory conversionHistory = ConversionHistory.builder()
                .id(UUID.randomUUID())
                .build();

        conversionHistoryService.saveConversionHistory(conversionHistory);

        verify(conversionHistoryRepository, times(1)).save(conversionHistory);
    }

    @Test
    void should_get_by_id() {
        UUID id = UUID.randomUUID();
        ConversionHistory conversionHistory = ConversionHistory.builder()
                .id(id)
                .build();

        when(conversionHistoryRepository.findById(id)).thenReturn(Optional.of(conversionHistory));

        ConversionHistory result = conversionHistoryService.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(conversionHistoryRepository, times(1)).findById(id);
    }
}

