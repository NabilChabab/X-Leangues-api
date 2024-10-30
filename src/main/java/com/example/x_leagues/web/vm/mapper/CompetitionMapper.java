package com.example.x_leagues.web.vm.mapper;


import com.example.x_leagues.model.Competition;
import com.example.x_leagues.web.vm.competitions.CompetitionResponseVM;
import com.example.x_leagues.web.vm.competitions.CompetitionVm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    Competition toEntity(CompetitionVm competitionVm);
    CompetitionResponseVM toVM(Competition competition);
}
