package com.example.foreignexchange.repository;

import com.example.foreignexchange.data.entity.ConversionHistory;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConversionHistoryRepository
        extends JpaRepository<ConversionHistory, UUID>,
        JpaSpecificationExecutor<ConversionHistory> {
    Page<ConversionHistory> findAll(@Nullable Specification<ConversionHistory> spec,
                                    @Nonnull Pageable pageable);
}
