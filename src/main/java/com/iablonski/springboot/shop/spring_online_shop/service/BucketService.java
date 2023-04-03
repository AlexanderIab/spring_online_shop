package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.entity.Bucket;
import com.iablonski.springboot.shop.spring_online_shop.entity.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    BucketDTO getBucketByUser(String name);

    void getOrderFromBucket(String name, String address);

    Bucket createBucket(User user, List<Long> productListById);

    void addProductsToBucket(Bucket bucket, List<Long> productListById);

    void deleteProductFromBucket(String name, Long productId);
}
