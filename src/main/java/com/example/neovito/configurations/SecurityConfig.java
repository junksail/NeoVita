package com.example.neovito.configurations;

import com.example.neovito.service.CustomUserDetailsService;


import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // Включение безопасности на уровне методов;
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService; // реализация интерфейса. Отвечает за загрузку информации о пользователе.

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http ) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/product/**",
                                "/images/**",
                                "/registration",
                                "/user/**",
                                "static/**") // разрешение доступа к набору URL для всех пользователей;
                        .permitAll().anyRequest().authenticated()). // гарантирует, что остальные запросы потребуют аутентификации;
                formLogin((form) -> form.loginPage("/login").permitAll()) // гарантирует, что юзер будет перенаправлен на страницу для входа;
                .logout((logout) -> logout.permitAll()); // позволяет любому юзеру производить выход;
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); // класс для проверки учетных данных
        authenticationProvider.setUserDetailsService(userDetailsService); // информация о сервисе, используемом для загрузки пользователя
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // установка кодировщика
        return authenticationConfiguration.getAuthenticationManager(); // получаем готоый объект, отвечающий за аутентификацию пользователя
    }

    /**
     * хэширование паролей. Сложность 8 в угоду быстродействию, но жертвуя безопасностью.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

}