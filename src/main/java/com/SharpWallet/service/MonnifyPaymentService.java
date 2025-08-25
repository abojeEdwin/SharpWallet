package com.SharpWallet.service;

import com.SharpWallet.config.BeanConfig;
import com.SharpWallet.dto.request.InitializePayment;
import com.SharpWallet.dto.response.InitializePaymentResponse;
import com.SharpWallet.dto.response.MonnifyResponse;
import com.SharpWallet.util.External;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class MonnifyPaymentService implements PaymentService {

    @Autowired
    private BeanConfig beanConfig;

    @Override
    public InitializePaymentResponse initializeTransaction(InitializePayment<?> request) {
        InitializePaymentResponse initializePaymentResponse = new InitializePaymentResponse();
        String secret = beanConfig.getMonnifyApiKey()+":"+beanConfig.getMonnifySecretKey();
        String key = "Basic "+ Base64.getEncoder().encodeToString(secret.getBytes());
        ResponseEntity<MonnifyResponse> response = External.makeCall(key, request.getData(), beanConfig.getMonnifyUrl(), MonnifyResponse.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null){
            initializePaymentResponse.setStatus(false);
        }else {
            MonnifyResponse monnifyResponse = response.getBody();
            initializePaymentResponse.setUrl(monnifyResponse.getResponseBody().getCheckoutUrl());
            initializePaymentResponse.setReference(monnifyResponse.getResponseBody().getPaymentReference());
            initializePaymentResponse.setStatus(true);
        }
        return initializePaymentResponse;

    }


}
