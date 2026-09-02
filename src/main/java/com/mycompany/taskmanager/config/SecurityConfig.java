package com.mycompany.taskmanager.config;

import com.mycompany.taskmanager.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity

public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

public SecurityConfig(
        JwtAuthenticationFilter jwtAuthenticationFilter) {

    this.jwtAuthenticationFilter =
            jwtAuthenticationFilter;
}

   @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())

        .sessionManagement(session ->
            session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
            )
        )

        .exceptionHandling(exception ->
            exception.authenticationEntryPoint(
                (request, response, authException) -> {

                    response.setStatus(401);
                    response.setContentType(
                        "application/json;charset=UTF-8"
                    );

                    response.getWriter().write(
                        """
                        {
                          "status": 401,
                          "erro": "Unauthorized",
                          "mensagem": "Usuário não autenticado"
                        }
                        """
                    );
                }
            )
        )

        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/api/auth/register",
                "/api/auth/login",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-ui.html"
            ).permitAll()

            .requestMatchers(
                "/api/v1/tarefas/**"
            ).authenticated()

            .anyRequest().permitAll()
        );

    http.addFilterBefore(
        jwtAuthenticationFilter,
        UsernamePasswordAuthenticationFilter.class
    );

    return http.build();
}
}
