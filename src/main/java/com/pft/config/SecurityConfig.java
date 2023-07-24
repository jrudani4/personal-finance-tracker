package com.pft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers("/register", "/error", "/css/**", "/js/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                .formLogin(
                        li ->
                                li
                                        .loginPage("/login")
                                        .defaultSuccessUrl("/")
                                        .permitAll()
                )
                .logout(
                        lo ->
                                lo
                                        .logoutUrl("/logout")
                                        .logoutSuccessUrl("/login?logout")
                                        .permitAll()
                );
        return http.build();
    }
}
