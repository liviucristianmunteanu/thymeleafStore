package com.thymeleaf.store.startup;

import com.github.javafaker.Faker;
import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.entity.Product;
import com.thymeleaf.store.entity.Role;
import com.thymeleaf.store.entity.ShoppingCart;
import com.thymeleaf.store.repository.ProductRepository;
import com.thymeleaf.store.repository.RoleRepository;
import com.thymeleaf.store.repository.UserRepository;
import com.thymeleaf.store.service.ShoppingCartService;
import com.thymeleaf.store.service.UserService;
import com.thymeleaf.store.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class RunAtStartup {
    private final UserService userService;

    private final RoleRepository roleRepository;

    private final ProductRepository productRepository;

    private final ShoppingCartService shoppingCartService;

    private final UserRepository userRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {

        roleRepository.save(new Role(Constants.ROLE_USER));
        roleRepository.save(new Role(Constants.ROLE_ADMIN));

        saveUser();
        //saveUserToDelete();
        saveAdminUser();
        save50Products();
    }

    private void saveAdminUser() {
        MyUser myUser = new MyUser();
        myUser.setUsername("admin");
        myUser.setPassword("admin");
        myUser.setRandomToken("randomToken");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Constants.ROLE_ADMIN));
        myUser.setRoles(roles);
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUser.setEmail("munteanuion6511@gmail.com");
        myUser.setFullName("Ion Admin");
        myUser.setPasswordConfirm("admin");
        myUser.setRandomTokenEmail("randomToken");

        userService.saveUser(myUser);
    }

    public void saveUser() {
        Faker faker = new Faker();
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
        myUser.setEmail("mystikal_eu@yahoo.com");
        myUser.setFullName("Ion User");
        myUser.setPasswordConfirm("user");
        myUser.setRandomTokenEmail("randomToken");

        MyUser myUser1 = userService.saveUser(myUser);

        List<Product> products = Stream
                .generate(() -> productRepository.save(
                        new Product(faker.company().logo(),faker.commerce().productName(), faker.lorem().sentence(), faker.number().numberBetween(100, 10000),  faker.number().numberBetween(0, 199)))
                )
                .limit(3)
                .toList();

        ShoppingCart cart = myUser1.getShoppingCart();
        cart.setUser(myUser1);
        cart.setProducts(products);

//        ShoppingCart myCart = shoppingCartService.save(cart);

//        myUser1.setShoppingCart(cart);
        userService.updateUser(myUser1);

    }

    public void save50Products() {
        Faker faker = new Faker();
        Stream
                .generate(() -> productRepository.save(
                        new Product(faker.company().logo(),faker.commerce().productName(), faker.lorem().sentence(), faker.number().numberBetween(100, 10000),  faker.number().numberBetween(0, 199)))
                )
                .limit(5)
                .toList();
    }

}
