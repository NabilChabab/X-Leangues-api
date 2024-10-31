package com.example.x_leagues.web.vm.mapper;


import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.web.vm.auth.LoginVM;
import com.example.x_leagues.web.vm.auth.ResponseVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    AppUser toEntity(LoginVM loginVm);
    ResponseVM toVM(AppUser appUser);
}
