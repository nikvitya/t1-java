package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.demo.dto.account.AccountDto;
import ru.t1.java.demo.dto.account.NewAccountDto;
import ru.t1.java.demo.dto.account.UpdatedAccountDto;
import ru.t1.java.demo.service.AccountService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequestMapping("/client/{clientId}/account")
@RequiredArgsConstructor
@Validated
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AccountDto create(@PathVariable @Positive Long clientId,
                             @RequestBody @Valid NewAccountDto newAccountDto) {
        return accountService.create(NewAccountDto.builder()
                .clientId(clientId)
                .type(newAccountDto.getType())
                .build());
    }

    @GetMapping
    public Collection<AccountDto> getAllByClientId(@PathVariable @Positive Long clientId) {
        return accountService.getAll(clientId);
    }

    @GetMapping("/{accountId}")
    public AccountDto getById(@PathVariable @Positive Long clientId,
                              @PathVariable @Positive Long accountId) {
        return accountService.getById(clientId, accountId);
    }

    @PatchMapping("/{accountId}")
    public AccountDto update(@PathVariable @Positive Long clientId,
                             @PathVariable @Positive Long accountId,
                             @Valid @RequestBody UpdatedAccountDto updatedAccountDto) {
        return accountService.update(updatedAccountDto.toBuilder()
                .clientId(clientId)
                .accountId(accountId)
                .build());
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long clientId,
                       @PathVariable Long accountId) {
        accountService.delete(clientId, accountId);
    }

}
