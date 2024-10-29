package com.example.x_leagues.annotations;

import com.example.x_leagues.validations.UniqueEmailValidator;
import com.example.x_leagues.validations.UniqueUsernameValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

        String message() default "Email already exists";
        Class<?>[] groups() default {};
        Class<?>[] payload() default {};
}
