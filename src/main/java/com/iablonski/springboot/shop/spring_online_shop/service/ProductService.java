package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.dto.ProductDTO;
import com.iablonski.springboot.shop.spring_online_shop.entity.CategoryEnum;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    List<ProductDTO> getAllByCategory(CategoryEnum categoryEnum);
    void addToUserBucket(Long productId, String username);

    void addProduct(ProductDTO productDTO);

    void deleteProduct(Long id);

}
