package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Zoltan Altfatter
 */
@Component
@Slf4j
public class EventHandler {

    @EventListener
    public void handleBankAccountCreatedEvent(BankAccountCreatedEvent event) {
        log.info("handing event {}", event.getBankAccountId());
    }
}
