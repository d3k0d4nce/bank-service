package ru.kishko.bankservice.user.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleProjectNotFoundException(UserNotFoundException userNotFoundException) {

        UserException taskException = new UserException(
                userNotFoundException.getMessage(),
                userNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(taskException, taskException.getHttpStatus());

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка: ключ уже существует: " + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка: " + e.getMessage());
    }

    @ExceptionHandler(value = {LastContactException.class})
    public ResponseEntity<Object> handleProjectNotFoundException(LastContactException lastContactException) {

        UserException taskException = new UserException(
                lastContactException.getMessage(),
                lastContactException.getCause(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(taskException, taskException.getHttpStatus());

    }

    @ExceptionHandler(value = {TransactionException.class})
    public ResponseEntity<Object> handleNotEnoughMoneyException(TransactionException lastContactException) {

        UserException taskException = new UserException(
                lastContactException.getMessage(),
                lastContactException.getCause(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(taskException, taskException.getHttpStatus());

    }

}
