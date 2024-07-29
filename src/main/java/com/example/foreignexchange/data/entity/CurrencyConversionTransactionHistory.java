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

    @Column(name = "source")
    private String source;

    @Column(name = "target")
    private String target;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
