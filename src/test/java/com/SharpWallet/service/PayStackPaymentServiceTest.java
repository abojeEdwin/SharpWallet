package com.SharpWallet.service;

import com.SharpWallet.dto.request.InitializePayment;
import com.SharpWallet.dto.request.PayStackInitializePayment;
import com.SharpWallet.dto.response.InitializePaymentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PayStackPaymentServiceTest {

    @Autowired
    @Qualifier("payStackPaymentService")
    private PaymentService paymentService;


    @Test
    public void testThatInitializePayment() {
        InitializePayment<PayStackInitializePayment> request = new InitializePayment<>();
        PayStackInitializePayment initializePayment = new PayStackInitializePayment();
        initializePayment.setAmount(BigDecimal.valueOf(10000));
        initializePayment.setReference(UUID.randomUUID().toString());
        initializePayment.setEmail("abojeedwin@gmail.com");
        request.setData(initializePayment);
        InitializePaymentResponse response = paymentService.initializeTransaction(request);
        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isEqualTo(Boolean.TRUE);
        assertThat(response.getUrl()).isNotNull();
        assertThat(response.getReference()).isNotNull();

    }
}