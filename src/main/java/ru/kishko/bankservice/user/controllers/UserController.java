package ru.kishko.bankservice.user.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.bankservice.user.dtos.change.UserChangeDTO;
import ru.kishko.bankservice.user.dtos.input.UserInputDTO;
import ru.kishko.bankservice.user.dtos.output.UserOutputDTO;
import ru.kishko.bankservice.user.enums.UserSort;
import ru.kishko.bankservice.user.services.UserService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserOutputDTO> createUser(@Valid @RequestBody UserInputDTO userDTO) {
        LOGGER.info("Creating user: {}", userDTO);
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDTO>> getAllUsers() {
        LOGGER.info("Getting all users");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserOutputDTO> getUserById(@PathVariable("userId") Long userId) {
        LOGGER.info("Getting user by userId: {}", userId);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<UserOutputDTO> getUserByLogin(@RequestParam("login") String login) {
        LOGGER.info("Getting user by login: {}", login);
        return new ResponseEntity<>(userService.getUserByLogin(login), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserOutputDTO> updateUserById(@PathVariable("userId") Long userId, @Valid @RequestBody UserChangeDTO userDTO) {
        LOGGER.info("Updating user by userId: {}", userId);
        return new ResponseEntity<>(userService.updateUserById(userId, userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{contact}")
    public ResponseEntity<UserOutputDTO> deleteContactByUserId(@PathVariable("userId") Long userId, @PathVariable("contact") String contactType) {
        LOGGER.info("Deleting user's {} by userId: {}", contactType, userId);
        return new ResponseEntity<>(userService.deleteContactByUserId(userId, contactType), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") Long userId) {
        LOGGER.info("Deleting user by userId: {}", userId);
        return new ResponseEntity<>(userService.deleteUserById(userId), HttpStatus.OK);
    }

    @PostMapping("/remittance/{senderId}/{recipientId}")
    public ResponseEntity<UserOutputDTO> remittanceMoneyByUserId(@PathVariable("senderId") Long senderId,
                                                                 @PathVariable("recipientId") Long recipientId,
                                                                 @RequestParam("amount") BigDecimal amount) {
        LOGGER.info("Transferring {} from user with id {} to user with id: {}", amount, senderId, recipientId);
        return new ResponseEntity<>(userService.remittanceMoneyByUserId(senderId, recipientId, amount), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserOutputDTO>> searchUsers(
            @RequestParam(name = "birthDate", required = false) Date birthDate,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(value = "sort", defaultValue = "ID_ASC") UserSort sort,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(userService.searchUsers(birthDate, phone, lastName, email, sort, page, size), HttpStatus.OK);
    }

}
