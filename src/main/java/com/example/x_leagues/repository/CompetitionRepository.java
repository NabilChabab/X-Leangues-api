package com.example.x_leagues.repository;

import com.example.x_leagues.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface CompetitionRepository extends JpaRepository<Competition , UUID> {


    Optional<Competition> findByCode(String code);

    @Query("SELECT c FROM Competition c WHERE EXTRACT(YEAR FROM c.date) = EXTRACT(YEAR FROM CAST(:date AS timestamp)) " +
            "AND EXTRACT(WEEK FROM c.date) = EXTRACT(WEEK FROM CAST(:date AS timestamp))")
        List<Competition> findCompetitionsInSameWeek(@Param("date") LocalDateTime date);


    @Query("SELECT c FROM Competition c WHERE c.date BETWEEN :now AND :nowPlus24Hours")
    List<Competition> findCompetitionsStartingIn24Hours(@Param("now") LocalDateTime now, @Param("nowPlus24Hours") LocalDateTime nowPlus24Hours);

}
