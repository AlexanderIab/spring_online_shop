package com.iablonski.springboot.shop.spring_online_shop.dao;

import com.iablonski.springboot.shop.spring_online_shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
