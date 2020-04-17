package com.enoxus.xbetapi.controllers;

import com.enoxus.xbetapi.dto.SignInDto;
import com.enoxus.xbetapi.dto.SignInResultDto;
import com.enoxus.xbetapi.dto.TokenDto;
import com.enoxus.xbetapi.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SignInController {

    @Autowired
    private SignInService signInService;

    @PostMapping("/signIn")
    public ResponseEntity<SignInResultDto> signIn(@RequestBody SignInDto dto) {
        try {
            return ResponseEntity.ok(SignInResultDto.builder()
                    .success(true)
                    .token(signInService.signIn(dto))
                    .build());
        } catch (AccessDeniedException e) {
            return ResponseEntity.badRequest().body(SignInResultDto.builder()
                    .success(false)
                    .reason(e.getMessage())
                    .build());
        }
    }
}
