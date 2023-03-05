package com.iablonski.springboot.shop.spring_online_shop.dao;

import com.iablonski.springboot.shop.spring_online_shop.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket,Long> {
}
