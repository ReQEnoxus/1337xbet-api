package com.enoxus.xbetapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BetResponseDto {
    private boolean success;
    private BetViewDto createdBet;
    private String reason;
}
