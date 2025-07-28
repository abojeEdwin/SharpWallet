package com.SharpWallet.service;


import com.SharpWallet.dto.request.InitializePayment;
import com.SharpWallet.dto.response.InitializePaymentResponse;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    InitializePaymentResponse initializeTransaction(InitializePayment<?> request);
}
