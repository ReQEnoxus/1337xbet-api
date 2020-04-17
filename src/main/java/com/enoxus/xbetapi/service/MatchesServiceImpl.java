package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.MatchDto;
import com.enoxus.xbetapi.dto.MatchesSearchResult;
import com.enoxus.xbetapi.entity.Match;
import com.enoxus.xbetapi.repository.MatchesRepository;
import com.enoxus.xbetapi.util.BetHelper;
import com.enoxus.xbetapi.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchesServiceImpl implements MatchesService {

    @Autowired
    private DateHelper dateHelper;

    @Autowired
    private MatchesRepository matchesRepository;

    @Autowired
    private BetHelper betHelper;

    @Override
    public List<MatchDto> getAllByDate(String date) {
        Date initialDate = dateHelper.parse(date);
        Date dueDate = new Date(initialDate.getTime() + 86400000);

        return matchesRepository.findAllByDateBetween(initialDate, dueDate)
                .stream()
                .map(match -> MatchDto.builder()
                        .awayTeam(match.getAwayTeam())
                        .homeTeam(match.getHomeTeam())
                        .coefficients(betHelper.coefficientSet(match.getHomeTeam().getId(), match.getAwayTeam().getId()))
                        .id(match.getId())
                        .localizedDate(dateHelper.russianLocalized(match.getDate()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public MatchesSearchResult getByQuery(String query, String date, Integer page, Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Match> dtos = matchesRepository.search(query, new Date(), new Date(dateHelper.parse(date).getTime() + 86400000), pageRequest);
        int count = dtos.getTotalPages();
        List<MatchDto> dtoList = dtos
                .stream()
                .map(match -> MatchDto.builder()
                        .awayTeam(match.getAwayTeam())
                        .homeTeam(match.getHomeTeam())
                        .coefficients(betHelper.coefficientSet(match.getHomeTeam().getId(), match.getAwayTeam().getId()))
                        .id(match.getId())
                        .localizedDate(dateHelper.russianLocalized(match.getDate()))
                        .build())
                .collect(Collectors.toList());

        return MatchesSearchResult.builder()
                .matches(dtoList)
                .pagesCount(count)
                .build();

    }

    @Override
    public Optional<MatchDto> getById(Long id) {
        return matchesRepository.findById(id).map(match -> MatchDto.builder()
                .awayTeam(match.getAwayTeam())
                .homeTeam(match.getHomeTeam())
                .coefficients(betHelper.coefficientSet(match.getHomeTeam().getId(), match.getAwayTeam().getId()))
                .id(match.getId())
                .localizedDate(dateHelper.russianLocalized(match.getDate()))
                .build());
    }
}
