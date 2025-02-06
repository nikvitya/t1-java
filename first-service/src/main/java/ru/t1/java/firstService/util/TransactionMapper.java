package ru.t1.java.firstService.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import dto.transaction.NewTransactionDto;
import dto.transaction.TransactionDto;
import ru.t1.java.general.model.Transaction;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "timestamp", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = "REQUESTED")
    Transaction toTransaction(NewTransactionDto newTransactionDto);

    TransactionDto toDto(Transaction transaction);
}