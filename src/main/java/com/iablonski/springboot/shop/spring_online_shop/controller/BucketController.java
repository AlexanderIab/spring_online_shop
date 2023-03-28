package com.iablonski.springboot.shop.spring_online_shop.controller;

import com.iablonski.springboot.shop.spring_online_shop.dto.BucketDTO;
import com.iablonski.springboot.shop.spring_online_shop.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService bucketService;

    @Autowired
    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping
    public String bucketInfo(Model model, Principal principal) {
        if (principal == null) model.addAttribute("bucket", new BucketDTO());
        else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDTO);
        }
        return "bucket";
    }

    @PostMapping
    public String getOrderFromBucket(@ModelAttribute("bucket") BucketDTO dto, Principal principal) {
        if (principal != null) bucketService.getOrderFromBucket(principal.getName(), dto.getAddress());
        return "redirect:/bucket";
    }

    @RequestMapping("/delete/{productId}")
    public String deleteFromBucket(@PathVariable Long productId, Principal principal) {
        bucketService.deleteProductFromBucket(principal.getName(), productId);
        return "redirect:/bucket";
    }
}
