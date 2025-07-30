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
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("--- LETS DEBUG RENDER ENV VARS ---");

            String springProfile = System.getenv("SPRING_PROFILES_ACTIVE");
            System.out.println("SPRING_PROFILES_ACTIVE = " + springProfile);

            String dbUrl = System.getenv("DATABASE_URL");
            if (dbUrl != null && !dbUrl.isEmpty()) {
                System.out.println("DATABASE_URL IS SET. It starts with 'jdbc': " + dbUrl.startsWith("jdbc"));
            } else {
                // This is the most likely cause of your error
                System.out.println("DATABASE_URL IS NULL OR EMPTY. THIS IS THE PROBLEM!");
            }

            System.out.println("--- END OF DEBUG ---");
        };
    }

}

