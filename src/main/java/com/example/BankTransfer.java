package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @author Zoltan Altfatter
 */
@Entity
@Data
@AllArgsConstructor
@ToString(exclude = "domainEvents")
public class BankTransfer {

    @Id
    private String id;
    private String sourceBankAccountId;
    private String destinationBankAccountId;
    private long amountInCents;
    private Status status;

    private transient final List<BankTransferCompletedEvent> domainEvents = new ArrayList<BankTransferCompletedEvent>();

    // JPA
    private BankTransfer() {
    }

    public BankTransfer(String sourceBankAccountId, String destinationBankAccountId, long amountInCents) {
        this.sourceBankAccountId = sourceBankAccountId;
        this.destinationBankAccountId = destinationBankAccountId;
        this.amountInCents = amountInCents;
        this.status = Status.CREATED;
    }

    public BankTransfer complete() {
        id = UUID.randomUUID().toString();
        registerEvent(new BankTransferCompletedEvent(id));
        return this;
    }

    @DomainEvents
    Collection<BankTransferCompletedEvent> domainEvents() {
        return domainEvents;
    }

    @AfterDomainEventPublication
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    protected void registerEvent(BankTransferCompletedEvent event) {
        Assert.notNull(event, "Domain event must not be null!");
        this.domainEvents.add(event);
    }

    enum Status {
        CREATED,
        STARTED,
        FAILED,
        COMPLETED
    }

    public void markStarted() {
        if (status != Status.CREATED) {
            throw new IllegalStateException(
                    String.format("Bank transfer must be in CREATED state to start processing! Current status: %s", this.status));
        }
        status = Status.STARTED;
    }

    public void markCompleted() {
        if (status != Status.STARTED) {
            throw new IllegalStateException(
                    String.format("Cannot complete a bank transfer that is currently not in STARTED mode! Current status: %s", this.status));
        }
        status = Status.COMPLETED;
    }


}
