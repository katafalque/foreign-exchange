package com.example.foreignexchange.repository;

import com.example.foreignexchange.data.entity.CurrencyConversionTransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CurrencyConversionTransactionHistoryRepository extends JpaRepository<CurrencyConversionTransactionHistory, UUID> {
}
