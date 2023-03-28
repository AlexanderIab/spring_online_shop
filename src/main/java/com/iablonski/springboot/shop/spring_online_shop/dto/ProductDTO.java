package com.iablonski.springboot.shop.spring_online_shop.dto;

import com.iablonski.springboot.shop.spring_online_shop.entity.CategoryEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    @NotBlank(message = "Must contain at least 2 characters")
    private String title;
    @Min(value = 1, message = "Must contain at least 1 digit")
    private double price;
    private CategoryEnum categoryEnum;
}
