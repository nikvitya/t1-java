package ru.t1.java.firstService.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import dto.account.AccountDto;
import dto.account.NewAccountDto;
import ru.t1.java.general.model.Account;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    @Mapping(target = "balance", constant = "0.0")
    Account toAccount(NewAccountDto newAccountDto);

    AccountDto toDto(Account account);
}
