package com.example.roombooking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserDataSeeder {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void seed() {
        if (roleRepository.findByName("Admin").isEmpty()) {
            addRole("Admin");
        }
        if (roleRepository.findByName("Receptionist").isEmpty()) {
            addRole("Receptionist");
        }
        if (userRepository.findByUsername("walter.white@crystal.com").isEmpty()) {
            addUser("walter.white@crystal.com", "Admin");
        }
        if (userRepository.findByUsername("jesse.pinkman@crystal.com").isEmpty()) {
            addUser("jesse.pinkman@crystal.com", "Receptionist");

        }
    }

    private void addUser(String mail, String group) {
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName(group)
                .orElseThrow(() -> new NoSuchElementException("Role was not found in the database"));

        roles.add(role);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("123");
        User user = User.builder()
                .enabled(true)
                .password(hash)
                .username(mail)
                .roles(roles)
                .build();
        userRepository.save(user);
    }

    private void addRole(String name) {
        roleRepository.save(Role.builder()
                .name(name)
                .build());
    }

}
