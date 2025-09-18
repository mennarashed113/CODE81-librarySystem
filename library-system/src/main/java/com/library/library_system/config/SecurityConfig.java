package com.library.library_system.config;


import com.library.library_system.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/users/createUsers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/books/**").hasAnyRole("LIBRARIAN", "STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/books").hasAnyRole("LIBRARIAN", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/books/**").hasAnyRole("LIBRARIAN", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/book-copies").hasAnyRole("LIBRARIAN", "STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/book-copies/**").hasAnyRole("LIBRARIAN", "STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/book-copies/**").hasAnyRole("LIBRARIAN", "STAFF", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/book-copies/**").hasAnyRole("LIBRARIAN", "ADMIN")




                        .requestMatchers(HttpMethod.POST, "/api/borrowings").hasRole("STAFF")
                        .requestMatchers(HttpMethod.POST, "/api/borrowings/return").hasRole("STAFF")
                        .requestMatchers(HttpMethod.GET, "/api/borrowings/**").hasAnyRole("LIBRARIAN", "ADMIN","STAFF")

                        .requestMatchers("/api/members/**").hasAnyRole("LIBRARIAN", "ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
