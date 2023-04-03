package com.iablonski.springboot.shop.spring_online_shop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserInDataBaseValidator.class)
public @interface UserInDataBase {
    String message() default "Name already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}