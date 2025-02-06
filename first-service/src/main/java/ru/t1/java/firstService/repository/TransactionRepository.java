package ru.t1.java.firstService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.general.model.Transaction;

import java.util.Collection;
import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
    Collection<Transaction> findAllByAccountId(Long accountId);
    Collection<Transaction> findAllByAccountIdIn(List<Long> accountIds);
}
