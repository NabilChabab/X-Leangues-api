package com.example.x_leagues.web.vm.mapper;


import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.services.dto.AppUserDTO;
import com.example.x_leagues.web.vm.LoginVm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    AppUser toEntity(LoginVm loginVm);
    AppUserDTO toDTO(AppUser appUser);
}
