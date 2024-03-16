package ru.kishko.bankservice.account.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class AccountException {

    private final String message;

    private final Throwable throwable;

    private final HttpStatus httpStatus;

}
