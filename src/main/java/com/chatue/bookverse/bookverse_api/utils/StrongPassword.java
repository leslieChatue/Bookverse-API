package com.chatue.bookverse.bookverse_api.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
@Documented
public @interface StrongPassword {

    String message() default "Mot de passe invalide";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}