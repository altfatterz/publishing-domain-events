package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Zoltan Altfatter
 */
@Entity
@Data
@AllArgsConstructor
@ToString(exclude = "domainEvents")
public class BankTransfer extends AbstractAggregateRoot {

    @Id
    private String id;
    private String sourceBankAccountId;
    private String destinationBankAccountId;
    private BigDecimal amount;
    private Status status;

    // JPA
    private BankTransfer() {
    }

    public BankTransfer(String sourceBankAccountId, String destinationBankAccountId, BigDecimal amount) {
        this.sourceBankAccountId = sourceBankAccountId;
        this.destinationBankAccountId = destinationBankAccountId;
        this.amount = amount;
        this.status = Status.CREATED;
    }

    public BankTransfer complete() {
        id = UUID.randomUUID().toString();
        registerEvent(new BankTransferCompletedEvent(id));
        return this;
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

    public void markFailed() {
        if (status != Status.STARTED) {
            throw new IllegalStateException(
                    String.format("Cannot fail a bank transfer that is currently not in STARTED mode! Current status: %s", this.status));
        }
        status = Status.FAILED;
    }


}
