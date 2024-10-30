package com.example.x_leagues.web.vm.mapper;


import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.model.Competition;
import com.example.x_leagues.model.Participation;
import com.example.x_leagues.repository.AppUserRepository;
import com.example.x_leagues.repository.CompetitionRepository;
import com.example.x_leagues.web.vm.participation.ParticipationVM;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ParticipationMapper {

    private final AppUserRepository appUserRepository;
    private final CompetitionRepository competitionRepository;


    public ParticipationMapper(AppUserRepository appUserRepository, CompetitionRepository competitionRepository) {
        this.appUserRepository = appUserRepository;
        this.competitionRepository = competitionRepository;
    }


    public Participation toEntity(ParticipationVM participationVM) {
        Participation participation = new Participation();

        AppUser appUser = appUserRepository.findById(participationVM.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid App User ID"));
        participation.setAppUser(appUser);

        Competition competition = competitionRepository.findById(participationVM.getCompetitionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Competition ID"));
        participation.setCompetition(competition);

        participation.setScore(participationVM.getScore());

        return participation;
    }

    public ParticipationVM toVM(Participation participation) {
        ParticipationVM participationVM = new ParticipationVM();
        participationVM.setAppUserId(participation.getAppUser().getId());
        participationVM.setCompetitionId(participation.getCompetition().getId());
        participationVM.setScore(participation.getScore());
        return participationVM;
    }
}
