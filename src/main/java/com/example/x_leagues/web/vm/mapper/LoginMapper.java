package com.example.x_leagues.web.vm.mapper;


import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.web.vm.auth.AuthResponseVM;
import com.example.x_leagues.web.vm.auth.LoginVm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    AppUser toEntity(LoginVm loginVm);
    AuthResponseVM toVM(AppUser appUser);
}
