package com.CinephileLog.configuration;

import com.CinephileLog.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public WebSecurityCustomizer configure() {      //Disable Spring Security Features
        return web -> web.ignoring().requestMatchers("/static/**","/api/**");
    }

    public WebSecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    //Configure web-based security for specific HTTP requests
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/images/**","/css/**","/login","/user", "/admin/**").permitAll()
                        // .anyRequest().authenticated())
                        .anyRequest().permitAll()) // 모든 요청에 permitAll() 적용
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .failureHandler((request, response, exception) -> {
                            exception.printStackTrace();
                        })
                        .loginPage("/login") // Custom login page Url
                        .defaultSuccessUrl("/checkNickname", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .csrf(auth -> auth.disable());
        return httpSecurity.build();
    }

}