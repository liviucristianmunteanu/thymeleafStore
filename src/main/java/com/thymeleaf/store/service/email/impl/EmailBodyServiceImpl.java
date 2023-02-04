package com.thymeleaf.store.service.email.impl;

import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.service.email.EmailBodyService;
import org.springframework.stereotype.Service;

@Service
public class EmailBodyServiceImpl implements EmailBodyService {

    @Override
    public String emailBody(MyUser myUser) {
        return "Hello," +
                "In order to activate your account please access the following link:\n" +
                "http://localhost:8080/activation/" + myUser.getRandomToken();
    }
}
