package com.thymeleaf.store.controller;

import com.thymeleaf.store.util.Constants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
   /* @GetMapping("/login")
    String login() {
        return "login";
    }*/

    @RequestMapping(value = {"/login", "/"})
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            System.out.println(authentication);
            return "login";
        }
        return Constants.REDIRECT_TO_USERS;
    }


    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }
}
