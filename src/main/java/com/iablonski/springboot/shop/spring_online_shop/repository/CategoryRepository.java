package com.iablonski.springboot.shop.spring_online_shop.repository;


import com.iablonski.springboot.shop.spring_online_shop.entity.Category;
import com.iablonski.springboot.shop.spring_online_shop.entity.CategoryEnum;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category getCategoryByCategoryEnum(CategoryEnum categoryEnum);
}
