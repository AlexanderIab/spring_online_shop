package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean save(UserDTO userDTO);
}
