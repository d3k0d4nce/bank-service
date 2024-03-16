package ru.kishko.bankservice.account.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kishko.bankservice.account.dtos.output.AccountOutputDTO;
import ru.kishko.bankservice.account.entities.Account;

@Component
@RequiredArgsConstructor
public class AccountOutputMapper {

    public AccountOutputDTO map(Account account) {
        return AccountOutputDTO.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .initialBalance(account.getInitialBalance())
                .build();
    }

    public Account map(AccountOutputDTO accountOutputDTO) {
        return Account.builder()
                .id(accountOutputDTO.getId())
                .balance(accountOutputDTO.getBalance())
                .initialBalance(accountOutputDTO.getInitialBalance())
                .build();
    }

}
