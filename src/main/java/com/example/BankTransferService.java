package com.example;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zoltan Altfatter
 */
@Service
public class BankTransferService {

    private final BankTransferRepository repository;

    public BankTransferService(BankTransferRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void completeTransfer(BankTransfer bankTransfer) {
        repository.save(bankTransfer.complete());
    }
}
