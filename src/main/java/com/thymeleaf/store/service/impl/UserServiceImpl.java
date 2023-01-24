package com.thymeleaf.store.service.impl;

import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.repository.UserRepository;
import com.thymeleaf.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;

    public MyUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public MyUser findUserByUserName(String userName) {
        return userRepository.findByUsernameIgnoreCase(userName);
    }

    @Override
    public MyUser findUserByRandomToken(String randomToken) {
        return null;
    }


    public boolean findUserByUserNameAndPassword(String userName, String password) {
        final Optional<MyUser> myUser = Optional.ofNullable(userRepository.findByUsernameIgnoreCase(userName));
        return myUser.filter(user -> BCrypt.checkpw(password, user.getPassword())).isPresent();
    }

    public List<MyUser> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public MyUser saveUser(MyUser receivedUser) {
        MyUser myUser = new MyUser(receivedUser);
        myUser.setPassword(new BCryptPasswordEncoder().encode(receivedUser.getPassword()));
        myUser.setRandomToken(UUID.randomUUID().toString());
        return userRepository.save(myUser);
    }

    @Override
    public void updateUser(MyUser user) {

    }


    @Override
    public Optional<MyUser> findById(Long id) {
        return userRepository.findById(id);
    }


}
