package ru.t1.java.general.dto.transaction;

import lombok.Builder;
import lombok.Value;
import ru.t1.java.general.model.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class TransactionDto {
    Long id;
    Long accountId;
    Double amount;
    LocalDateTime createdAt;
    TransactionStatus status;
    UUID transactionId;
    LocalDateTime timestamp;
}