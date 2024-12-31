package com.example.x_leagues.web.vm.mapper;


import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.services.dto.UserParticipationCountDTO;
import com.example.x_leagues.web.vm.auth.ResponseVM;
import com.example.x_leagues.web.vm.auth.RegisterVM;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUser toEntity(RegisterVM registerVM);
    ResponseVM toVM(AppUser appUser);
    List<ResponseVM> toVMs(List<AppUser> appUsers);
}
