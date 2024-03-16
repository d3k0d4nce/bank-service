package ru.kishko.bankservice.user.dtos.output;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.kishko.bankservice.account.dtos.output.AccountOutputDTO;

import java.io.Serializable;

@Data
@Builder
public class UserOutputDTO implements Serializable {
    @NotNull
    private Long id;

    @NotEmpty
    @Size(max = 20)
    private String login;

    @Email
    @Size(max = 50)
    private String email;

    @Size(max = 12)
    private String phone;

    @NotNull
    private AccountOutputDTO account;
}
