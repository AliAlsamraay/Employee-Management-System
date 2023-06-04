package com.spring.EmployeeManagementSystem.EmployeeManagementSystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableMethodSecurity
// public class SecurityConfiguration {
// @Bean
// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// // Define authorization rules based on URL patterns.
// http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
// .requestMatchers("/admin/**").hasRole("ADMIN")
// .requestMatchers("/employee/**").hasAnyRole("ADMIN", "EMPLOYEE")
// .anyRequest().authenticated())
// .formLogin(form -> form.loginPage("/login").permitAll())
// .logout(logout -> logout.permitAll());
// return http.build();
// }

// }