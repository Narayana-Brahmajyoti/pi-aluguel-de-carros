package com.DH.PI.config.security;

import com.DH.PI.service.AutenticacaoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })

public class SecurityConfig  {
    @Autowired
    AutenticacaoService service;
    private AuthenticationManager authenticationManager;
    @Autowired
    private FilterToken filter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

         return http.cors().and().csrf().disable()
                 .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and().authorizeHttpRequests()
                 .requestMatchers("/auth").permitAll()
                 .requestMatchers(HttpMethod.GET,"/caracteristica").hasAnyAuthority("USER","ADMIN")
                 .requestMatchers(HttpMethod.POST,"/caracteristica").hasAuthority("ADMIN")
                 .requestMatchers(HttpMethod.GET, "/categoria").permitAll()
                 .requestMatchers(HttpMethod.POST, "/categoria").hasAuthority("ADMIN")
                 .requestMatchers(HttpMethod.PATCH, "/categoria").hasAuthority("ADMIN")
                 .requestMatchers(HttpMethod.DELETE,"/categoria").hasAuthority("ADMIN")
                 .requestMatchers(HttpMethod.GET, "/cidade").permitAll()
                 .requestMatchers(HttpMethod.POST, "/cidade").hasAnyAuthority("ADMIN")
                 .requestMatchers(HttpMethod.DELETE, "/cidade").hasAuthority("ADMIN")
                 .requestMatchers(HttpMethod.PATCH, "/cidade").hasAuthority("ADMIN")
                 .requestMatchers("/imagem").hasAuthority("ADMIN")
                 .requestMatchers("/sending-email").permitAll()
                 .requestMatchers(HttpMethod.GET,"/produto/**").permitAll()
                 .requestMatchers(HttpMethod.POST,"/produto").hasAuthority("ADMIN")
                 .requestMatchers(HttpMethod.PATCH,"/produto").hasAuthority("ADMIN")
                 .requestMatchers(HttpMethod.DELETE,"/produto").hasAuthority("ADMIN")
                 .requestMatchers("/reserva").hasAnyAuthority("USER","ADMIN")
                 .requestMatchers("/usuario/admin").hasAuthority("ADMIN")
                 .requestMatchers(HttpMethod.GET,"/usuario").hasAuthority("ADMIN")
                 .requestMatchers(HttpMethod.POST,"/usuario").permitAll()
                 .requestMatchers(HttpMethod.PATCH,"/usuario").hasAnyAuthority("USER", "ADMIN")
                 .requestMatchers(HttpMethod.DELETE,"/usuario").hasAuthority("ADMIN")
                 .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                 .anyRequest().authenticated()
                 .and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                 .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder passwordEncoder,
                                                       AutenticacaoService autenticacaoService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(autenticacaoService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
    }
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PATCH"));
        configuration.addAllowedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return (CorsConfigurationSource) source;
    }

}




