package com.enoxus.xbetapi.repository;

import com.enoxus.xbetapi.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetsRepository extends JpaRepository<Bet, Long> {

    List<Bet> getAllByOwnerLogin(String login);
}
