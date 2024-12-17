package com.example.x_leagues;

import com.example.x_leagues.exceptions.ParticipationException;
import com.example.x_leagues.model.*;
import com.example.x_leagues.model.enums.Difficulty;
import com.example.x_leagues.repository.ParticipationRepository;
import com.example.x_leagues.services.dto.CompetitionHistoryDTO;
import com.example.x_leagues.services.dto.CompetitionResultDTO;
import com.example.x_leagues.services.dto.PodiumDTO;
import com.example.x_leagues.services.impl.HuntServiceImpl;
import com.example.x_leagues.services.impl.ParticipationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParticipationServiceImplTest {

    @Mock
    private ParticipationRepository participationRepository;

    @Mock
    private HuntServiceImpl huntService;

    @InjectMocks
    private ParticipationServiceImpl participationService;

    private Participation participation;
    private Competition competition;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock Participation, Competition, and AppUser objects for testing
        competition = new Competition();
        competition.setId(UUID.randomUUID());
        competition.setDate(LocalDateTime.now().plusDays(1));
        competition.setOpenRegistration(true);
        competition.setMaxParticipants(100);

        appUser = new AppUser();
        appUser.setId(UUID.randomUUID());
        appUser.setLicenseExpirationDate(LocalDateTime.now().plusMonths(1));

        participation = new Participation();
        participation.setId(UUID.randomUUID());
        participation.setCompetition(competition);
        participation.setAppUser(appUser);
    }

    @Test
    void save_ShouldThrowException_WhenUserOrCompetitionIsNull() {
        participation.setAppUser(null);

        Exception exception = assertThrows(ParticipationException.class, () -> {
            participationService.save(participation);
        });

        assertEquals("User ID or Competition ID cannot be null.", exception.getMessage());
    }

    @Test
    void save_ShouldThrowException_WhenRegistrationIsClosed() {
        competition.setOpenRegistration(false);

        Exception exception = assertThrows(ParticipationException.class, () -> {
            participationService.save(participation);
        });

        assertEquals("Registration is closed for this competition.", exception.getMessage());
    }

    @Test
    void save_ShouldThrowException_WhenLicenseIsExpired() {
        appUser.setLicenseExpirationDate(LocalDateTime.now().minusDays(1));

        Exception exception = assertThrows(ParticipationException.class, () -> {
            participationService.save(participation);
        });

        assertEquals("Participation not allowed: the user's license is expired.", exception.getMessage());
    }

    @Test
    void save_ShouldThrowException_WhenMaxParticipantsReached() {
        when(participationRepository.countByCompetitionId(any(UUID.class))).thenReturn((long) competition.getMaxParticipants());

        Exception exception = assertThrows(ParticipationException.class, () -> {
            participationService.save(participation);
        });

        assertEquals("Cannot join: The maximum number of participants for this competition has been reached.", exception.getMessage());
    }

    @Test
    void save_ShouldSaveParticipation_WhenValid() {
        when(participationRepository.save(any(Participation.class))).thenReturn(participation);

        Participation savedParticipation = participationService.save(participation);

        assertNotNull(savedParticipation);
        verify(participationRepository, times(1)).save(participation);
    }

    @Test
    void findAll_ShouldReturnPagedResults() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Participation> page = new PageImpl<>(List.of(participation));

        when(participationRepository.findAll(pageable)).thenReturn(page);

        Page<Participation> result = participationService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(participation, result.getContent().get(0));
    }

    @Test
    void getUserCompetitionResults_ShouldReturnCompetitionResults() {
        UUID userId = UUID.randomUUID();
        when(participationRepository.findByAppUserId(userId)).thenReturn(List.of(participation));

        List<CompetitionResultDTO> results = participationService.getUserCompetitionResults(userId);

        assertEquals(1, results.size());
        assertEquals(participation.getCompetition().getId(), results.get(0).getCompetitionId());
    }

    @Test
    void getCompetitionPodium_ShouldReturnPodiumResults() {
        UUID competitionId = UUID.randomUUID();
        when(participationRepository.findTop3ByCompetitionIdOrderByScoreDesc(competitionId)).thenReturn(List.of(participation));

        List<PodiumDTO> podium = participationService.getCompetitionPodium(competitionId);

        assertEquals(1, podium.size());
        assertEquals(participation.getId(), podium.get(0).getParticipantId());
    }

    @Test
    void getUserCompetitionHistory_ShouldReturnPagedCompetitionHistory() {
        UUID userId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Participation> page = new PageImpl<>(List.of(participation));

        when(participationRepository.findPastCompetitionsByAppUserId(userId, pageable)).thenReturn(page);

        Page<CompetitionHistoryDTO> history = participationService.getUserCompetitionHistory(userId, pageable);

        assertEquals(1, history.getTotalElements());
        assertEquals(participation.getCompetition().getId(), history.getContent().get(0).getCompetitionId());
    }

    @Test
    void updateScore_ShouldCalculateAndUpdateScore() {
        Hunt hunt = new Hunt();
        Species species = new Species();
        species.setPoints(10);
        species.setDifficulty(Difficulty.RARE);
        hunt.setSpecies(species);
        hunt.setWeight(5.0);

        when(huntService.findByParticipation(participation)).thenReturn(List.of(hunt));
        when(participationRepository.save(any(Participation.class))).thenReturn(participation);

        double updatedScore = participationService.updateScore(participation);

        assertEquals(17.5, updatedScore, 0.1);
    }

    @Test
    void findById_ShouldReturnParticipation_WhenFound() {
        UUID participationId = UUID.randomUUID();
        when(participationRepository.findById(participationId)).thenReturn(Optional.of(participation));

        Participation foundParticipation = participationService.findById(participationId);

        assertEquals(participation, foundParticipation);
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        UUID participationId = UUID.randomUUID();
        when(participationRepository.findById(participationId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            participationService.findById(participationId);
        });

        assertEquals("Species with id '" + participationId + "' does not exist.", exception.getMessage());
    }
}
