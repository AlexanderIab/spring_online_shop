package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.entity.Category;
import com.iablonski.springboot.shop.spring_online_shop.entity.CategoryEnum;

public interface CategoryService {

    Category getCategory (CategoryEnum categoryEnum);
}
