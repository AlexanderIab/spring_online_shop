package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.entity.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(User user);

    void saveNewUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    User findUserByName(String name);

    boolean findUserInDataBase(String name);

    void updateUserProfile(UserDTO userDTOLo);

    void deleteUser(Long id);

    void activateUser(String activationCode);

    void archiveUser(Long id);
}