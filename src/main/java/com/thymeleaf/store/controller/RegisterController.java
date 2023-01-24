package com.thymeleaf.store.controller;

import com.thymeleaf.store.service.UserService;
import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.repository.RoleRepository;
import com.thymeleaf.store.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final RoleRepository roleRepository;

    private final UserService userService;

    @GetMapping(value = "/register")
    public String registerForm(Model model) {
        MyUser user = new MyUser();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(false);
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute("user") @RequestBody MyUser user) {
        if (user.getPassword().equals(user.getPasswordConfirm())) {
            user.setRoles(Set.of(roleRepository.findByName(Constants.ROLE_USER)));
            userService.saveUser(user);
            return "register-success";
        } else {
            return "register";
        }
    }

}
