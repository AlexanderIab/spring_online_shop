package com.iablonski.springboot.shop.spring_online_shop.controller;

import com.iablonski.springboot.shop.spring_online_shop.domain.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.UserDTO;
import com.iablonski.springboot.shop.spring_online_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.getAll());
        return "userList";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new UserDTO());
        return "user";
    }

    @PostAuthorize("isAuthenticated() and #username == authentication.principal.username")
    @GetMapping("/{name}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("name") String username){
        User userByName = userService.findByName(username);
        return userByName.getRole().name();
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("user") UserDTO userDTO){
        if(userService.save(userDTO)) return "redirect:/users";
        return "user";
    }

    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal){
        if(principal == null) throw new RuntimeException("You are not authorize");
        User user = userService.findByName(principal.getName());
        UserDTO userDTO = UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("user", userDTO);
        return "profile";
    }

    @PostMapping ("/profile")
    public String updateProfileUser(@ModelAttribute("user") UserDTO userDTO, Principal principal){
        // To keep the user from changing their name
        if(principal == null || !Objects.equals(principal.getName(), userDTO.getUsername())){
            throw new RuntimeException("You are not authorize");
        }
        if(userDTO.getPassword() != null
                && !userDTO.getPassword().isEmpty()
                && !Objects.equals(userDTO.getPassword(), userDTO.getPasswordConfirmation())){
            return "profile";
        }
        userService.updateProfile(userDTO);
        return "redirect:/users/profile";
    }
}
