package ru.t1.java.demo.dto.account;

import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.t1.java.demo.model.AccountType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdatedAccountDto {
    Long clientId;
    Long accountId;

    @NotBlank
    @JsonProperty("type")
    AccountType type;

    @NotNull
    @JsonProperty("balance")
    Double balance;
}
