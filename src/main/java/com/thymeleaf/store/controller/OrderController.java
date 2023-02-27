package com.thymeleaf.store.controller;

import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.entity.Order;
import com.thymeleaf.store.repository.OrderRepository;
import com.thymeleaf.store.repository.UserRepository;
import com.thymeleaf.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    @GetMapping(value="/all")
    public String findAllOrders(Model model){
        List<Order> orders = orderService.findOrders();
        model.addAttribute("orders", orderService.findOrders());
        return "orders";
    }

    @GetMapping(value = "/user/{id}")
    public String getAllOrders(@PathVariable Long id, Model model) throws UserPrincipalNotFoundException {
        Optional<MyUser> optionalUser = userRepository.findById(id);

        List<Order> OrderListByUser = orderRepository.findByUserId(id);
        model.addAttribute("orders", OrderListByUser);
       /* if (optionalUser.isPresent())

            return optionalUser.get().getOrders();
        else
            //TODO Custom exception
            throw new UserPrincipalNotFoundException("User not found");*/
        return "orders";
    }

}
