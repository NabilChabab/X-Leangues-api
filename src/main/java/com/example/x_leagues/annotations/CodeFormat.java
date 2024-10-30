package com.example.x_leagues.annotations;

import com.example.x_leagues.validations.CodeFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CodeFormatValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeFormat {

    String message() default "Code must be in the format location-date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
