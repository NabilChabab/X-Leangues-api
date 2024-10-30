package com.example.x_leagues.web.api.v1;
import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.services.dto.AppUserDTO;
import com.example.x_leagues.web.vm.LoginVm;
import com.example.x_leagues.web.vm.mapper.AppUserMapper;
import com.example.x_leagues.services.impl.AppUserServiceImpl;
import com.example.x_leagues.web.vm.AppUserVm;
import com.example.x_leagues.web.vm.mapper.LoginMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<AppUserDTO> save(@RequestBody @Valid AppUserVm appUserVm){
        AppUser appUser = appUserMapper.toEntity(appUserVm);
        AppUser savedAppUser = appUserService.save(appUser);
        AppUserDTO appUserDTO = appUserMapper.toDTO(savedAppUser);
        return new ResponseEntity<>(appUserDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> findById(@PathVariable UUID id){
        AppUser appUser = appUserService.findById(id);
        AppUserDTO appUserDTO = appUserMapper.toDTO(appUser);
        return new ResponseEntity<>(appUserDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AppUserDTO> login(@RequestBody LoginVm loginVm){
        AppUser appUser = loginMapper.toEntity(loginVm);
        AppUser appUser1 = appUserService.login(appUser.getUsername(), appUser.getPassword());
        AppUserDTO appUserDTO = loginMapper.toDTO(appUser1);
        return new ResponseEntity<>(appUserDTO, HttpStatus.OK);
    }

}
