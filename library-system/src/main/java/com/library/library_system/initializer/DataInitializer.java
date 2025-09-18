package com.library.library_system.initializer;

import com.library.library_system.model.Role;
import com.library.library_system.model.User;
import com.library.library_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Create an initial admin if none exist
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // use a safe password
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Created admin user with username=admin and password=admin123");
        }


        userRepository.findAll().forEach(u -> {
            String pw = u.getPassword();
            if (pw != null && !(pw.startsWith("$2a$") || pw.startsWith("$2b$") || pw.startsWith("$2y$"))) {
                u.setPassword(passwordEncoder.encode(pw));
                userRepository.save(u);
                System.out.println("Encoded password for user: " + u.getUsername());
            }
        });
    }
}

