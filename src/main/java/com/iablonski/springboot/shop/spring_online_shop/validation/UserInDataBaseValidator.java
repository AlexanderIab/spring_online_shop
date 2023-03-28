package com.iablonski.springboot.shop.spring_online_shop.validation;

import com.iablonski.springboot.shop.spring_online_shop.dto.UserDTO;
import com.iablonski.springboot.shop.spring_online_shop.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserInDataBaseValidator implements ConstraintValidator<UserInDataBase, String> {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.findUserInDataBase(name);
    }
}