package com.example.x_leagues.web.api.v1.competition;


import com.example.x_leagues.model.Competition;
import com.example.x_leagues.services.impl.CompetitionServiceImpl;
import com.example.x_leagues.web.vm.competitions.CompetitionResponseVM;
import com.example.x_leagues.web.vm.competitions.CompetitionVM;
import com.example.x_leagues.web.vm.mapper.CompetitionMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CompetitionController {

    private final CompetitionServiceImpl competitionService;
    private final CompetitionMapper competitionMapper;

    public CompetitionController(CompetitionServiceImpl competitionService, CompetitionMapper competitionMapper) {
        this.competitionService = competitionService;
        this.competitionMapper = competitionMapper;
    }


    @PostMapping("/competition")
    public ResponseEntity<CompetitionResponseVM> save(@RequestBody @Valid CompetitionVM competitionVm){
        Competition competition = competitionMapper.toEntity(competitionVm);
        Competition savedCompetition = competitionService.save(competition);
        CompetitionResponseVM competitionResponseVM = competitionMapper.toVM(savedCompetition);
        return new ResponseEntity<>(competitionResponseVM , HttpStatus.CREATED);
    }


    @GetMapping("/competitions")
    public ResponseEntity<Page<CompetitionResponseVM>> findAll(Pageable pageable){
        Page<Competition> competitions = competitionService.findAll(pageable);
        Page<CompetitionResponseVM> competitionResponseVMS = competitions.map(competitionMapper::toVM);
        return new ResponseEntity<>(competitionResponseVMS, HttpStatus.OK);
    }
}
