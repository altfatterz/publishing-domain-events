package com.example;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zoltan Altfatter
 */
@RestController
public class BankTransferController {

    private final BankTransferService service;

    public BankTransferController(BankTransferService service) {
        this.service = service;
    }

    @PostMapping("/transfers")
    public void transfer(@RequestBody BankTransferRequest request) {
        service.completeTransfer(new BankTransfer(request.from, request.to, request.amountInCents));
    }

    static class BankTransferRequest {
        String from;
        String to;
        long amountInCents;
    }
}
