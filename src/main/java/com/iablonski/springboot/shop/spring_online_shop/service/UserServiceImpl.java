package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.repository.UserRepository;
import com.iablonski.springboot.shop.spring_online_shop.entity.Role;
import com.iablonski.springboot.shop.spring_online_shop.entity.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.UserDTO;

import com.iablonski.springboot.shop.spring_online_shop.exception_handling.exception.UserNotAuthorized;
import com.iablonski.springboot.shop.spring_online_shop.exception_handling.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           MailSenderService mailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public void saveNewUser(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .build();
        if (userDTO.getRole() == null) {
            user.setRole(Role.GUEST);
            user.setActivationCode(UUID.randomUUID().toString());
            user.setActivated(false);
        } else {
            user.setRole(userDTO.getRole());
            user.setActivationCode(null);
            user.setActivated(true);
        }
        this.save(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
        if (user.getActivationCode() != null && !user.getActivationCode().isEmpty()) {
            mailSenderService.sendActivateCode(user);
        }
    }

    @Override
    public void updateUserProfile(UserDTO userDTO) {
        User savedUser = userRepository.findFirstByName(userDTO.getUsername());
        if (savedUser == null) {
            throw new UserNotFoundException("User not found with name: " + userDTO.getUsername());
        }
        boolean isChanged = false;
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            savedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            isChanged = true;
        }
        if (userDTO.getRole().equals(Role.ADMIN) && !Objects.equals(userDTO.getEmail(), savedUser.getEmail())) {
            savedUser.setEmail(userDTO.getEmail());
            isChanged = true;
        }
        if (!userDTO.getRole().equals(Role.ADMIN) && !Objects.equals(userDTO.getEmail(), savedUser.getEmail())) {
            savedUser.setEmail(userDTO.getEmail());
            savedUser.setRole(Role.GUEST);
            savedUser.setActivationCode(UUID.randomUUID().toString());
            savedUser.setActivated(false);
            mailSenderService.sendActivateCode(savedUser);
            isChanged = true;
        }
        if (isChanged) userRepository.save(savedUser);
    }

    @Override
    public void activateUser(String activationCode) {
        User user = userRepository.findFirstByActivationCode(activationCode);
        if (user == null) {
            throw new UserNotFoundException("User with this activation code was not found.");
        }
        user.setActivationCode(null);
        user.setRole(Role.CLIENT);
        user.setActivated(true);
        userRepository.save(user);
    }

    @Override
    public void archiveUser(Long id) {
        User user = userRepository.findFirstById(id);
        if (user == null) throw new UserNotFoundException("User not found");
        user.setArchive(true);
        user.setActivated(false);
        user.setActivationCode(null);
        userRepository.save(user);
    }

    @Override
    public boolean findUserInDataBase(String name) {
        return userRepository.findFirstByName(name) != null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::userToDTO).collect(Collectors.toList());
    }

    @Override
    public User findUserByName(String name) {
        User user = userRepository.findFirstByName(name);
        if (user == null) throw new UserNotAuthorized("User not authorized");
        else return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if (user == null || user.isArchive()) {
            throw new UsernameNotFoundException("User not found with name: " + username);
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), roles);
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
}