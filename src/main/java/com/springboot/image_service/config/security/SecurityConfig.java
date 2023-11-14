package com.springboot.image_service.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // configure access control

/*
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // configure http security
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/home").permitAll()
                        .anyRequest().authenticated()) // allow only authenticated requests
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                }));

        return http.build();
    }

 */


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // configure http security

        http
                .authorizeRequests()
                .antMatchers("/test").permitAll() //TODO: remove this after tests
                .antMatchers("/file").permitAll() //TODO: remove this after tests
                .anyRequest().authenticated();

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
        }));

        return http.build();
    }
}
