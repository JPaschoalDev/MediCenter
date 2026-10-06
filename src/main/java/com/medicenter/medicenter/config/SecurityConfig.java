package com.medicenter.medicenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))
                .authorizeHttpRequests(auth -> auth
                        // públicos: login e cadastro
                        .requestMatchers("/api/auth/me").authenticated()
                        .requestMatchers("/api/auth/**").permitAll()
                        // somente funcionário
                        .requestMatchers("/api/usuarios/**").hasRole("FUNCIONARIO")
                        .requestMatchers("/api/funcionarios/**").hasRole("FUNCIONARIO")
                        // médicos: qualquer logado consulta, só funcionário altera
                        .requestMatchers(HttpMethod.GET, "/api/medicos/**").authenticated()
                        .requestMatchers("/api/medicos/**").hasRole("FUNCIONARIO")
                        // pacientes: médico consulta, funcionário altera
                        .requestMatchers(HttpMethod.GET, "/api/pacientes/**").hasAnyRole("FUNCIONARIO", "MEDICO")
                        .requestMatchers("/api/pacientes/**").hasRole("FUNCIONARIO")
                        // qualquer outra rota da API exige login
                        .requestMatchers("/api/**").authenticated()
                        // páginas HTML, CSS e JS ficam livres (os dados é que são protegidos)
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuracao) throws Exception {
        return configuracao.getAuthenticationManager();
    }
}