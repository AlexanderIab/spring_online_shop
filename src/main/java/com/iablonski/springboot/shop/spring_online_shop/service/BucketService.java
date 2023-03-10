package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.domain.Bucket;
import com.iablonski.springboot.shop.spring_online_shop.domain.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productListById);

    void addProducts(Bucket bucket, List<Long> productListById);

    BucketDTO getBucketByUser(String name);
}
