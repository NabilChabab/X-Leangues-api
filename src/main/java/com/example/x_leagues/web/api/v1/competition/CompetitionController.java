package com.example.x_leagues.web.api.v1.competition;


import com.example.x_leagues.model.Competition;
import com.example.x_leagues.services.impl.CompetitionServiceImpl;
import com.example.x_leagues.web.vm.competitions.CompetitionResponseVM;
import com.example.x_leagues.web.vm.competitions.CompetitionVm;
import com.example.x_leagues.web.vm.mapper.CompetitionMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CompetitionResponseVM> save(@RequestBody @Valid CompetitionVm competitionVm){
        Competition competition = competitionMapper.toEntity(competitionVm);
        Competition savedCompetition = competitionService.save(competition);
        CompetitionResponseVM competitionResponseVM = competitionMapper.toVM(savedCompetition);
        return new ResponseEntity<>(competitionResponseVM , HttpStatus.CREATED);
    }
}
