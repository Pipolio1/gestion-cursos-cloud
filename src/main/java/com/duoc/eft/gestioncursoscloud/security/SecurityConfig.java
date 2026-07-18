package com.duoc.eft.gestioncursoscloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtAuthConverter);

        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/actuator/**", "/error").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/cursos", "/api/examenes", "/api/calificaciones").hasAnyRole("INSTRUCTOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/cursos/**", "/api/examenes/**", "/api/calificaciones/**").hasAnyRole("INSTRUCTOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/cursos/**", "/api/examenes/**", "/api/calificaciones/**").hasAnyRole("INSTRUCTOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/inscripciones").hasAnyRole("ESTUDIANTE", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/estudiantes").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/estudiantes/**").hasAnyRole("ADMIN")
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
            );

        return http.build();
    }
}
