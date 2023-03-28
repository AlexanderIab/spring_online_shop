package com.iablonski.springboot.shop.spring_online_shop.dao;

import com.iablonski.springboot.shop.spring_online_shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);
    User findFirstByActivateCode(String activateCode);
    User findFirstById(Long id);
}
