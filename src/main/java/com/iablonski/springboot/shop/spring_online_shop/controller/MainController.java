package com.iablonski.springboot.shop.spring_online_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping({"", "/"})
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")// page 404
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping({"users/menu.html",
            "products/menu.html",
            "/menu.html",
            "users/profile/?/menu.html"})
    public String menuCall() {
        return "menu";
    }
}