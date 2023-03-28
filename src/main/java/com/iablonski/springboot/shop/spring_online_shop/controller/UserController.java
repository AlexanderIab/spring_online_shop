package com.iablonski.springboot.shop.spring_online_shop.controller;

import com.iablonski.springboot.shop.spring_online_shop.entity.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.OrderDTO;
import com.iablonski.springboot.shop.spring_online_shop.dto.UserDTO;
import com.iablonski.springboot.shop.spring_online_shop.service.OrderService;
import com.iablonski.springboot.shop.spring_online_shop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Objects;

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

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "usersList";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "newUser";
    }

    @PostMapping("/new")
    public String saveUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Principal principal) {
        if (result.hasErrors()) return "newUser";
        userService.save(userDTO);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal) {
        if (principal == null) throw new RuntimeException("You are not authorize");
        User user = userService.findByName(principal.getName());
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getName())
                .activated(user.getActivateCode() == null)
                .email(user.getEmail())
                .activated(user.isActivated())
                .build();
        model.addAttribute("userProfile", userDTO);
        return "profile";
    }

    @GetMapping("/profile/{id}/history")
    public String profileHistory(@PathVariable Long id, Model model) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(id);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/profile")
    public String updateProfileUser(@ModelAttribute("userProfile") UserDTO userDTO, Principal principal) {
        // To keep the user from changing their name
        if (principal == null || !Objects.equals(principal.getName(), userDTO.getUsername())) {
            throw new RuntimeException("You are not authorize");
        }
        if (userDTO.getPassword() != null
                && !userDTO.getPassword().isEmpty()
                && !Objects.equals(userDTO.getPassword(), userDTO.getPasswordConfirmation())) {
            return "profile";
        }
        userService.updateProfile(userDTO);
        return "profile";
    }

    // This method removes the user from the database permanently
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping("/{id}/delete")
    public String deleteUserByAdmin(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    // This method removes the user,
    // but a record about him is saved in the database (field archived - true)
    @RequestMapping("/{id}/user-delete")
    public String deleteUserByUser(@PathVariable("id") Long id) {
        userService.archiveUser(id);
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable("code") String activateCode) {
        boolean activated = userService.activateUser(activateCode);
        return "redirect:/login";
    }
}
