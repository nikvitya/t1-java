package ru.t1.java.firstService.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import aop.LogDataSourceError;
import dto.account.AccountDto;
import dto.account.NewAccountDto;
import dto.account.UpdatedAccountDto;
import dto.transaction.NewTransactionDto;
import ru.t1.java.general.model.Account;
import ru.t1.java.general.model.enums.AccountStatus;
import ru.t1.java.general.repository.AccountRepository;
import ru.t1.java.general.repository.ClientRepository;
import ru.t1.java.firstService.service.AccountService;
import ru.t1.java.general.util.AccountMapper;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class AccountServiceImpl implements AccountService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;


    @Override
    @LogDataSourceError
    @Transactional
    public AccountDto create(NewAccountDto newAccountDto) {
        checkClientExists(newAccountDto.getClientId());

        return mapper.toDto(accountRepository.save(mapper.toAccount(newAccountDto)));
    }

    @Override
    @Transactional
    @LogDataSourceError
    public AccountDto getById(Long clientId, Long accountId) {
        checkClientExists(clientId);

        return accountRepository.findById(accountId)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Счет с id = %d не найден", accountId)));
    }

    @Override
    @LogDataSourceError
    @Transactional
    public AccountDto update(@Valid UpdatedAccountDto updatedAccountDto) {
        Long accountId = updatedAccountDto.getAccountId();
        Long clientId = updatedAccountDto.getClientId();

        checkClientExists(clientId);

        Account accountFromDb = accountRepository.findByIdAndClientId(accountId, clientId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Пользователь с id = %d не имеет счета с id = %d", clientId, accountId)));

        if (updatedAccountDto.getBalance() != null) {
            accountFromDb.setBalance(updatedAccountDto.getBalance());
        }
        if (updatedAccountDto.getType() != null) {
            accountFromDb.setType(updatedAccountDto.getType());
        }

        return mapper.toDto(accountFromDb);
    }

    @Override
    @LogDataSourceError
    @Transactional
    public void delete(@Valid @Positive Long clientId,
                       @Valid @Positive Long accountId) {
        checkClientExists(clientId);

        if (!accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException(String.format("Счет с id = %d не найден", accountId));
        }

        accountRepository.deleteById(accountId);
    }

    @Override
    @LogDataSourceError
    public Collection<AccountDto> getAll(@Valid @Positive Long clientId) {
        checkClientExists(clientId);

        return accountRepository.findAllByClientId(clientId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @LogDataSourceError
    public boolean isOpenAccount(@Valid @Positive Long accountId) {
        return accountRepository.existsByIdAndStatus(accountId, AccountStatus.OPEN);
    }

    @Override
    @Transactional
    @LogDataSourceError
    public AccountDto updateAccountBalance(@Valid NewTransactionDto newTransactionDto) {
        checkClientExists(newTransactionDto.getClientId());

        Account accountFromDb = getAccountOrThrowException(newTransactionDto.getClientId(),
                newTransactionDto.getAccountId());

        accountFromDb.setBalance(accountFromDb.getBalance() - newTransactionDto.getAmount());

        return mapper.toDto(accountFromDb);
    }


    private void checkClientExists(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new EntityNotFoundException(String.format("Клиент с id = %d не найден", clientId));
        }
    }

    private Account getAccountOrThrowException(Long clientId, Long accountId) {
        return accountRepository.findByIdAndClientId(accountId, clientId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Пользователь с id = %d не имеет счета с id = %d", clientId, accountId)));
    }

}
