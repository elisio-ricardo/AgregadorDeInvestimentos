package com.agregador.agregadorDeInvestimentos.service;


import com.agregador.agregadorDeInvestimentos.controller.dto.AccountResponseDto;
import com.agregador.agregadorDeInvestimentos.controller.dto.CreateAccountDto;
import com.agregador.agregadorDeInvestimentos.controller.dto.CreateUserDto;
import com.agregador.agregadorDeInvestimentos.controller.dto.UpdateUserDto;
import com.agregador.agregadorDeInvestimentos.entity.Account;
import com.agregador.agregadorDeInvestimentos.entity.BillingAddress;
import com.agregador.agregadorDeInvestimentos.entity.User;
import com.agregador.agregadorDeInvestimentos.repository.AccountRepository;
import com.agregador.agregadorDeInvestimentos.repository.BillingAddressRepository;
import com.agregador.agregadorDeInvestimentos.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {
        var entity = new User(UUID.randomUUID(),
                createUserDto.usersame(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDto updateUserDto) {

        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.usarname() != null) {
                user.setUsername(updateUserDto.usarname());
            }

            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }


    public void deleteById(String userID) {
        var id = UUID.fromString(userID);
        var userExist = userRepository.existsById(id);

        if (userExist) {
            userRepository.deleteById(id);
        }
    }

    public void createAccount(String userId, CreateAccountDto createAccountDto) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account(
                UUID.randomUUID(),
                user,
                null,
                createAccountDto.description(),
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                account,
                createAccountDto.street(),
                createAccountDto.number()
        );

        billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDto> listAccountsById(String userId) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

       return user.getAccounts()
                .stream()
                .map(ac ->
                        new AccountResponseDto(ac.getAccountId().toString(), ac.getDescription()))
                .toList();
    }
}

   
