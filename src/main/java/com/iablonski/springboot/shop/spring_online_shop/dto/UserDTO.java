package com.iablonski.springboot.shop.spring_online_shop.dto;

import com.iablonski.springboot.shop.spring_online_shop.entity.Role;
import com.iablonski.springboot.shop.spring_online_shop.validation.MatchingPasswords;
import com.iablonski.springboot.shop.spring_online_shop.validation.PasswordCheck;
import com.iablonski.springboot.shop.spring_online_shop.validation.UserInDataBase;
import com.iablonski.springboot.shop.spring_online_shop.validation.UsernameCheck;

import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MatchingPasswords(groups = PasswordCheck.class)
public class UserDTO {
    private Long id;
    @UserInDataBase(groups = UsernameCheck.class)
    private String username;
    @Pattern(regexp = "^\\S+$", message = "Password must not contain spaces")
    private String password;
    private String passwordConfirmation;
    private String email;
    private Role role;
    private boolean activated;
    private boolean archived;
}

