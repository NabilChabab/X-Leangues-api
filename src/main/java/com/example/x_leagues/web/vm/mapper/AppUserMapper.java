package com.example.x_leagues.web.vm.mapper;


import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.web.vm.auth.AuthResponseVM;
import com.example.x_leagues.web.vm.auth.RegisterVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUser toEntity(RegisterVM registerVM);
    AuthResponseVM toVM(AppUser appUser);
}
