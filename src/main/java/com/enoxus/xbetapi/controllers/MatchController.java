package com.enoxus.xbetapi.controllers;

import com.enoxus.xbetapi.dto.MatchDto;
import com.enoxus.xbetapi.dto.MatchesSearchResult;
import com.enoxus.xbetapi.service.MatchesService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchesService matchesService;

    @GetMapping("/search")
    public ResponseEntity<MatchesSearchResult> search(String query, String date, Integer page, Integer size) {
        return ResponseEntity.ok(matchesService.getByQuery(query, date, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDto> getById(@PathVariable Long id) {
        return ResponseEntity.of(matchesService.getById(id));
    }
}
