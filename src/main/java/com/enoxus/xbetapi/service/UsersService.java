package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    Optional<UserDto> getUserByLogin(String login);
    List<UserDto> getAllUsers();
}
