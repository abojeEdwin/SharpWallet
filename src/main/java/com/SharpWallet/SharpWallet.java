package com.SharpWallet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.SharpWallet")
public class SharpWallet {
    public static void main(String[] args) {
        SpringApplication.run(SharpWallet.class, args);
    }
}

