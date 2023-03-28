package com.iablonski.springboot.shop.spring_online_shop.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandling(Exception exception, Model model){
        String errorMessage = (exception != null ? exception.getMessage(): "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
