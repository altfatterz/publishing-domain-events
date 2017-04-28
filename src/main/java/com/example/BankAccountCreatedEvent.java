package com.example;

import lombok.Value;

/**
 * @author Zoltan Altfatter
 */
@Value
public class BankAccountCreatedEvent {

    private Long bankAccountId;
    private Long overdraftLimit;
}
