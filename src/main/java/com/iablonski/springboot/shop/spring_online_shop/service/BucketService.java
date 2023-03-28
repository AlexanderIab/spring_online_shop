package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.entity.Bucket;
import com.iablonski.springboot.shop.spring_online_shop.entity.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productListById);

    void addProducts(Bucket bucket, List<Long> productListById);

    BucketDTO getBucketByUser(String name);

    void deleteProductFromBucket(String name, Long productId);

    void getOrderFromBucket(String name, String address);
}
