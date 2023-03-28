package com.iablonski.springboot.shop.spring_online_shop.controller;

import com.iablonski.springboot.shop.spring_online_shop.dto.ProductDTO;
import com.iablonski.springboot.shop.spring_online_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listOfAllProducts(Model model) {
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        model.addAttribute("newProduct", new ProductDTO());
        return "products";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal) {
        if (principal == null) return "redirect:/products";
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PostMapping("/add")
    public String addProduct(@ModelAttribute("newProduct") ProductDTO productDTO) {
        productService.addProduct(productDTO);
        return "redirect:/products";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @RequestMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
