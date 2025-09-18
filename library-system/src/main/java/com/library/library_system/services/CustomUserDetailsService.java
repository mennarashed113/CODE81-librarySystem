package com.library.library_system.services;


import com.library.library_system.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library.library_system.model.User;

import java.util.List;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.library.library_system.model.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // normalize role (handle enum or string stored with/without ROLE_ prefix)
        String rawRole = user.getRole() == null ? "USER" : user.getRole().toString();
        if (rawRole.startsWith("ROLE_")) {
            rawRole = rawRole.substring(5);
        }
        String authority = "ROLE_" + rawRole;

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
