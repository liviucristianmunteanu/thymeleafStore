package com.thymeleaf.store.repository;

import com.thymeleaf.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);
   Product findProductById(Integer id);

    List<Product> findByQuantityGreaterThan(Long quantity);
}
