package ru.t1.java.demo.service.impl;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.aop.LogDataSourceError;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.dto.transaction.TransactionDto;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.ClientRepository;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.TransactionService;
import ru.t1.java.demo.util.TransactionMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@LogDataSourceError
public class TransactionServiceImpl implements TransactionService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    @LogDataSourceError
    public Collection<TransactionDto> getAll(Long clientId) {
        checkClientExists(clientId);

        List<Long> accountIds = accountRepository.findAllByClientId(clientId).stream()
                .map(Account::getId)
                .collect(Collectors.toList());

        return transactionRepository.findAllByAccountIdIn(accountIds).stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @LogDataSourceError
    public Collection<TransactionDto> getAllByAccount(Long clientId, Long accountId) {
        checkClientExists(clientId);
        checkAccountExists(accountId);

        return transactionRepository.findAllByAccountId(accountId).stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @LogDataSourceError
    public TransactionDto create(NewTransactionDto newTransactionDto) {
        checkClientExists(newTransactionDto.getClientId());
        checkAccountExists(newTransactionDto.getAccountId());

        return transactionMapper.toDto(transactionRepository.save(transactionMapper.toTransaction(newTransactionDto)));
    }

    @Override
    @LogDataSourceError
    public TransactionDto getById(Long clientId, Long transactionId) {
        checkClientExists(clientId);

        return transactionRepository.findById(transactionId)
                .map(transactionMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Транзакция с id = %d не найдена", transactionId)));
    }

    private void checkAccountExists(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException(String.format("Счет с id = %d не найден", accountId));
        }
    }

    private void checkClientExists(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new EntityNotFoundException(String.format("Пользователь с id = %d не найден", clientId));
        }
    }
}
