package com.iablonski.springboot.shop.spring_online_shop.controller;

import com.iablonski.springboot.shop.spring_online_shop.entity.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.OrderDTO;
import com.iablonski.springboot.shop.spring_online_shop.dto.UserDTO;
import com.iablonski.springboot.shop.spring_online_shop.service.OrderService;
import com.iablonski.springboot.shop.spring_online_shop.service.UserService;
import com.iablonski.springboot.shop.spring_online_shop.validation.PasswordCheck;
import com.iablonski.springboot.shop.spring_online_shop.validation.UsernameCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PreAuthorize("hasAnyAuthority({'ADMIN','MANAGER'})")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "usersList";
    }

    @PreAuthorize("!isAuthenticated() or hasAnyAuthority('ADMIN')")
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "newUser";
    }

    @PostMapping("/new")
    public String saveUser(@Validated({PasswordCheck.class, UsernameCheck.class}) @ModelAttribute("user") UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) return "newUser";
        userService.saveNewUser(userDTO);
        return "redirect:/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal) {
        User user = userService.findUserByName(principal.getName());
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .activated(user.isActivated())
                .build();
        model.addAttribute("userProfile", userDTO);
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile")
    public String updateUserProfile(@Validated(PasswordCheck.class) @ModelAttribute("userProfile") UserDTO userDTO,
                                    BindingResult result) {
        if (result.hasErrors()) return "profile";
        userService.updateUserProfile(userDTO);
        return "redirect:/users/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile/{id}/history")
    public String userProfileHistory(@PathVariable("id") Long id, Model model) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(id);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable("code") String activationCode) {
        userService.activateUser(activationCode);
        return "redirect:/login";
    }

    // This method removes user from the database permanently
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping("/{id}/delete")
    public String deleteUserByAdmin(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    // This method removes the user by himself,
    // but a record about him is saved in the database (field archived - true)
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/{id}/user-delete")
    public String deleteUserByUser(@PathVariable("id") Long id) {
        userService.archiveUser(id);
        return "redirect:/logout";
    }
}