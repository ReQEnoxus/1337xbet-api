package com.enoxus.xbetapi.security.jwt.provider;

import com.enoxus.xbetapi.repository.UserRepository;
import com.enoxus.xbetapi.security.jwt.authentication.JwtAuthentication;
import com.enoxus.xbetapi.security.jwt.details.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

// проверить аутентификацию пользователя
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    // секретный ключ, которым мы подписываем токен
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();

        Claims claims;
        try {
            // выполняю парсинг токена со своим секретным ключом
             claims =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Bad token");
        }

        // создаем UserDetails
        UserDetails userDetails = UserDetailsImpl.builder()
                .id(Long.parseLong(claims.get("sub", String.class)))
                .state(claims.get("state", String.class))
                .login(claims.get("login", String.class))
                .build();
        // аутентификация прошла успешно
        authentication.setAuthenticated(true);
        // положили в эту аутентификацию пользователя
        ((JwtAuthentication)authentication).setUserDetails(userDetails);
        return authentication;
    }

    // проверяет, подходит ли текущий провайдер для этой аутентификации
    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
