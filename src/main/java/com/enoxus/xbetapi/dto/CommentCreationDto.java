package com.enoxus.xbetapi.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentCreationDto {
    private Long matchId;
    private String text;
}
