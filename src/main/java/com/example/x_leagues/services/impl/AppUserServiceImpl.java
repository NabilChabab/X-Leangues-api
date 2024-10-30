package com.example.x_leagues.services.impl;


import com.example.x_leagues.exceptions.UserAlreadyExistException;
import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.services.AppUserService;
import com.example.x_leagues.exceptions.UserNotFoundException;
import com.example.x_leagues.repository.AppUserRepository;
import com.example.x_leagues.utils.PasswordEncoderUtil;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoderUtil passwordEncoder;

    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoderUtil passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public AppUser save(AppUser appUser) {
        String encodedPassword = passwordEncoder.encodePassword(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        Optional<AppUser> appUserOptional = appUserRepository.findByUsernameOrEmail(appUser.getUsername(), appUser.getEmail());
        if (appUserOptional.isPresent()){
            throw new UserAlreadyExistException("User already exists");
        }
        return appUserRepository.save(appUser);
    }

    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public AppUser findById(UUID id){
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("AppUser not found with id : " + id));
        return appUser;
    }

    @Override
    public AppUser login(String username, String password) {
        AppUser appUser = appUserRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("AppUser not found with username : " + username));
        if (!checkPassword(password, appUser.getPassword())){
            throw new UserNotFoundException("Invalid password");
        }
        return appUser;
    }


}
