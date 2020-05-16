package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.BetDto;
import com.enoxus.xbetapi.dto.BetViewDto;
import com.enoxus.xbetapi.dto.UserDto;
import com.enoxus.xbetapi.entity.Bet;
import com.enoxus.xbetapi.entity.Match;
import com.enoxus.xbetapi.entity.Prediction;
import com.enoxus.xbetapi.entity.User;
import com.enoxus.xbetapi.exceptions.BetCreatingException;
import com.enoxus.xbetapi.repository.BetsRepository;
import com.enoxus.xbetapi.repository.MatchesRepository;
import com.enoxus.xbetapi.repository.UserRepository;
import com.enoxus.xbetapi.util.BetHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BetServiceImpl implements BetService {

    @Autowired
    private BetsRepository betsRepository;

    @Autowired
    private MatchesService matchesService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BetHelper betHelper;

    @Autowired
    private MatchesRepository matchRepository;

    @Override
    public List<BetViewDto> getBetsByOwnerLogin(String login) {
        return userRepository.findUserByLogin(login).orElseThrow(IllegalArgumentException::new).getActiveBets().stream()
                .map(bet -> BetViewDto.builder()
                        .active(bet.isActive())
                        .amount(bet.getAmount())
                        .coefficient(bet.getCoefficient())
                        .prediction(bet.getPrediction().name())
                        .won(bet.isWon())
                        .match(matchesService.getById(bet.getMatch().getId()).get())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public BetViewDto createBet(BetDto betDto, UserDto userDto) {

        betsRepository.getAllByOwnerLogin(userDto.getLogin()).stream()
                .filter(b -> b.getMatch().getId().equals(betDto.getMatchId()))
                .findAny().ifPresent(b -> {
            throw new BetCreatingException("Вы уже поставили ставку на этот матч");
        });

        User user = userRepository.findById(userDto.getId()).<BetCreatingException>orElseThrow(() -> {
            throw new BetCreatingException("Пользователь не найден"); // не должно выкидываться в принципе
        });

        Match match = matchRepository.findById(betDto.getMatchId()).<BetCreatingException>orElseThrow(() -> {
            throw new BetCreatingException("Матч не найден"); // не должно выкидываться в принципе
        });

        Prediction prediction = Prediction.values()[betDto.getPrediction()];

        if (user.getBalance() < betDto.getAmount()) {
            throw new BetCreatingException("Не хватает денег");
        } else if (betDto.getAmount() < 1) {
            throw new BetCreatingException("Размер ставки должен быть положительным");
        } else if (new Date().after(match.getDate())) {
            throw new BetCreatingException("Матч уже начался или был сыгран");
        } else if (!"Not Started".equals(match.getStatus())) {
            throw new BetCreatingException("Матч уже начался или был сыгран");
        } else {
            user.setBalance(user.getBalance() - betDto.getAmount());
            userRepository.save(user);

            Bet bet = Bet.builder()
                    .active(true)
                    .amount(betDto.getAmount())
                    .coefficient(betHelper.calculateCoefficients(match.getHomeTeam().getId(), match.getAwayTeam().getId()).get(prediction))
                    .prediction(Prediction.values()[betDto.getPrediction()])
                    .match(match)
                    .owner(user)
                    .won(false)
                    .build();
            betsRepository.save(bet);

            return BetViewDto.builder()
                    .active(bet.isActive())
                    .amount(bet.getAmount())
                    .coefficient(bet.getCoefficient())
                    .prediction(bet.getPrediction().name())
                    .won(bet.isWon())
                    .match(matchesService.getById(bet.getMatch().getId()).get())
                    .build();
        }
    }
}
