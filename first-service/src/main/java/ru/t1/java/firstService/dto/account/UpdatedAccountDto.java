package ru.t1.java.firstService.dto.account;

import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.t1.java.general.model.enums.AccountType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdatedAccountDto {
    private Long clientId;
    private Long accountId;

    @NotBlank
    @JsonProperty("type")
    private AccountType type;

    @NotNull
    @JsonProperty("balance")
    private Double balance;
}
