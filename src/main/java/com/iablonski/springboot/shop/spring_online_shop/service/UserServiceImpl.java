package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.dao.UserRepository;
import com.iablonski.springboot.shop.spring_online_shop.entity.Role;
import com.iablonski.springboot.shop.spring_online_shop.entity.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MailSenderService mailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public boolean save(UserDTO userDTO) {
//        if (!Objects.equals((userDTO.getPassword()), userDTO.getPasswordConfirmation()))
//            throw new RuntimeException("Password is not equals");
        User user = User.builder()
                .name(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .build();
        if (userDTO.getRole() == null) {
            user.setRole(Role.GUEST);
            user.setActivateCode(UUID.randomUUID().toString());
            user.setActivated(false);
        } else {
            user.setRole(userDTO.getRole());
            user.setActivateCode(null);
            user.setActivated(true);
        }
        this.save(user);
        return true;
    }

    @Override
    public boolean findUserInDataBase(String name) {
        return userRepository.findFirstByName(name) != null;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
        if(user.getActivateCode() != null && !user.getActivateCode().isEmpty()){
            mailSenderService.sendActivateCode(user);
        }
    }

    @Override
    public List<UserDTO> getAll() {
        // Convert Stream User to Stream UserDTO
        return userRepository.findAll().stream().map(this::userToDTO).collect(Collectors.toList());
    }

    @Override
    public User findByName(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    public void updateProfile(UserDTO userDTO) {
        User savedUser = userRepository.findFirstByName(userDTO.getUsername());
        if (savedUser == null) throw new RuntimeException("User not found by name: " + userDTO.getUsername());
        boolean isChanged = false;
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));
            isChanged = true;
        }
        if (!Objects.equals(userDTO.getEmail(), savedUser.getEmail())) {
            savedUser.setEmail(userDTO.getEmail());
            isChanged = true;
        }
        if (isChanged) userRepository.save(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean activateUser(String activateCode) {
        if(activateCode == null || activateCode.isEmpty()){
            return false;
        }
        User user = userRepository.findFirstByActivateCode(activateCode);
        if(user == null){
            return false;
        }
        user.setActivateCode(null);
        user.setRole(Role.CLIENT);
        user.setActivated(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public void archiveUser(Long id) {
        User user = userRepository.findFirstById(id);
        if (user == null) throw new RuntimeException("User not found ");
        user.setArchive(true);
        user.setActivated(false);
        user.setActivateCode(null);
        userRepository.save(user);
    }

    private UserDTO userToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getName())
                .email(user.getEmail())
                .activated(user.isActivated())
                .role(user.getRole())
                .archived(user.isArchive())
                .build();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if (user == null || user.isArchive())
            throw new UsernameNotFoundException("User not fount with name: " + username);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails
                .User(user.getName(), user.getPassword(), roles);
    }


}
