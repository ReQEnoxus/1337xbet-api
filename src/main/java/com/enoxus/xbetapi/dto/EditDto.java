package com.enoxus.xbetapi.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EditDto {
    private String name;
    private String lastName;
    private Double balance;
    private String avatarPath;
}
