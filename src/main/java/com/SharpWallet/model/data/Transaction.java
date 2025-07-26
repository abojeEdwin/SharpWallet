package com.SharpWallet.model.data;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private String description;
    @ManyToOne
    private Account account;
    private String recipientName;
    @Enumerated(EnumType.STRING)
    private Status status = Status.PROCESSING;
    private LocalDateTime createdAt = LocalDateTime.now();

}
