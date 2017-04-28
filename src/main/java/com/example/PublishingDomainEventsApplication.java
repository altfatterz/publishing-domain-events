package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PublishingDomainEventsApplication implements CommandLineRunner {

    @Autowired
    private BankAccountRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(PublishingDomainEventsApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        repository.save(new BankAccount(1000L));
    }
}
