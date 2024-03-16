package ru.kishko.bankservice.user.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kishko.bankservice.account.entities.Account;
import ru.kishko.bankservice.user.dtos.input.UserInputDTO;
import ru.kishko.bankservice.user.entities.User;

@Component
@RequiredArgsConstructor
public class UserInputMapper {

    public User map(UserInputDTO userInputDTO) {
        return User.builder()
                .email(userInputDTO.getEmail())
                .login(userInputDTO.getLogin())
                .phone(userInputDTO.getPhone())
                .birthDate(userInputDTO.getBirthDate())
                .firstName(userInputDTO.getFirstName())
                .lastName(userInputDTO.getLastName())
                .middleName(userInputDTO.getMiddleName())
                .password(userInputDTO.getPassword())
                .account(Account.builder()
                        .balance(userInputDTO.getInitialBalance())
                        .initialBalance(userInputDTO.getInitialBalance())
                        .build())
                .build();
    }

}
