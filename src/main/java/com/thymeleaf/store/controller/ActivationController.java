package com.thymeleaf.store.controller;

import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.repository.UserRepository;
import com.thymeleaf.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ActivationController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/activation/{randomToken}")
    public String registerForm(@PathVariable String randomToken, Model model) {
        final Optional<MyUser> userByRandomToken = Optional.ofNullable(userService.findUserByRandomToken(randomToken));
        if (userByRandomToken.isPresent()) {
            model.addAttribute("user", userByRandomToken.get());
            return "activation";
        } else
            return "invalid-token";

    }

    @PostMapping(value = "/activation/{randomToken}")
    public String registerUser(@ModelAttribute("user") @RequestBody MyUser user) {
        final MyUser myUser = userService.findUserByRandomToken(user.getRandomToken());
        myUser.setEnabled(true);
        userRepository.save(myUser);
        return "activation-success";
    }

}
