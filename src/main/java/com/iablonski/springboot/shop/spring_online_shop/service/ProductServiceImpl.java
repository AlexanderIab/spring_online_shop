package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.exception_handling.exception.UserNotFoundException;
import com.iablonski.springboot.shop.spring_online_shop.repository.ProductRepository;
import com.iablonski.springboot.shop.spring_online_shop.entity.*;
import com.iablonski.springboot.shop.spring_online_shop.dto.ProductDTO;
import com.iablonski.springboot.shop.spring_online_shop.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              UserService userService,
                              BucketService bucketService,
                              CategoryService categoryService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
        this.categoryService = categoryService;
    }

    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = mapper.toDTO(product);
            productDTO.setCategoryEnum(product.getCategory().getCategoryEnum());
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    @Override
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findUserByName(username);
        if (user == null) throw new UserNotFoundException("User not found");
        Bucket bucket = user.getBucket();
        if (bucket == null) {
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        } else {
            bucketService.addProductsToBucket(bucket, Collections.singletonList(productId));
        }
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        Product product = mapper.toProduct(productDTO);
        Category category = categoryService.getCategory(productDTO.getCategoryEnum());
        product.setCategory(category);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}