package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.BetDto;
import com.enoxus.xbetapi.dto.BetViewDto;
import com.enoxus.xbetapi.dto.UserDto;

import java.util.List;

public interface BetService {

    List<BetViewDto> getBetsByOwnerLogin(String login);

    BetViewDto createBet(BetDto betDto, UserDto userDto);
}
