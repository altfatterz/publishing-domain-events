package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zoltan Altfatter
 */
@RestController
public class BankAccountController {

    private final BankAccountRepository repository;

    public BankAccountController(BankAccountRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<CreateBankAccountResponse> create(@RequestBody CreateBankAccountRequest request) {
        BankAccount saved = repository.save(new BankAccount(request.overdraftLimit));
        return new ResponseEntity<>(new CreateBankAccountResponse(saved.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleBankAccountView> findById(@PathVariable Long id) {
        BankAccount bankAccount = repository.findById(id);
        if (bankAccount != null) {
            return ResponseEntity.ok(new SingleBankAccountView(bankAccount.getOverdraftLimit(),
                    bankAccount.getBalanceInCents()));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Data
    static class CreateBankAccountRequest {
        long overdraftLimit;
    }

    @Value
    static class CreateBankAccountResponse {
        long bankAccountId;
    }

    @Data
    @AllArgsConstructor
    static class SingleBankAccountView {
        long overdraftLimit;
        long balance;
    }
}
