package com.thymeleaf.store.controller;

import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/users")
    private String listUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }
}



