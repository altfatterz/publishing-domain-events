package com.example;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Zoltan Altfatter
 */
@Slf4j
@Entity
@Getter
public class BankAccount extends AbstractAggregateRoot {

    @Id
    @GeneratedValue
    private Long id;

    private Long overdraftLimit;  // Use MonetaryAmount
    private Long balanceInCents = 0L;

    // JPA
    private BankAccount() {}

    public BankAccount(Long overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @DomainEvents
    Collection<Object> domainEvents() {
        return Collections.singletonList(new BankAccountCreatedEvent(123L, 123L));
    }

    @AfterDomainEventPublication
    void callbackMethod() {
        log.info("cleaning up domain events list");
    }

}
