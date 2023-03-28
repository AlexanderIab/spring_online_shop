package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.dao.CategoryRepository;
import com.iablonski.springboot.shop.spring_online_shop.entity.Category;
import com.iablonski.springboot.shop.spring_online_shop.entity.CategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category getCategory(CategoryEnum categoryEnum) {
        return categoryRepository.getCategoryByCategoryEnum(categoryEnum);
    }

}
