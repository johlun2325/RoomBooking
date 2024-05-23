package com.example.roombooking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDataSeeder {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void seed() {
        if (roleRepository.findByName("Admin") == null) {
            addRole("Admin");
        }
        if (roleRepository.findByName("Customer") == null) {
            addRole("Customer");
        }
        if(userRepository.getUserByUsername("ivan.radovan@hotmail.com") == null){
            addUser("ivan.radovan@hotmail.com","Admin");
        }
        if(userRepository.getUserByUsername("milo.radovan@systementor.hotmail.com") == null){
            addUser("milo.radovan@systementor.hotmail.com","Receptionist");
        }
    }

    private void addUser(String mail, String group) {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(group));

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
