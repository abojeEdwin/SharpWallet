package com.SharpWallet.model.data;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;
    private String accountNumber;
    private BigDecimal balance = BigDecimal.ZERO;
    private String pin;

    @OneToOne
    private Profile profile;


}
