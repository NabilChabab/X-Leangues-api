package com.example.x_leagues.web.api.v1.competition;


import com.example.x_leagues.model.Competition;
import com.example.x_leagues.services.dto.CompetitionDetailsDTO;
import com.example.x_leagues.services.impl.CompetitionServiceImpl;
import com.example.x_leagues.web.vm.competitions.CompetitionResponseVM;
import com.example.x_leagues.web.vm.competitions.CompetitionVM;
import com.example.x_leagues.web.vm.mapper.CompetitionMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/competition")
public class CompetitionController {

    private final CompetitionServiceImpl competitionService;
    private final CompetitionMapper competitionMapper;

    public CompetitionController(CompetitionServiceImpl competitionService, CompetitionMapper competitionMapper) {
        this.competitionService = competitionService;
        this.competitionMapper = competitionMapper;
    }


    @PostMapping("/save")
    public ResponseEntity<CompetitionResponseVM> save(@RequestBody @Valid CompetitionVM competitionVm){
        Competition competition = competitionMapper.toEntity(competitionVm);
        Competition savedCompetition = competitionService.save(competition);
        CompetitionResponseVM competitionResponseVM = competitionMapper.toVM(savedCompetition);
        return new ResponseEntity<>(competitionResponseVM , HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<Page<CompetitionResponseVM>> findAll(Pageable pageable) {
        // Fetch paginated competitions from the service
        Page<CompetitionDetailsDTO> competitionDetailsDTOs = competitionService.findAll(pageable);

        // Map CompetitionDetailsDTO to CompetitionResponseVM using the mapper
        Page<CompetitionResponseVM> competitionResponseVMS = competitionDetailsDTOs.map(competitionMapper::toDetailsVM);

        // Return the response with HTTP status OK
        return ResponseEntity.ok(competitionResponseVMS);
    }


    @GetMapping("/{id}/details")
    @PreAuthorize("hasAuthority('CAN_VIEW_COMPETITIONS')")
    public ResponseEntity<CompetitionResponseVM> competitionDetails(@PathVariable UUID id){
        CompetitionDetailsDTO competitionDetailsDTO = competitionService.competitionDetails(id);
        CompetitionResponseVM competitionResponseVM = competitionMapper.toDetailsVM(competitionDetailsDTO);
        return new ResponseEntity<>(competitionResponseVM, HttpStatus.OK);
    }
}
