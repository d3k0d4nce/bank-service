package ru.kishko.bankservice.account.services;

import ru.kishko.bankservice.account.dtos.input.AccountInputDTO;
import ru.kishko.bankservice.account.dtos.output.AccountOutputDTO;

import java.util.List;

public interface AccountService {
    String createAccount(AccountInputDTO accountInputDTO);

    List<AccountOutputDTO> getAllAccounts();

    AccountOutputDTO getAccountById(Long accountId);

}
