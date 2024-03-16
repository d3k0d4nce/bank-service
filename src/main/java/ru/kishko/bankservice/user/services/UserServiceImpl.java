package ru.kishko.bankservice.user.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kishko.bankservice.user.dtos.change.UserChangeDTO;
import ru.kishko.bankservice.user.dtos.input.UserInputDTO;
import ru.kishko.bankservice.user.dtos.output.UserOutputDTO;
import ru.kishko.bankservice.user.entities.User;
import ru.kishko.bankservice.user.enums.UserSort;
import ru.kishko.bankservice.user.exceptions.LastContactException;
import ru.kishko.bankservice.user.exceptions.TransactionException;
import ru.kishko.bankservice.user.exceptions.UserNotFoundException;
import ru.kishko.bankservice.user.mappers.UserInputMapper;
import ru.kishko.bankservice.user.mappers.UserOutputMapper;
import ru.kishko.bankservice.user.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInputMapper userInputMapper;
    private final UserOutputMapper userOutputMapper;

    @Override
    public List<UserOutputDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userOutputMapper::map).toList();
    }

    @Override
    @Cacheable(value = "UserService::getUserById", key = "#userId")
    public UserOutputDTO getUserById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: " + userId)
        );

        return userOutputMapper.map(user);
    }

    @Override
    @Cacheable(value = "UserService::getUserByLogin", key = "#login")
    public UserOutputDTO getUserByLogin(String login) {

        User user = userRepository.getUserByLogin(login).orElseThrow(
                () -> new UserNotFoundException("There is no user with login: " + login)
        );

        return userOutputMapper.map(user);
    }

    @Override
    @CacheEvict(value = "UserService::getUserById", key = "#userId")
    public String deleteUserById(Long userId) {

        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException("There is no user with id: " + userId);
        }

        userRepository.deleteById(userId);

        return "successful deleted";

    }

    @Override
    @Caching(put = {
            @CachePut(value = "UserService::getUserById", key = "#userId"),
            @CachePut(value = "UserService::getUserByLogin", key = "#result.login")
    })
    public UserOutputDTO updateUserById(Long userId, UserChangeDTO userChangeDTO) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: " + userId)
        );

        String email = userChangeDTO.getEmail();
        String phone = userChangeDTO.getPhone();

        if (email != null && !email.isEmpty()) {
            user.setEmail(email);
        }
        if (phone != null && !phone.isEmpty()) {
            user.setPhone(phone);
        }

        userRepository.save(user);

        return userOutputMapper.map(user);
    }

    @Override
    @Caching(cacheable = {
            @Cacheable(value = "UserService::getUserByLogin", key = "#userDTO.login")
    })
    public UserOutputDTO createUser(UserInputDTO userDTO) {
        User user = userInputMapper.map(userDTO);
        User savedUser = userRepository.save(user); // Сохраняем и получаем объект обратно с присвоенным id
        System.out.println(savedUser);
        return userOutputMapper.map(savedUser);
    }


    @Override
    @Caching(put = {
            @CachePut(value = "UserService::getUserById", key = "#userId"),
            @CachePut(value = "UserService::getUserByLogin", key = "#result.login")
    })
    public UserOutputDTO deleteContactByUserId(Long userId, String contactType) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: " + userId)
        );

        if (contactType.equals("phone")) {
            if (user.getEmail() != null) {
                user.setPhone(null);
            } else {
                throw new LastContactException("Cannot delete the last contact");
            }
        } else if (contactType.equals("email")) {
            if (user.getPhone() != null) {
                user.setEmail(null);
            } else {
                throw new LastContactException("Cannot delete the last contact");
            }
        } else {
            throw new LastContactException("Invalid contact type");
        }

        userRepository.save(user);

        return userOutputMapper.map(user);
    }

    @Override
    @Transactional
    @Caching(put = {
            @CachePut(value = "UserService::getUserById", key = "#senderId"),
            @CachePut(value = "UserService::getUserByLogin", key = "#result.login")
    })
    public UserOutputDTO remittanceMoneyByUserId(Long senderId, Long recipientId, BigDecimal amount) {

        User sender = userRepository.findById(senderId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: " + senderId)
        );

        User recipient = userRepository.findById(recipientId).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: " + recipientId)
        );

        checkConstrains(sender, recipient, amount);

        var senderBalance = sender.getAccount().getBalance();
        var recipientBalance = recipient.getAccount().getBalance();
        sender.getAccount().setBalance(senderBalance.subtract(amount));
        recipient.getAccount().setBalance(recipientBalance.add(amount));

        userRepository.save(sender);
        userRepository.save(recipient);

        return userOutputMapper.map(sender);

    }


    private void checkConstrains(User sender, User recipient, BigDecimal amount) {

        // Проверка наличия суммы для перевода (больше нуля)
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException("Amount should be greater than zero.");
        }

        // Проверка наличия средств у отправителя
        if (sender.getAccount().getBalance().compareTo(amount) < 0) {
            throw new TransactionException("You don't have enough money to transfer it.");
        }

        // Обработка случаев перевода самому себе
        if (sender.getId().equals(recipient.getId())) {
            throw new TransactionException("Sender and recipient cannot be the same user.");
        }

    }

    @Override
    public Page<UserOutputDTO> searchUsers(Date birthDate, String phone, String lastName, String email, UserSort sort, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, sort.getSortValue());

        Specification<User> spec = Specification.where(null);

        if (birthDate != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThan(root.get("birthDate"), birthDate));
        }

        if (phone != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("phone"), phone));
        }

        if (lastName != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("lastName"), lastName + "%"));
        }

        if (email != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("email"), email));
        }

        Page<User> result = userRepository.findAll(spec, pageable);

        return result.map(userOutputMapper::map);
    }

    @Override
    @Caching(put = {
            @CachePut(value = "UserService::getUserById", key = "#result.id"),
            @CachePut(value = "UserService::getUserByLogin", key = "#result.login")
    })
    public UserOutputDTO updateUserInCache(UserOutputDTO user) {
        return user;
    }

}
