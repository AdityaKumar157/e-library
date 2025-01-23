package com.makeprojects.library.elib.security;

import com.makeprojects.library.elib.security.filters.JwtAuthenticationFilter;
import com.makeprojects.library.elib.security.filters.JwtRequestFilter;
import com.makeprojects.library.elib.service.CustomOAuth2UserService;
import com.makeprojects.library.elib.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter, JwtUtil jwtUtil) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
                authenticationManager(httpSecurity.getSharedObject(AuthenticationConfiguration.class)), jwtUtil);
        jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(authorize -> {
           authorize.requestMatchers("/book/**").hasAnyRole("MEMBER", "LIBRARIAN")
                   .requestMatchers("/member/**").hasRole("LIBRARIAN")
                   .requestMatchers("/public/**", "/auth/**, /oauth2/**").permitAll()
                   .anyRequest().authenticated();})
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/github")
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint(userInfo -> userInfo.userService(this.customOAuth2UserService())))
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(this.jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        //httpSecurity.httpBasic(Customizer.withDefaults());

        //httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return httpSecurity.build();
    }

    public CustomOAuth2UserService customOAuth2UserService() {
        return new CustomOAuth2UserService();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        //return authenticationConfiguration.getAuthenticationManager();
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(this.userDetailsService);
//        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
//        return new ProviderManager(authenticationProvider);

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
