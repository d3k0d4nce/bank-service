package ru.kishko.bankservice.account.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kishko.bankservice.account.dtos.input.AccountInputDTO;
import ru.kishko.bankservice.account.dtos.output.AccountOutputDTO;
import ru.kishko.bankservice.account.entities.Account;
import ru.kishko.bankservice.account.exceptions.AccountNotFoundException;
import ru.kishko.bankservice.account.mappers.AccountInputMapper;
import ru.kishko.bankservice.account.mappers.AccountOutputMapper;
import ru.kishko.bankservice.account.repositories.AccountRepository;
import ru.kishko.bankservice.user.dtos.output.UserOutputDTO;
import ru.kishko.bankservice.user.entities.User;
import ru.kishko.bankservice.user.exceptions.UserNotFoundException;
import ru.kishko.bankservice.user.mappers.UserOutputMapper;
import ru.kishko.bankservice.user.repositories.UserRepository;
import ru.kishko.bankservice.user.services.UserService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountOutputMapper accountOutputMapper;
    private final AccountInputMapper accountInputMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserOutputMapper userOutputMapper;
    @Override
    public String createAccount(AccountInputDTO accountInputDTO) {

        accountRepository.save(accountInputMapper.map(accountInputDTO));

        return "successful created";
    }

    @Override
    public List<AccountOutputDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountOutputMapper::map).toList();
    }

    @Override
    public AccountOutputDTO getAccountById(Long accountId) {

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException("There is no account with id: " + accountId)
        );

        return accountOutputMapper.map(account);
    }

    @Scheduled(fixedRate = 60000)
    public void increaseBalance() {

        for (UserOutputDTO user : userService.getAllUsers()) {

            System.out.println(user);

            Account account = accountOutputMapper.map(user.getAccount());

            BigDecimal currentBalance = account.getBalance();
            BigDecimal initialDeposit = account.getInitialBalance();

            BigDecimal newBalance = currentBalance.multiply(BigDecimal.valueOf(1.05));

            if (newBalance.compareTo(initialDeposit.multiply(BigDecimal.valueOf(2.07))) > 0) {
                newBalance = initialDeposit.multiply(BigDecimal.valueOf(2.07));
            }

            account.setBalance(newBalance);

            // Сохраняем обновленный баланс в базе данных
            accountRepository.save(account);

            user.setAccount(accountOutputMapper.map(account));
            userService.updateUserInCache(user);
        }
    }

}
