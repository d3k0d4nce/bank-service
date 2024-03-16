package ru.kishko.bankservice.user.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kishko.bankservice.account.dtos.output.AccountOutputDTO;
import ru.kishko.bankservice.account.entities.Account;
import ru.kishko.bankservice.user.dtos.output.UserOutputDTO;
import ru.kishko.bankservice.user.entities.User;

@Component
@RequiredArgsConstructor
public class UserOutputMapper {

    public User map(UserOutputDTO userOutputDTO) {
        return User.builder()
                .id(userOutputDTO.getId())
                .login(userOutputDTO.getLogin())
                .email(userOutputDTO.getEmail())
                .phone(userOutputDTO.getPhone())
                .account(Account.builder()
                        .id(userOutputDTO.getAccount().getId())
                        .balance(userOutputDTO.getAccount().getBalance())
                        .initialBalance(userOutputDTO.getAccount().getInitialBalance())
                        .build()
                )
                .build();
    }

    public UserOutputDTO map(User user) {
        return UserOutputDTO.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .login(user.getLogin())
                .email(user.getEmail())
                .account(AccountOutputDTO.builder()
                        .id(user.getAccount().getId())
                        .balance(user.getAccount().getBalance())
                        .initialBalance(user.getAccount().getInitialBalance())
                        .build()
                )
                .build();
    }

}
