package ru.t1.java.demo.kafka;

import dto.account.AccountDto;
import dto.transaction.NewTransactionDto;
import dto.transaction.TransactionAcceptDto;
import dto.transaction.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.general.service.AccountService;
import ru.t1.java.general.service.ClientService;
import ru.t1.java.general.service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class KafkaTransactionConsumer {
    private final TransactionService transactionService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final KafkaTransactionProducer producer;

    @KafkaListener(topics = {"${t1.kafka.topic.transactions.default}"},
            containerFactory = "kafkaTransactionListenerContainerFactory")
    public void listener(@Payload List<NewTransactionDto> newTransactionDtos,
                         Acknowledgment ack) {
        try {
            newTransactionDtos.stream()
                    .filter(newTransactionDto -> accountService.isOpenAccount(newTransactionDto.getAccountId()))
                    .forEach(newTransactionDto -> {
                        TransactionDto transactionFromDb = transactionService.create(newTransactionDto);
                        AccountDto accountFromDb = accountService.updateAccountBalance(newTransactionDto);
                        UUID clientUUID = clientService.getUUIDbyId(newTransactionDto.getClientId());

                        producer.send(TransactionAcceptDto.builder()
                                .clientId(clientUUID)
                                .accountId(accountFromDb.getAccountId())
                                .transactionId(transactionFromDb.getTransactionId())
                                .transactionAmount(newTransactionDto.getAmount())
                                .accountBalance(accountFromDb.getBalance())
                                .timestamp(LocalDateTime.now())
                                .build());
                    });
        } finally {
            ack.acknowledge();
        }
    }
}