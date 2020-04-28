package com.enoxus.xbetapi.controllers;

import com.enoxus.xbetapi.dto.SignUpForm;
import com.enoxus.xbetapi.dto.SignUpResultDto;
import com.enoxus.xbetapi.exceptions.SignUpException;
import com.enoxus.xbetapi.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResultDto> signUp(@RequestBody @Valid SignUpForm form) {
        try {
            signUpService.signUp(form);
            return ResponseEntity.ok(SignUpResultDto.builder()
                    .success(true)
                    .build());
        } catch (SignUpException e) {
            return ResponseEntity.badRequest().body(SignUpResultDto.builder()
                    .success(false)
                    .reason(e.getMessage())
                    .build());
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SignUpResultDto handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return SignUpResultDto.builder()
                .success(false)
                .reason("Одно или несколько полей не прошли валидацию")
                .invalidFields(errors)
                .build();
    }

}
