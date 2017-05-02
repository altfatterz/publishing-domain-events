package com.example;

import org.springframework.data.repository.Repository;

/**
 * @author Zoltan Altfatter
 */
public interface BankAccountRepository extends Repository<BankAccount, Long> {

    BankAccount save(BankAccount bankAccount);

    BankAccount findById(Long id);

}
