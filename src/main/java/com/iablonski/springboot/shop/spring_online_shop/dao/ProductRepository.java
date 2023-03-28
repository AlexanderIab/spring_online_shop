package com.iablonski.springboot.shop.spring_online_shop.dao;

import com.iablonski.springboot.shop.spring_online_shop.entity.CategoryEnum;
import com.iablonski.springboot.shop.spring_online_shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> getAllByCategory_CategoryEnum(CategoryEnum category_categoryEnum);

}
