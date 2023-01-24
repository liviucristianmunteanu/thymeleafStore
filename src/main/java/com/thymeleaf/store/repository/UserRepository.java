package com.thymeleaf.store.repository;

import com.thymeleaf.store.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByUsernameIgnoreCase(String username);

    MyUser findByEmail(String username);
}
