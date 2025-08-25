package com.SharpWallet.service;

import com.SharpWallet.config.BeanConfig;
import com.SharpWallet.dto.request.InitializePayment;
import com.SharpWallet.dto.response.InitializePaymentResponse;
import com.SharpWallet.dto.response.PaystackResponse;
import com.SharpWallet.util.External;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PayStackPaymentService implements PaymentService {

    @Autowired
    private BeanConfig beanConfig;


    @Override
    public InitializePaymentResponse initializeTransaction(InitializePayment<?> request) {
        InitializePaymentResponse initializePaymentResponse = new InitializePaymentResponse();
        String key = "Bearer " + beanConfig.getPayStackSecretKey();
        ResponseEntity<PaystackResponse> response = External.makeCall(key, request.getData(), beanConfig.getPayStackUrl(), PaystackResponse.class);
        if(response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            initializePaymentResponse.setStatus(false);}

        PaystackResponse paystackResponse = response.getBody();
        initializePaymentResponse.setStatus(true);
        initializePaymentResponse.setReference(paystackResponse.getData().getReference());
        initializePaymentResponse.setUrl(paystackResponse.getData().getAuthorization_url());
        return initializePaymentResponse;
    }
}
