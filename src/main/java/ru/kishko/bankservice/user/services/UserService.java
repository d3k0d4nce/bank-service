package ru.kishko.bankservice.user.services;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import ru.kishko.bankservice.user.dtos.change.UserChangeDTO;
import ru.kishko.bankservice.user.dtos.input.UserInputDTO;
import ru.kishko.bankservice.user.dtos.output.UserOutputDTO;
import ru.kishko.bankservice.user.enums.UserSort;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface UserService {
    List<UserOutputDTO> getAllUsers();

    UserOutputDTO getUserById(Long userId);

    UserOutputDTO getUserByLogin(String email);

    String deleteUserById(Long userId);

    UserOutputDTO updateUserById(Long userId, UserChangeDTO userChangeDTO);

    UserOutputDTO createUser(UserInputDTO userDTO);

    UserOutputDTO deleteContactByUserId(Long userId, String contactType);

    UserOutputDTO remittanceMoneyByUserId(Long senderId, Long recipientId, BigDecimal amount);

    Page<UserOutputDTO> searchUsers(Date birthDate, String phone, String lastName, String email, UserSort sort, int page, int size);

    @Caching(put = {
            @CachePut(value = "UserService::getUserById", key = "#user.id"),
            @CachePut(value = "UserService::getUserByLogin", key = "#user.login")
    })
    UserOutputDTO updateUserInCache(UserOutputDTO user);
}
