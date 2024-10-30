package com.example.x_leagues.web.api.v1.auth;
import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.web.vm.auth.AuthResponseVM;
import com.example.x_leagues.web.vm.auth.LoginVm;
import com.example.x_leagues.web.vm.auth.RegisterVM;
import com.example.x_leagues.web.vm.mapper.AppUserMapper;
import com.example.x_leagues.services.impl.AppUserServiceImpl;
import com.example.x_leagues.web.vm.mapper.LoginMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AppUserController {


    private final AppUserServiceImpl appUserService;
    private final AppUserMapper appUserMapper;
    private final LoginMapper loginMapper;

    public AppUserController(AppUserServiceImpl appUserService, AppUserMapper appUserMapper, LoginMapper loginMapper) {
        this.appUserService = appUserService;
        this.appUserMapper = appUserMapper;
        this.loginMapper = loginMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseVM> save(@RequestBody @Valid RegisterVM registerVM){
        AppUser appUser = appUserMapper.toEntity(registerVM);
        AppUser savedAppUser = appUserService.save(appUser);
        AuthResponseVM authResponseVM = appUserMapper.toVM(savedAppUser);
        return new ResponseEntity<>(authResponseVM, HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<AuthResponseVM> findById(@PathVariable UUID id){
        AppUser appUser = appUserService.findById(id);
        AuthResponseVM authResponseVM = appUserMapper.toVM(appUser);
        return new ResponseEntity<>(authResponseVM, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseVM> login(@RequestBody LoginVm loginVm){
        AppUser appUser = loginMapper.toEntity(loginVm);
        AppUser appUser1 = appUserService.login(appUser.getUsername(), appUser.getPassword());
        AuthResponseVM authResponseVM = loginMapper.toVM(appUser1);
        return new ResponseEntity<>(authResponseVM, HttpStatus.OK);
    }

}
