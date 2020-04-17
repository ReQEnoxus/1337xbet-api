package com.enoxus.xbetapi.controllers;

import com.enoxus.xbetapi.dto.*;
import com.enoxus.xbetapi.exceptions.BetCreatingException;
import com.enoxus.xbetapi.security.jwt.details.UserDetailsImpl;
import com.enoxus.xbetapi.service.BetService;
import com.enoxus.xbetapi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bets")
public class BetsController {

    @Autowired
    private BetService betService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/")
    public ResponseEntity<List<BetViewDto>> getMyBets(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("userDetails = " + userDetails);
        return ResponseEntity.ok(betService.getBetsByOwnerLogin(userDetails.getLogin()));
    }

    @PostMapping("/create")
    public ResponseEntity<BetResponseDto> makeBet(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BetDto betDto) {

        UserDto userDto = usersService.getUserByLogin(userDetails.getLogin()).get();

        try {
            return ResponseEntity.ok(BetResponseDto.builder()
                    .createdBet(betService.createBet(betDto, userDto))
                    .success(true)
                    .build());
        } catch (BetCreatingException e) {
            return ResponseEntity.badRequest().body(BetResponseDto
                    .builder()
                    .success(false)
                    .reason(e.getMessage())
                    .build());
        }
    }

}
