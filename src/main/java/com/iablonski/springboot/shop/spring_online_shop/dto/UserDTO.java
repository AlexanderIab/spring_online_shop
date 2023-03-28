package com.iablonski.springboot.shop.spring_online_shop.dto;

import com.iablonski.springboot.shop.spring_online_shop.entity.Role;
import com.iablonski.springboot.shop.spring_online_shop.validation.MatchingPasswords;
import com.iablonski.springboot.shop.spring_online_shop.validation.UserInDataBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MatchingPasswords
public class UserDTO {
    private Long id;
    @UserInDataBase
    private String username;
    private String password;
    private String passwordConfirmation;
    private String email;
    private Role role;
    private boolean activated;
    private boolean archived;
}

