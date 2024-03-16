package ru.kishko.bankservice.user.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.kishko.bankservice.account.dtos.output.AccountOutputDTO;
import ru.kishko.bankservice.account.entities.Account;
import ru.kishko.bankservice.user.dtos.output.UserOutputDTO;
import ru.kishko.bankservice.user.entities.User;
import ru.kishko.bankservice.user.mappers.UserInputMapper;
import ru.kishko.bankservice.user.mappers.UserOutputMapper;
import ru.kishko.bankservice.user.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserOutputMapper userOutputMapper;
    @Mock
    private UserInputMapper userInputMapper;
    @InjectMocks
    private UserServiceImpl userService;

    private UserOutputDTO user3;
    private UserOutputDTO user4;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, userInputMapper, userOutputMapper);

        Account account1 = Account.builder()
                .id(1L)
                .initialBalance(BigDecimal.valueOf(500.0))
                .balance(BigDecimal.valueOf(1000.0))
                .build();

        Account account2 = Account.builder()
                .id(2L)
                .initialBalance(BigDecimal.valueOf(1000.0))
                .balance(BigDecimal.valueOf(1000.0))
                .build();

        AccountOutputDTO account3 = AccountOutputDTO.builder()
                .id(1L)
                .initialBalance(BigDecimal.valueOf(500.0))
                .balance(BigDecimal.valueOf(1000.0))
                .build();

        AccountOutputDTO account4 = AccountOutputDTO.builder()
                .id(2L)
                .initialBalance(BigDecimal.valueOf(1000.0))
                .balance(BigDecimal.valueOf(1000.0))
                .build();

        User user1 = User.builder()
                .id(1L)
                .login("d3k0d4nce")
                .email("kishko.2003@list.ru")
                .phone("89198210825")
                .account(account1)
                .build();

        User user2 = User.builder()
                .id(2L)
                .login("d3k0")
                .email("kishko.2004@list.ru")
                .phone("89198210826")
                .account(account2)
                .build();

        user3 = UserOutputDTO.builder()
                .id(1L)
                .login("d3k0d4nce")
                .email("kishko.2003@list.ru")
                .phone("89198210825")
                .account(account3)
                .build();

        user4 = UserOutputDTO.builder()
                .id(2L)
                .login("d3k0")
                .email("kishko.2004@list.ru")
                .phone("89198210826")
                .account(account4)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        // Set up expected behavior for mappers
        when(userOutputMapper.map(user1)).thenReturn(user3);
        when(userOutputMapper.map(user2)).thenReturn(user4);
    }

    @Test
    void remittanceMoneyByUserId() {
        BigDecimal amount = BigDecimal.valueOf(200.0);
        Long senderId = 1L;
        Long recipientId = 2L;

        // Set up the initial sender and recipient account balances
        BigDecimal senderInitialBalance = user3.getAccount().getBalance();
        BigDecimal recipientInitialBalance = user4.getAccount().getBalance();

        // Call the method under test
        UserOutputDTO result = userService.remittanceMoneyByUserId(senderId, recipientId, amount);

        // Calculate the expected balances after remittance
        BigDecimal expectedSenderBalance = senderInitialBalance.subtract(amount);
        BigDecimal expectedRecipientBalance = recipientInitialBalance.add(amount);

        // Check if the result matches the expected user output
        assertThat(result).isEqualTo(user3);

        // Check if the recipient's account balance is updated correctly
        assertThat(userRepository.findById(recipientId).get().getAccount().getBalance()).isEqualTo(expectedRecipientBalance);
        assertThat(userRepository.findById(senderId).get().getAccount().getBalance()).isEqualTo(expectedSenderBalance);
    }
}