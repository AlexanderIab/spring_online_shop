package com.iablonski.springboot.shop.spring_online_shop.validation;

import com.iablonski.springboot.shop.spring_online_shop.dto.UserDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MatchingPasswordsValidator implements ConstraintValidator<MatchingPasswords, UserDTO> {

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext constraintValidatorContext) {
        return userDTO.getPasswordConfirmation().equals(userDTO.getPassword());
    }
}