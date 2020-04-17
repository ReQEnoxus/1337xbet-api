package com.enoxus.xbetapi.dto;

import com.enoxus.xbetapi.entity.CoefficientSet;
import com.enoxus.xbetapi.entity.Team;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MatchDto {
    private Long id;
    private Team homeTeam;
    private Team awayTeam;
    private CoefficientSet coefficients;
    private String localizedDate;
}
