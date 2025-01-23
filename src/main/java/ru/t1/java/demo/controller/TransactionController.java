package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.dto.transaction.TransactionDto;
import ru.t1.java.demo.service.TransactionService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequestMapping("/client/{clientId}")
@RequiredArgsConstructor
@Validated
public class TransactionController {
    private final TransactionService transactionService;

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
    public TransactionDto createTransaction(@PathVariable @Positive Long clientId,
                                            @PathVariable @Positive Long accountId,
                                            @RequestBody @Valid NewTransactionDto newTransactionDto) {
        return transactionService.create(newTransactionDto.toBuilder()
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