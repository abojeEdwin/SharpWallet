package com.SharpWallet.service;

import com.SharpWallet.config.BeanConfig;
import com.SharpWallet.dto.request.InitializePayment;
import com.SharpWallet.dto.request.MonnifyInitializePayment;
import com.SharpWallet.dto.response.InitializePaymentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MonnifyPaymentServiceTest {

    @Autowired
    BeanConfig beanConfig;

    @Autowired
    @Qualifier("monnifyPaymentService")
    PaymentService monnifyPaymentService;

    @Test
    public void testThatInitializePayment() {
        InitializePayment<MonnifyInitializePayment> request = new InitializePayment<>();
        MonnifyInitializePayment initializePayment = new MonnifyInitializePayment();
        initializePayment.setPaymentDescription("Food");
        initializePayment.setCustomerName("Edwin Aboje");
        initializePayment.setAmount(BigDecimal.valueOf(10000));
        initializePayment.setCustomerEmail("abojeedwin@gmail.com");
        initializePayment.setCurrencyCode("NGN");
        initializePayment.setContractCode(beanConfig.getMonnifyContractCode());
        initializePayment.setPaymentReference(UUID.randomUUID().toString());
        request.setData(initializePayment);
        InitializePaymentResponse response = monnifyPaymentService.initializeTransaction(request);
        assertNotNull(response);
        assertThat(response.isStatus()).isEqualTo(Boolean.TRUE);
        assertThat(response.getUrl()).isNotNull();
        assertThat(response.getReference()).isNotNull();

    }



}