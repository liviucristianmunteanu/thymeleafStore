package com.thymeleaf.store.controller;

import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.entity.Order;
import com.thymeleaf.store.repository.OrderRepository;
import com.thymeleaf.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/get-all/{userId}")
    public List<Order> getAllOrders(@PathVariable Long userId) throws UserPrincipalNotFoundException {
        Optional<MyUser> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent())
            return optionalUser.get().getOrders();
        else
            //TODO Custom exception
            throw new UserPrincipalNotFoundException("User not found");
    }

}
