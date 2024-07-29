package com.example.foreignexchange.data.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

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
