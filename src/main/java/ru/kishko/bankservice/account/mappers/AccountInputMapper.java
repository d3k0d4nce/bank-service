package ru.kishko.bankservice.account.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kishko.bankservice.account.dtos.input.AccountInputDTO;
import ru.kishko.bankservice.account.entities.Account;

@Component
@RequiredArgsConstructor
public class AccountInputMapper {
    public AccountInputDTO map(Account account) {
        return AccountInputDTO.builder()
                .balance(account.getBalance())
                .build();
    }

    public Account map(AccountInputDTO accountInputDTO) {
        return Account.builder()
                .balance(accountInputDTO.getBalance())
                .initialBalance(accountInputDTO.getBalance())
                .build();
    }

}

