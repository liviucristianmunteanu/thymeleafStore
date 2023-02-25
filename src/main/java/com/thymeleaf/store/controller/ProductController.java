package com.thymeleaf.store.controller;

import com.thymeleaf.store.entity.MyUser;
import com.thymeleaf.store.entity.Product;
import com.thymeleaf.store.entity.ShoppingCartProductQuantity;
import com.thymeleaf.store.repository.ProductRepository;
import com.thymeleaf.store.repository.ShoppingCartProductQuantityRepository;
import com.thymeleaf.store.service.ShoppingCartService;
import com.thymeleaf.store.service.UserService;
import com.thymeleaf.store.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartProductQuantityRepository quantityRepository;

    @RequestMapping(value = {"/all"})
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);

        List<Product> productsByShoppingCartId = quantityRepository.getProductsByShoppingCartId(userByUserName.getId());
        model.addAttribute("products", productRepository.findByQuantityGreaterThan(0L));
        model.addAttribute("cartSize", productsByShoppingCartId.size());
        return "products";
    }
    @RequestMapping(value = "/add/{id}")
    public String addProductToShoppingCart(@PathVariable Integer id, @ModelAttribute("product") @RequestBody Product frontendProduct) {
        //cautam produsul dupa id
        Optional<Product> optionalProduct = productRepository.findById(id);

        //stabilim care e username-ul user-ului autentificat
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        //aducem userul din db pe baza username-ului
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);

        Integer quantityToBeOrdered = frontendProduct.getQuantity();

        //in shopping cart-ul userului adus adaugam produsul trimis din frontend
        optionalProduct.ifPresent(product -> {
            //setez pe produs quantity-ul si il adaug in shopping cart
            Product productToBeAddedToShoppingCart = new Product();
            productToBeAddedToShoppingCart.setId(product.getId());
            productToBeAddedToShoppingCart.setPrice(product.getPrice());
            productToBeAddedToShoppingCart.setName(product.getName());
            productToBeAddedToShoppingCart.setQuantity(quantityToBeOrdered);
            userByUserName.getShoppingCart().addProductToShoppingCart(productToBeAddedToShoppingCart);




                    product.setQuantity(product.getQuantity() - quantityToBeOrdered);
            quantityRepository.save(new ShoppingCartProductQuantity(userByUserName.getId().intValue(), product.getId(), quantityToBeOrdered));
            productRepository.save(product);
            userService.updateUser(userByUserName);
            Integer cartSize =  quantityRepository.getProductsByShoppingCartId(userByUserName.getId()).size();




        });

        return Constants.REDIRECT_TO_PRODUCTS;
    }
    @GetMapping(value = "/add-new")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping(value = "/add-new")
    public String addProduct(@ModelAttribute("product") @RequestBody Product product) {
        productRepository.save(product);
        return Constants.REDIRECT_TO_PRODUCTS;
    }
}
