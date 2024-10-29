package com.example.x_leagues.web.vm.mapper;


import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.services.dto.AppUserDTO;
import com.example.x_leagues.web.vm.AppUserVm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUser toEntity(AppUserVm appUserVm);
    AppUserDTO toDTO(AppUser appUser);
}
