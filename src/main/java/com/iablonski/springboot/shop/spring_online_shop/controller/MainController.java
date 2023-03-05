package com.iablonski.springboot.shop.spring_online_shop.controller;

import com.iablonski.springboot.shop.spring_online_shop.service.SessionObjectHolder;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
public class MainController {

    private final SessionObjectHolder sessionObjectHolder;

    public MainController(SessionObjectHolder sessionObjectHolder) {
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @RequestMapping({"","/"})
    public String index(Model model, HttpSession session){
        model.addAttribute("amountClicks", sessionObjectHolder.getAmountClicks());
        if(session.getAttribute("myId") == null){
            String uuid = UUID.randomUUID().toString();
            session.setAttribute("myId", uuid);
            System.out.println("UUID -> " + uuid);
        }
        model.addAttribute("uuid", session.getAttribute("myId"));
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/login-error")// page 404
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        return "login";
    }
}
