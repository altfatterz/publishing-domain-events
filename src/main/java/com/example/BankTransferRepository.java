package com.example;

import org.springframework.data.repository.Repository;

/**
 * @author Zoltan Altfatter
 */
public interface BankTransferRepository extends Repository<BankTransfer, String> {

    BankTransfer save(BankTransfer bankTransfer);

    BankTransfer findById(String id);
}
