package com.enoxus.xbetapi.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignUpForm {

    @NotNull
    @Pattern(regexp = "[A-Za-z]+")
    @Size(min = 6, max = 20)
    private String login;

    @NotNull
    @Pattern(regexp = "[А-Яа-я]+")
    private String name;

    @NotNull
    @Pattern(regexp = "[А-Яа-я]+")
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 20)
    private String password;

    @NotNull
    @Pattern(regexp = "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$")
    private String phoneNumber;
}
