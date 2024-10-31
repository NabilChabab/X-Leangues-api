package com.example.x_leagues.web.api.v1.auth;
import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.model.enums.Role;
import com.example.x_leagues.web.vm.auth.LoginVM;
import com.example.x_leagues.web.vm.auth.ResponseVM;
import com.example.x_leagues.web.vm.auth.RegisterVM;
import com.example.x_leagues.web.vm.mapper.AppUserMapper;
import com.example.x_leagues.services.impl.AppUserServiceImpl;
import com.example.x_leagues.web.vm.mapper.LoginMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;


import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AppUserServiceImpl appUserService;
    private final AppUserMapper appUserMapper;
    private final LoginMapper loginMapper;

    public AuthController(AppUserServiceImpl appUserService, AppUserMapper appUserMapper, LoginMapper loginMapper, View error) {
        this.appUserService = appUserService;
        this.appUserMapper = appUserMapper;
        this.loginMapper = loginMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseVM> save(@RequestBody @Valid RegisterVM registerVM){
        AppUser appUser = appUserMapper.toEntity(registerVM);
        AppUser savedAppUser = appUserService.save(appUser);
        ResponseVM responseVM = appUserMapper.toVM(savedAppUser);
        return new ResponseEntity<>(responseVM, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<ResponseVM> login(@RequestBody LoginVM loginVm){
        AppUser appUser = loginMapper.toEntity(loginVm);
        AppUser appUser1 = appUserService.login(appUser.getUsername(), appUser.getPassword());
        ResponseVM responseVM = loginMapper.toVM(appUser1);
        String redirect;
        if (appUser1.getRole() == Role.ADMIN) {
            redirect = "/admin/dashboard";
        } else if (appUser1.getRole() == Role.MEMBER) {
            redirect = "/member/dashboard";
        } else if (appUser1.getRole() == Role.JURY) {
            redirect = "/jury/dashboard";
        } else {
            redirect = "/login";
        }

        responseVM.setRedirect(redirect);
        return new ResponseEntity<>(responseVM, HttpStatus.OK);
    }

}
