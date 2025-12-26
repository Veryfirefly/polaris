package com.xsdq.polaris.security.config;

import java.util.List;

import com.xsdq.polaris.security.JwtTokenAuthenticationFilter;
import com.xsdq.polaris.security.PolarisAccessDeniedHandler;
import com.xsdq.polaris.security.PolarisAuthenticationEntryPoint;
import com.xsdq.polaris.security.PolarisLogoutSuccessHandler;
import com.xsdq.polaris.security.PolarisUserDetailsService;
import com.xsdq.polaris.security.autoconfigure.PolarisSecurityProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
    public JwtTokenAuthenticationFilter authenticationFilter() {
        return new JwtTokenAuthenticationFilter();
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
    public AccessDeniedHandler accessDeniedHandler() {
        return new PolarisAccessDeniedHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(
            HttpSecurity http,
            JwtTokenAuthenticationFilter authenticationFilter,
            LogoutSuccessHandler logoutSuccessHandler,
            AuthenticationEntryPoint authenticationEntryPoint,
            AccessDeniedHandler accessDeniedHandler,
            PolarisSecurityProperties props) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(configurer -> configurer.configurationSource(corsConfigurationSource()))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(props.toPermitUrls()).permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(configurer ->
                        configurer.authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler))
                .logout(configurer ->
                        configurer.logoutUrl(props.getLogoutUrl()).logoutSuccessHandler(logoutSuccessHandler)
                                .clearAuthentication(false))
                .addFilterBefore(authenticationFilter, LogoutFilter.class)
                .build();
    }

    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(false);
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
