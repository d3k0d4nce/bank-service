package ru.kishko.bankservice.user.dtos.change;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserChangeDTO implements Serializable {

    @Email
    @Size(max = 50)
    private String email;

    @Size(max = 12)
    private String phone;

}

