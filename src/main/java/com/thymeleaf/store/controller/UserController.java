package com.thymeleaf.store.controller;

import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}



