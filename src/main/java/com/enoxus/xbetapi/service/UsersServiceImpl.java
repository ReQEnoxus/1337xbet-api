package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.UserDto;
import com.enoxus.xbetapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserDto> getUserByLogin(String login) {
        return Optional.of(userRepository.getUserDtoByLogin(login));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> UserDto.builder()
                        .avatarPath(user.getAvatar().getStorageFileName())
                        .balance(user.getBalance())
                        .email(user.getEmail())
                        .id(user.getId())
                        .lastName(user.getLastName())
                        .name(user.getName())
                        .login(user.getLogin())
                        .state(user.getState().name())
                        .build())
                .collect(Collectors.toList());
    }
}
