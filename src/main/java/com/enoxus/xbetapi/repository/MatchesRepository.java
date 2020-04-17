package com.enoxus.xbetapi.repository;

import com.enoxus.xbetapi.entity.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MatchesRepository extends JpaRepository<Match, Long> {

    List<Match> findAllByDateBetween(Date dateFrom, Date dateTo);

    @Query("from Match match where concat(upper(match.homeTeam.name), ' ', upper(match.awayTeam.name)) like concat('%', upper(:query), '%')" +
            "and match.status = 'Not Started'" +
            "and match.date between :dateFrom AND :dateTo")
    Page<Match> search(@Param("query") String query,
                       @Param("dateFrom") Date dateFrom,
                       @Param("dateTo") Date dateTo,
                       Pageable pageable);
}
