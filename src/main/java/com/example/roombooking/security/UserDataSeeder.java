package com.example.roombooking.security;

import com.example.roombooking.configurations.IntegrationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserDataSeeder {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    IntegrationProperties integrationProperties;

    public void seed() {
        if (roleRepository.findByName(integrationProperties.getSeed().getRole1()).isEmpty()) {
            addRole(integrationProperties.getSeed().getRole1());
        }
        if (roleRepository.findByName(integrationProperties.getSeed().getRole2()).isEmpty()) {
            addRole(integrationProperties.getSeed().getRole2());
        }
        if (userRepository.findByUsername(integrationProperties.getSeed().getUsername1()).isEmpty()) {
            addUser(integrationProperties.getSeed().getUsername1(), integrationProperties.getSeed().getRole1());
        }
        if (userRepository.findByUsername(integrationProperties.getSeed().getUsername2()).isEmpty()) {
            addUser(integrationProperties.getSeed().getUsername2(), integrationProperties.getSeed().getRole2());

        }
    }

    private void addUser(String mail, String group) {
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName(group)
                .orElseThrow(() -> new NoSuchElementException("Role was not found in the database"));

        roles.add(role);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(integrationProperties.getSeed().getPassword());
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
