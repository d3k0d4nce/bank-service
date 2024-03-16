package ru.kishko.bankservice.account.dtos.input;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class AccountInputDTO implements Serializable {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal balance;

}
