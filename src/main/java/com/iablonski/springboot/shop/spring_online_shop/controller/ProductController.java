package com.iablonski.springboot.shop.spring_online_shop.controller;

import com.iablonski.springboot.shop.spring_online_shop.dto.ProductDTO;
import com.iablonski.springboot.shop.spring_online_shop.service.ProductService;
import com.iablonski.springboot.shop.spring_online_shop.service.SessionObjectHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final SessionObjectHolder sessionObjectHolder;

    @Autowired
    public ProductController(ProductService productService, SessionObjectHolder sessionObjectHolder) {
        this.productService = productService;
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @GetMapping
    public String listOfProducts(Model model){
        sessionObjectHolder.addClick();
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "products";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal){
        sessionObjectHolder.addClick();
        if (principal == null) return "redirect:/products";
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }
}
