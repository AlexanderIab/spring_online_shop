package com.iablonski.springboot.shop.spring_online_shop.exception_handling.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}