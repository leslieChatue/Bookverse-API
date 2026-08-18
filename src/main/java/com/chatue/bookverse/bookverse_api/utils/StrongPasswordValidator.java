package com.chatue.bookverse.bookverse_api.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password,ConstraintValidatorContext context) {

        if (password == null) {
            return false;
        }

        // Au moins 8 caractères
        if (password.length() < 8) {
            return false;
        }

        // Une majuscule
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Une minuscule
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Un chiffre
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Un caractère spécial
        if (!password.matches(".*[@#$%^&+=!].*")) {
            return false;
        }

        return true;
    }
}
