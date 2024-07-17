package com.agregador.agregadorDeInvestimentos.service;


import com.agregador.agregadorDeInvestimentos.controller.dto.CreateUserDto;
import com.agregador.agregadorDeInvestimentos.controller.dto.UpdateUserDto;
import com.agregador.agregadorDeInvestimentos.entity.User;
import com.agregador.agregadorDeInvestimentos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
