package com.xsdq.polaris.security.config;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsdq.polaris.security.DefaultAccessDeniedHandler;
import com.xsdq.polaris.security.DefaultAuthenticationEntryPoint;
import com.xsdq.polaris.security.DefaultLogoutSuccessHandler;
import com.xsdq.polaris.security.DynamicDecisionAuthorizationManager;
import com.xsdq.polaris.security.JwtTokenAuthenticationFilter;
import com.xsdq.polaris.security.PolarisUserDetails;
import com.xsdq.polaris.security.PolarisUserDetailsService;
import com.xsdq.polaris.security.TokenManager;
import com.xsdq.polaris.security.autoconfigure.PolarisSecurityProperties;
import com.xsdq.polaris.service.MenuService;
import com.xsdq.polaris.service.RoleService;
import com.xsdq.polaris.service.TenantService;
import com.xsdq.polaris.service.UserService;
import com.xsdq.polaris.tenant.TenantFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationManager;
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
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
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
    public UserDetailsService userDetailsService(
            UserService userService,
            TenantService tenantService,
            RoleService roleService,
            PolarisSecurityProperties securityProperties) {
        return new PolarisUserDetailsService(userService, tenantService, roleService, securityProperties);
    }

    @Bean
    public JwtTokenAuthenticationFilter authenticationFilter(TokenManager<PolarisUserDetails> tokenManager) {
        return new JwtTokenAuthenticationFilter(tokenManager);
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(TokenManager<PolarisUserDetails> tokenManager, ObjectMapper objectMapper) {
        return new DefaultLogoutSuccessHandler(tokenManager, objectMapper);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
        return new DefaultAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
        return new DefaultAccessDeniedHandler(objectMapper);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> dynamicDecisionAuthorizationManager(MenuService menuService) {
        return new DynamicDecisionAuthorizationManager(menuService);
    }

    @Bean
    public TenantFilter tenantFilter() {
        return new TenantFilter();
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(
            HttpSecurity http,
            JwtTokenAuthenticationFilter authenticationFilter,
            LogoutSuccessHandler logoutSuccessHandler,
            AuthenticationEntryPoint authenticationEntryPoint,
            AccessDeniedHandler accessDeniedHandler,
            AuthorizationManager<RequestAuthorizationContext> authorizationManager,
            PolarisSecurityProperties securityProps) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(configurer -> configurer.configurationSource(corsConfigurationSource()))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(securityProps.whitelistUrls()).permitAll()
                                .anyRequest().access(authorizationManager))
                .exceptionHandling(configurer ->
                        configurer.authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler))
                .logout(configurer ->
                        configurer.logoutRequestMatcher(securityProps.logoutRequestMatcher())
                                .logoutSuccessHandler(logoutSuccessHandler)
                                .clearAuthentication(false))
                .addFilterBefore(authenticationFilter, LogoutFilter.class)
                .addFilterBefore(tenantFilter(), LogoutFilter.class)
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
