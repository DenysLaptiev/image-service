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

//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//
//        // configure http security
//
//        http
//                .authorizeHttpRequests(authz -> authz
//                                .requestMatchers(HttpMethod.GET,"/add-subscription/**").permitAll()
//                                .requestMatchers(HttpMethod.GET,"/add-subscription-email/**").permitAll()
//                        .anyRequest().authenticated()) // allow only authenticated requests
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
//                })); // incoming requests should contain jwt token
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // configure http security
        http
                .authorizeHttpRequests(authz -> authz
                        //.requestMatchers(HttpMethod.GET,"/","/home").permitAll()
                        .anyRequest().authenticated()) // allow only authenticated requests
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                }));

//        http.oauth2Login(Customizer.withDefaults()); // incoming requests should contain jwt token

//        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
