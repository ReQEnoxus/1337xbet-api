package com.enoxus.xbetapi.controllers;

import com.enoxus.xbetapi.dto.UserDto;
import com.enoxus.xbetapi.security.jwt.details.UserDetailsImpl;
import com.enoxus.xbetapi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/{user}")
    public ResponseEntity<UserDto> getUser(@PathVariable String user) {
        return ResponseEntity.of(usersService.getUserByLogin(user));

    }

    @GetMapping("/self")
    public ResponseEntity<UserDto> getSelf(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.of(usersService.getUserByLogin(userDetails.getLogin()));
    }
}
