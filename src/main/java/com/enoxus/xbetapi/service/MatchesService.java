package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.MatchDto;
import com.enoxus.xbetapi.dto.MatchesSearchResult;
import com.enoxus.xbetapi.entity.Match;

import java.util.List;
import java.util.Optional;

public interface MatchesService {

    List<MatchDto> getAllByDate(String date);
    MatchesSearchResult getByQuery(String query, String date, Integer page, Integer size);
    Optional<MatchDto> getById(Long id);
}
