package ru.t1.java.general.dto.account;

import lombok.Builder;

import lombok.Value;
import ru.t1.java.general.model.enums.AccountStatus;
import ru.t1.java.general.model.enums.AccountType;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class AccountDto {
    Long id;
    Long clientId;
    AccountType type;
    Double balance;
    AccountStatus status;
    UUID accountId;;
    Double frozenAmount;
}
