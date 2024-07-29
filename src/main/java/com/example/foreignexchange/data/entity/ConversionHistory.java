package com.example.foreignexchange.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conversion_history")
public class ConversionHistory {
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
    private Date date;
}
