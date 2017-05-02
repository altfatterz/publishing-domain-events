package com.example;

import lombok.*;

/**
 * @author Zoltan Altfatter
 */
@Value
public class BankTransferCompletedEvent {

    private final String bankTransferId;

}
