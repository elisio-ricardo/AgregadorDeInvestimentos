package com.agregador.agregadorDeInvestimentos.controller;


import com.agregador.agregadorDeInvestimentos.controller.dto.AccountResponseDto;
import com.agregador.agregadorDeInvestimentos.controller.dto.CreateAccountDto;
import com.agregador.agregadorDeInvestimentos.controller.dto.CreateUserDto;
import com.agregador.agregadorDeInvestimentos.controller.dto.UpdateUserDto;
import com.agregador.agregadorDeInvestimentos.entity.User;
import com.agregador.agregadorDeInvestimentos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto body) {
        var userId = userService.createUser(body);
        return ResponseEntity.created(URI.create("/v1/user/" + userId.toString())).build();
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userID) {
        var user = userService.getUserById(userID);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.listAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
                                               @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount(@PathVariable("userId") String userId,
                                              @RequestBody CreateAccountDto createAccountDto) {
        userService.createAccount(userId, createAccountDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDto>> findAccountById(@PathVariable("userId") String userId) {
        var accounts = userService.listAccountsById(userId);
        return ResponseEntity.ok(accounts);
    }


}
