package com.owenc.bank_api;

import com.owenc.bank_api.repository.UserRepository;
import com.owenc.bank_api.repository.InMemoryUserRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankApiApplication {

    // This is the main method
    public static void main(String[] args) {
        SpringApplication.run(BankApiApplication.class, args);
    }

    // Define userRespository bean as required by UserService
    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }
}

