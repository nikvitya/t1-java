package ru.t1.java.firstService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.general.enums.transaction.NewTransactionDto;
import ru.t1.java.general.enums.transaction.TransactionDto;
import ru.t1.java.general.kafka.KafkaTransactionProducer;
import ru.t1.java.general.service.TransactionService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequestMapping("/client/{clientId}")
@RequiredArgsConstructor
@Validated
public class TransactionController {
    private final TransactionService transactionService;
    private final KafkaTransactionProducer producer;

    @GetMapping("/transaction")
    public Collection<TransactionDto> getAllByClientId(@PathVariable @Positive Long clientId) {
        return transactionService.getAll(clientId);
    }

    @GetMapping("/account/{accountId}/transaction")
    public Collection<TransactionDto> getAllByAccountId(@PathVariable @Positive Long clientId,
                                                        @PathVariable @Positive Long accountId) {
        return transactionService.getAllByAccount(clientId, accountId);
    }


    @PostMapping("/account/{accountId}/transaction")
    public void createTransaction(@PathVariable @Positive Long clientId,
                                            @PathVariable @Positive Long accountId,
                                            @RequestBody @Valid NewTransactionDto newTransactionDto) {
        producer.send(newTransactionDto.toBuilder()
                .clientId(clientId)
                .accountId(accountId)
                .build());
    }

    @GetMapping("/transaction/{transactionId}")
    public TransactionDto getById(@PathVariable @Positive Long clientId,
                                  @PathVariable @Positive Long transactionId) {
        return transactionService.getById(clientId, transactionId);
    }
}