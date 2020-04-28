package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.entity.ConfirmationMessage;

public interface EmailService {
    void sendMail(ConfirmationMessage message);
}