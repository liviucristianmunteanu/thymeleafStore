package com.thymeleaf.store.config;

import com.thymeleaf.store.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider())
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/login",
                        "/register",
                        "/activation/**",
                        "/activation-success",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .defaultSuccessUrl("/users").and()
                .logout().logoutSuccessUrl("/login").and()
                .sessionManagement()
                .maximumSessions(1);
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
