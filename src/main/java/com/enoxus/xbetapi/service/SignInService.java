package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.SignInDto;
import com.enoxus.xbetapi.dto.TokenDto;

public interface SignInService {

    TokenDto signIn(SignInDto dto);
}
