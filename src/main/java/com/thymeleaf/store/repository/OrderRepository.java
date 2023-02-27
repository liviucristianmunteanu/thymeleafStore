package com.thymeleaf.store.repository;

import com.thymeleaf.store.entity.Order;
import com.thymeleaf.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAll();

    List<Order> findByUserId(Long id);

}
