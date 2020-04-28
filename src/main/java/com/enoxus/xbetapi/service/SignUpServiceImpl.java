package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.SignUpForm;
import com.enoxus.xbetapi.entity.ConfirmationMessage;
import com.enoxus.xbetapi.entity.FileInfo;
import com.enoxus.xbetapi.entity.State;
import com.enoxus.xbetapi.entity.User;
import com.enoxus.xbetapi.exceptions.SignUpException;
import com.enoxus.xbetapi.repository.FileInfoRepository;
import com.enoxus.xbetapi.repository.UserRepository;
import com.enoxus.xbetapi.util.FileStorageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private FileStorageUtil fileStorageUtil;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private SMSService smsService;

    @Override
    public void signUp(SignUpForm dto) {
        String raw = dto.getPassword();
        String encoded = passwordEncoder.encode(raw);

        userRepository.findUserByLogin(dto.getLogin()).ifPresent(user -> {
            throw new SignUpException("Логин уже занят");
        });

        FileInfo avatarFile = FileInfo.builder()
                .originalFileName("avi.png")
                .storageFileName("avi.png")
                .url(fileStorageUtil.getStoragePath() + "/" + "avi.png")
                .size(fileStorageUtil.sizeOf(fileStorageUtil.getStoragePath() + "/" + "avi.png"))
                .type("image/png")
                .build();

        fileInfoRepository.save(avatarFile);

        User user = User.builder()
                .login(dto.getLogin())
                .email(dto.getEmail())
                .balance(10_000d)
                .phoneNumber(dto.getPhoneNumber())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .password(encoded)
                .state(State.NOT_CONFIRMED)
                .confirmCode(UUID.randomUUID().toString())
                .avatar(avatarFile)
                .build();

        executorService.submit(() ->
                log.debug(smsService.sendSignUpSms("Вы успешно зарегистрировались. Подтвердите аккаунт, следуя инструкциям на почте", dto.getPhoneNumber()))
        );

        executorService.submit(() -> emailService.sendMail(ConfirmationMessage.builder()
                .code(user.getConfirmCode())
                .subject("Подтверждение регистрации")
                .targetMail(user.getEmail())
                .build()));

        userRepository.save(user);
    }
}