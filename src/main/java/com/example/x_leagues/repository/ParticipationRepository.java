package com.example.x_leagues.repository;

import com.example.x_leagues.model.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<Participation , UUID> {
    List<Participation> findByAppUserId(UUID userId);

    long countByCompetitionId(UUID competitionId);


    @Query("SELECT p FROM Participation p WHERE p.competition.id = :competitionId ORDER BY p.score DESC LIMIT 3")
    List<Participation> findTop3ByCompetitionIdOrderByScoreDesc(@Param("competitionId") UUID competitionId);

    @Query("""
           SELECT p
           FROM Participation p
           JOIN FETCH p.competition c
           WHERE p.appUser.id = :appUserId
           AND c.date < CURRENT_TIMESTAMP
           ORDER BY c.date DESC
           """)
    Page<Participation> findPastCompetitionsByAppUserId(@Param("appUserId") UUID appUserId , Pageable pageable);
}
