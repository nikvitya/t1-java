package ru.t1.java.firstService.service;

import dto.transaction.NewTransactionDto;
import dto.transaction.TransactionDto;

import java.util.Collection;

public interface TransactionService {

    Collection<TransactionDto> getAll(Long clientId);
    Collection<TransactionDto> getAllByAccount(Long clientId, Long accountId);

    TransactionDto create(NewTransactionDto newTransactionDto);

    TransactionDto getById(Long clientId, Long transactionId);

}
