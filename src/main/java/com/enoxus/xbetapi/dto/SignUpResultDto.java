package com.enoxus.xbetapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResultDto {

    private boolean success;
    private Map<String, String> invalidFields;
    private String reason;
}
