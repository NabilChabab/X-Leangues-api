package com.example.x_leagues.web.vm.mapper;


import com.example.x_leagues.model.Competition;
import com.example.x_leagues.services.dto.CompetitionDetailsDTO;
import com.example.x_leagues.web.vm.competitions.CompetitionResponseVM;
import com.example.x_leagues.web.vm.competitions.CompetitionVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    Competition toEntity(CompetitionVM competitionVm);
    CompetitionResponseVM toVM(Competition competition);
    @Mapping(target = "location", source = "location")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "numberOfParticipants", source = "numberOfParticipants")
    @Mapping(target = "openRegistration", source = "openRegistration")
    @Mapping(target = "code", source = "code")
    CompetitionResponseVM toDetailsVM(CompetitionDetailsDTO competitionDetailsDTO);
}
