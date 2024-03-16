package ru.kishko.bankservice.account.dtos.output;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class AccountOutputDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal balance;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal initialBalance;

}
