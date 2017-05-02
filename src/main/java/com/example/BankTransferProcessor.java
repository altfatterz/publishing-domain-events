package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Random;

/**
 * @author Zoltan Altfatter
 */
@Service
@Slf4j
public class BankTransferProcessor {

    private final BankTransferRepository repository;

    public BankTransferProcessor(BankTransferRepository repository) {
        this.repository = repository;
    }

    @Async
    @TransactionalEventListener
    public void handleBankTransferCompletedEvent(BankTransferCompletedEvent event) {
        BankTransfer bankTransfer = repository.findById(event.getBankTransferId());
        bankTransfer.markStarted();
        bankTransfer = repository.save(bankTransfer);

        log.info("Starting to process bank transfer {}.", bankTransfer);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (new Random().nextBoolean()) {
            bankTransfer.markCompleted();
        } else {
            bankTransfer.markFailed();
        }

        repository.save(bankTransfer);

        log.info("Finished processing bank transfer {}.", bankTransfer);
    }

}
