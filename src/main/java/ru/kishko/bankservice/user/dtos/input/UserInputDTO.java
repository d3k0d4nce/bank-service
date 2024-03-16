package ru.kishko.bankservice.user.dtos.input;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class UserInputDTO implements Serializable {

    @Size(max = 20)
    @NotBlank
    private String login;

    @Size(max = 50)
    @NotBlank
    @Email
    private String email;

    @Size(max = 12)
    @NotBlank
    private String phone;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Size(max = 50)
    @NotBlank
    private String firstName;

    @Size(max = 50)
    @NotBlank
    private String middleName;

    @Size(max = 50)
    @NotBlank
    private String lastName;

    @NotNull
    @Min(0)
    private BigDecimal initialBalance;

    @Size(max = 32)
    @NotBlank
    private String password;

}
