package com.iablonski.springboot.shop.spring_online_shop.exception_handling.exception;

public class UserNotAuthorized extends RuntimeException{
    public UserNotAuthorized(String message) {
        super(message);
    }
}
