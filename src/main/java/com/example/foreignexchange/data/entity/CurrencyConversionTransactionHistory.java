package com.example.foreignexchange.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currency_conversion_transactions")
public class CurrencyConversionTransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String source;

    private String target;

    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
