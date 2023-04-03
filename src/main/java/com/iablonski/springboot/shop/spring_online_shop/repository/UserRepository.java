package com.iablonski.springboot.shop.spring_online_shop.repository;

import com.iablonski.springboot.shop.spring_online_shop.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);

    User findFirstByActivationCode(String activationCode);

    User findFirstById(Long id);
}
