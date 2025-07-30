package com.SharpWallet.controller;

import com.SharpWallet.dto.request.*;

import com.SharpWallet.dto.response.ApiResponse;
import com.SharpWallet.exception.InvalidTransaction;
import com.SharpWallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/sharp_wallet/")
public class AccountController {


    @Autowired
    WalletService walletService;

    @GetMapping
    public ResponseEntity<String> wakeUp(){
        return ResponseEntity.accepted().body("Sharp Wallet is awake, sharp and running!");
    }

    @PostMapping("createAccount")
    public ResponseEntity<ApiResponse<?>> createAccount(@RequestBody CreateAccountRequest request) {
        try{
            return ResponseEntity.ok(new ApiResponse<>(walletService.createAccount(request),true));
        }catch(Exception exception){
            return ResponseEntity.badRequest().body(new ApiResponse<>(exception.getMessage(), false));
        }

    }

    @PostMapping("init-transaction")
    public ResponseEntity<ApiResponse<?>> initTransaction(@RequestBody PerformTransactionRequest request) {
        try{
            return ResponseEntity.ok(new ApiResponse<>(walletService.performTransaction(request),true));
        }catch(Exception exception){
            return ResponseEntity.badRequest().body(new ApiResponse<>(exception.getMessage(), false));
        }
    }

    @PostMapping("fund-paystackwallet")
    public ResponseEntity<ApiResponse<?>> fundPaystackWallet(@RequestBody PayStackFundWalletRequest request){
        try{
            walletService.fundWallet(new FundWalletRequest(request));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InvalidTransaction exception ) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(exception.getMessage(), false));

        }
    }

    @PostMapping("fund-monnifywallet")
    public ResponseEntity<ApiResponse<?>> fundMonnifyWallet(@RequestBody MonnifyFundWalletRequest request){
        try{
            walletService.fundWallet(new FundWalletRequest(request));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InvalidTransaction exception ) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(exception.getMessage(), false));
        }
    }

    @PostMapping("findAllTransactions")
    public ResponseEntity<ApiResponse<?>> getAllTransactions(@RequestParam("accountNumber") String accountNumber, @RequestParam("pin") String pin){
        try{
            return ResponseEntity.ok(new ApiResponse<>(walletService.findAllTransaction(accountNumber, pin), true));
        }catch(Exception exception){
            return ResponseEntity.badRequest().body(new ApiResponse<>(exception.getMessage(), false));
        }
    }

    @PostMapping("getProfile")
    public ResponseEntity<ApiResponse<?>> getProfile(@RequestParam("accountNumber") String accountNumber, @RequestParam("pin") String pin){
        try{
            return ResponseEntity.ok(new ApiResponse<>(walletService.getProfile(accountNumber, pin), true));
        }catch(Exception exception){
            return ResponseEntity.badRequest().body(new ApiResponse<>(exception.getMessage(), false));
        }
    }




}
