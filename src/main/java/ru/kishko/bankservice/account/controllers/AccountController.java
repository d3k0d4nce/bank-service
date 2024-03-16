package ru.kishko.bankservice.account.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.bankservice.account.dtos.input.AccountInputDTO;
import ru.kishko.bankservice.account.dtos.output.AccountOutputDTO;
import ru.kishko.bankservice.account.services.AccountService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountInputDTO accountInputDTO) {
        LOGGER.info("Creating account: {}", accountInputDTO);
        return new ResponseEntity<>(accountService.createAccount(accountInputDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AccountOutputDTO>> getAllAccounts() {
        LOGGER.info("Getting all accounts");
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountOutputDTO> getAccountById(@PathVariable("accountId") Long accountId) {
        LOGGER.info("Getting account by accountId: {}", accountId);
        return new ResponseEntity<>(accountService.getAccountById(accountId), HttpStatus.OK);
    }

}
