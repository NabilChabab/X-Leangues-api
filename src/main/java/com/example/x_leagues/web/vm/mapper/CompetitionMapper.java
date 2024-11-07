package com.example.x_leagues.web.vm.mapper;


import com.example.x_leagues.model.Competition;
import com.example.x_leagues.services.dto.CompetitionDetailsDTO;
import com.example.x_leagues.web.vm.competitions.CompetitionResponseVM;
import com.example.x_leagues.web.vm.competitions.CompetitionVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    Competition toEntity(CompetitionVM competitionVm);
    CompetitionResponseVM toVM(Competition competition);
    CompetitionResponseVM toDetailsVM(CompetitionDetailsDTO competitionDetailsDTO);
}
