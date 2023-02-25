package com.thymeleaf.store.controller;

import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.entity.Order;
import com.thymeleaf.store.entity.Product;
import com.thymeleaf.store.repository.OrderRepository;
import com.thymeleaf.store.repository.ProductRepository;
import com.thymeleaf.store.repository.ShoppingCartProductQuantityRepository;
import com.thymeleaf.store.service.ShoppingCartService;
import com.thymeleaf.store.service.UserService;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartProductQuantityRepository quantityRepository;


//    @PutMapping(value = "/add/{cartId}")
//    public ResponseEntity addProductToShoppingCart(@PathVariable Integer cartId, @RequestParam Integer productId) {
//
//        Product product = productRepository.findById(productId).orElseThrow();
//        ShoppingCart cart = shoppingCartService.findById(cartId).orElseThrow();
//
//        cart.addProductToShoppingCart(product);
//        shoppingCartService.update(cart);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping(value = "/remove/{cartId}")
//    public ResponseEntity removeProductFromShoppingCart(@PathVariable Integer cartId, @RequestParam Integer productId) {
//
//        Product product = productRepository.findById(productId).orElseThrow();
//        ShoppingCart cart = shoppingCartService.findById(cartId).orElseThrow();
//
//        cart.removeProductFromShoppingCart(product);
//        shoppingCartService.update(cart);
//
//        return ResponseEntity.ok().build();
//    }
    @Transactional
    @RequestMapping(value = "/to-order")
    public String convertToOrder(Model model) {

        //stabilim care e username-ul user-ului autentificat
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        //aducem userul din db pe baza username-ului
        MyUser user = userService.findUserByUserName(currentPrincipalName);

        List<Product> productsByShoppingCartId = quantityRepository.getProductsByShoppingCartId(user.getId());
        shoppingCartService.findById(user.getId().intValue()).ifPresent(cart -> {
            cart.setProducts(productsByShoppingCartId);
            user.setShoppingCart(cart);
        });

        Order order = orderRepository.save(shoppingCartService.convertShoppingCartToOrder(user.getShoppingCart()));
        user.getShoppingCart().getProducts().clear();
        quantityRepository.deleteByShoppingCartId(user.getId().intValue());
        model.addAttribute("order", order);
        return "order-successful";
    }

    @RequestMapping
    public String getShoppingCartForPrincipal(Model model) {
        //stabilim care e username-ul user-ului autentificat
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        //aducem userul din db pe baza username-ului
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);

        List<Product> productsByShoppingCartId = quantityRepository.getProductsByShoppingCartId(userByUserName.getId());


        model.addAttribute("products", productsByShoppingCartId);

        model.addAttribute("cartSize", productsByShoppingCartId.size());


        return "shopping-cart";
    }


    @RequestMapping(value = "/product/remove/{productId}")
    public String removeProductFromShoppingCart(@PathVariable Integer productId) {
        //stabilim care e username-ul user-ului autentificat
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        //aducem userul din db pe baza username-ului
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);

        Optional<Product> optionalProduct = productRepository.findById(productId);

        userByUserName.getShoppingCart().getProducts().removeIf(product -> product.getId().equals(productId));
        userService.updateUser(userByUserName);

        return "redirect:/shopping-cart";
    }

}
