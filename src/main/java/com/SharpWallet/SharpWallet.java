package com.SharpWallet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.SharpWallet")
public class SharpWallet {
    public static void main(String[] args) {
        SpringApplication.run(SharpWallet.class, args);
    }
}

