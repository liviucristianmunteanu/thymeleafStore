package com.thymeleaf.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.thymeleaf.store.entity.Product;
import com.thymeleaf.store.entity.ShoppingCartProductQuantity;

import java.util.List;

public interface ShoppingCartProductQuantityRepository extends JpaRepository<ShoppingCartProductQuantity, Long> {
    @Query(value = "SELECT new com.thymeleaf.store.entity.Product(p.id, p.imageUrl, p.name,p.description, p.price, s.quantity) from Product p inner join ShoppingCartProductQuantity s " +
            "on p.id = s.productId where shoppingCartId = :id")
    List<Product> getProductsByShoppingCartId(Long id);

    @Modifying
    @Transactional
    void deleteByShoppingCartIdAndProductId(Integer shoppingCartId, Integer productId);
}
