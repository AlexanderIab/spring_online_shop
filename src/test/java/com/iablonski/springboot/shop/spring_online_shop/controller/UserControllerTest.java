package com.iablonski.springboot.shop.spring_online_shop.controller;

import com.iablonski.springboot.shop.spring_online_shop.entity.Role;
import com.iablonski.springboot.shop.spring_online_shop.entity.User;
import com.iablonski.springboot.shop.spring_online_shop.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private User testUser1 = User.builder()
            .name("TestUser1")
            .id(1L)
            .role(Role.CLIENT)
            .build();

    private User testUser2 = User.builder()
            .name("TestUser2")
            .id(1L)
            .role(Role.CLIENT)
            .build();
}
