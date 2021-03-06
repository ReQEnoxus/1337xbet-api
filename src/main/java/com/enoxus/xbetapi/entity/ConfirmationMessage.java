package com.enoxus.xbetapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfirmationMessage {
    private String code;
    private String subject;
    private String targetMail;
}