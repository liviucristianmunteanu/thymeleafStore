package com.thymeleaf.store.startup;

import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.entity.Role;
import com.thymeleaf.store.repository.RoleRepository;
import com.thymeleaf.store.service.UserService;
import com.thymeleaf.store.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RunAtStartup {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {

        roleRepository.save(new Role(Constants.ROLE_USER));
        roleRepository.save(new Role(Constants.ROLE_ADMIN));

        saveUser();
        saveAdminUser();
    }

    private void saveAdminUser() {
        MyUser myUser = new MyUser();
        myUser.setUsername("admin");
        myUser.setPassword("admin");
        myUser.setRandomToken("randomToken");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Constants.ROLE_USER));
        roles.add(roleRepository.findByName(Constants.ROLE_ADMIN));
        myUser.setRoles(roles);
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUser.setEmail("admin1@gmail.com");
        myUser.setFullName("Ion Admin");
        myUser.setPasswordConfirm("admin");
        myUser.setRandomTokenEmail("randomToken");

        userService.saveUser(myUser);
    }

    public void saveUser() {
        MyUser myUser = new MyUser();
        myUser.setUsername("user");
        myUser.setPassword("user");
        myUser.setRandomToken("randomToken");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Constants.ROLE_USER));
        myUser.setRoles(roles);
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUser.setEmail("user@gmail.com");
        myUser.setFullName("Ion User");
        myUser.setPasswordConfirm("user");
        myUser.setRandomTokenEmail("randomToken");

        userService.saveUser(myUser);
    }


}
