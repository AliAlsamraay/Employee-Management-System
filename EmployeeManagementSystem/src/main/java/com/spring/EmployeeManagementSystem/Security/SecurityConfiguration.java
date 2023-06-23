package com.spring.EmployeeManagementSystem.Security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

        @Bean
        public UserDetailsManager userDetailsManager(DataSource datasource) {
                return new JdbcUserDetailsManager(datasource);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                // give the admin role permission to delete.
                // give the manager role permission to add and update.
                // give the employee role permission to view.
                http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers(HttpMethod.DELETE, "/api/**")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/employees/**",
                                                "api/manager/**",
                                                "api/attendance-records",
                                                "api/attendance-records", "api/attendance-records")
                                .hasAnyRole("ADMIN", "MANAGER")
                                // the employee can create leave requests for himself.
                                .requestMatchers(HttpMethod.POST,
                                                "/api/leave-requests")
                                .hasAnyRole("ADMIN", "MANAGER", "EMPLOYEE")
                                .requestMatchers(HttpMethod.PUT, "/api/employees", "/api/employees/**",
                                                "/api/manager/**",
                                                "api/attendance-records",
                                                "api/attendance-records", "api/attendance-records/**",
                                                "/api/leave-requests", "/api/leave-requests/**")
                                .hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers(HttpMethod.GET, "/api/leave-requests", "/api/leave-requests/**",
                                                "/api/employees",
                                                "/api/employees/**",
                                                "/api/manager/**",
                                                "api/attendance-records",
                                                "api/attendance-records/**")
                                .hasAnyRole("ADMIN", "MANAGER", "EMPLOYEE"));

                // use basic authentication for all requests.
                http.httpBasic(Customizer.withDefaults());
                // disable csrf because we are using REST API.
                http.csrf(csrf -> csrf.disable());
                return http.build();
        }

        // @Bean
        // public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        // // Create users and roles programmatically.
        // final UserDetails employee = User.builder()
        // .username("employee").password("{noop}pass123").roles("EMPLOYEE").build();

        // final UserDetails manager = User.builder()
        // .username("manager").password("{noop}pass123").roles("EMPLOYEE",
        // "MANAGER").build();

        // final UserDetails admin = User.builder().username("admin")
        // .password("{noop}pass123").roles("ADMIN", "EMPLOYEE", "MANAGER").build();

        // return new InMemoryUserDetailsManager(admin, manager, employee);
        // }

}