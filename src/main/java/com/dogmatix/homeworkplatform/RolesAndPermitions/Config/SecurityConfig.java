package com.dogmatix.homeworkplatform.RolesAndPermitions.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login", "/api/register", "/api/classes/**").permitAll() // Allow these endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/teacher/classes").hasAnyRole("TEACHER", "STUDENT", "ADMIN")
                        .requestMatchers("/teacher/**").hasRole("TEACHER")
                        .requestMatchers("/student/**").hasAnyRole("STUDENT", "TEACHER")
                        .requestMatchers(HttpMethod.GET, "/homeworks/**").hasAnyRole("STUDENT", "TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/homeworks/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/homeworks/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/homeworks/**").hasRole("TEACHER")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")  // Custom logout URL
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200);
                            response.getWriter().write("Logout successful");
                        })
                )
                .formLogin(withDefaults()) //form-based auth
                .httpBasic(withDefaults()) //Basic Auth
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
