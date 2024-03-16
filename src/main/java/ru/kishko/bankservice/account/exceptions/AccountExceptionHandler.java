package ru.kishko.bankservice.account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(value = {AccountNotFoundException.class})
    public ResponseEntity<Object> handleProjectNotFoundException(AccountNotFoundException accountNotFoundException) {

        AccountException accountException = new AccountException(
                accountNotFoundException.getMessage(),
                accountNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(accountException, accountException.getHttpStatus());

    }

}
