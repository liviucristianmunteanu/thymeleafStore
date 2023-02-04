package com.thymeleaf.store.service.email;

import com.thymeleaf.store.entity.MyUser;

public interface EmailBodyService {
    String emailBody (MyUser myUser);

}
