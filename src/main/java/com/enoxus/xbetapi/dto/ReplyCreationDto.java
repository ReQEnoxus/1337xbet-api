package com.enoxus.xbetapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyCreationDto {
    private Long matchId;
    private Long parentId;
    private String text;
}
