package com.thymeleaf.store.service;

import com.thymeleaf.store.entity.Order;
import com.thymeleaf.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public List<Order> findOrders() {
        return orderRepository.findAll();
    }
}
