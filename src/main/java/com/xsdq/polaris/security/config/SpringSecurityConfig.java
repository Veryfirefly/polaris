package com.xsdq.polaris.security.config;

import com.xsdq.polaris.security.PolarisAuthenticationEntryPoint;
import com.xsdq.polaris.security.PolarisLogoutSuccessHandler;
import com.xsdq.polaris.security.PolarisUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new PolarisUserDetailsService();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new PolarisLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new PolarisAuthenticationEntryPoint();
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(configure ->
                        configure.requestMatchers("/api/auth/login", "/api/auth/logout").permitAll())
                .build();
    }
}
