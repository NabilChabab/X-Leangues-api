package com.example.x_leagues.web.api.v1.participation;


import com.example.x_leagues.model.Participation;
import com.example.x_leagues.services.ParticipationService;
import com.example.x_leagues.services.dto.CompetitionResultDTO;
import com.example.x_leagues.services.dto.ScoreUpdateDTO;
import com.example.x_leagues.services.impl.ParticipationServiceImpl;
import com.example.x_leagues.web.vm.mapper.ParticipationMapper;
import com.example.x_leagues.web.vm.participation.ParticipationVM;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/participation")
public class ParticipationController {


    private final ParticipationService participationService;
    private final ParticipationMapper participationMapper;

    public ParticipationController(ParticipationService participationService, ParticipationMapper participationMapper) {
        this.participationService = participationService;
        this.participationMapper = participationMapper;
    }


    @PostMapping("/save")
    public ResponseEntity<ParticipationVM> save(@Valid @RequestBody ParticipationVM participationVM){
        Participation participation = participationMapper.toEntity(participationVM);
        Participation savedParticipation = participationService.save(participation);
        ParticipationVM participationVM1 = participationMapper.toVM(savedParticipation);
        return ResponseEntity.ok(participationVM1);
    }


    @GetMapping("/all")
    public ResponseEntity<Page<ParticipationVM>> findAll(Pageable pageable){
        Page<Participation> participations = participationService.findAll(pageable);
        Page<ParticipationVM> participationVM = participations.map(participationMapper::toVM);
        return ResponseEntity.ok(participationVM);
    }

    @PutMapping("/{participationId}/score")
    public ResponseEntity<String> updateScore(
            @PathVariable UUID participationId,
            @RequestBody ScoreUpdateDTO scoreUpdateDTO) {
        participationService.updateScore(participationId, scoreUpdateDTO.getScore());
        return ResponseEntity.ok("Score updated successfully");
    }

    @GetMapping("/{userId}/results")
    public List<CompetitionResultDTO> getUserCompetitionResults(@PathVariable UUID userId) {
        return participationService.getUserCompetitionResults(userId);
    }
}
