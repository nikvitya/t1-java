package ru.t1.java.firstService.service;

import dto.account.AccountDto;
import dto.account.NewAccountDto;
import dto.account.UpdatedAccountDto;
import dto.transaction.NewTransactionDto;

import java.util.Collection;

public interface AccountService {
    AccountDto create(NewAccountDto newAccountDto);

    AccountDto getById(Long clientId, Long accountId);

    AccountDto update(UpdatedAccountDto updatedAccountDto);

    void delete(Long clientId, Long accountId);

    Collection<AccountDto> getAll(Long clientId);
    boolean isOpenAccount(Long accountId);
    AccountDto updateAccountBalance(NewTransactionDto newTransactionDto);
}
