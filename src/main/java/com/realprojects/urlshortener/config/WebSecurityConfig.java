package com.realprojects.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//This is a SECURITY FILTER
//Any request hitting on any of the controllers/request handlers should pass through this
//It's purpose is to restrict certain URLs for guest (example home page) and others for authenticated users and admin
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/error", "/webjars/**", "/css/**", "/js/**", "/images/**",
                    "/", "/short-urls", "/s/**", "/register", "/login"
                ).permitAll() //These paths are permitted to all/ guest users
                .requestMatchers("/my-urls").authenticated()//Permitted to logged in users
                .requestMatchers("/admin/**").hasRole("ADMIN")//permitted to admin users
                .anyRequest().authenticated()//All the other paths apart form above specified shall require authenticated user
            )
            .formLogin(form -> form
                .loginPage("/login")//path to our own login page
                .defaultSuccessUrl("/")//Upon successful login user will be redirected to homepage
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();// web security getting built finally.
    }
}