package com.example.x_leagues.validations;

import com.example.x_leagues.annotations.UniqueUsername;
import com.example.x_leagues.repository.AppUserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername , String> {
    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username != null && !appUserRepository.existsByUsername(username);
    }
}
