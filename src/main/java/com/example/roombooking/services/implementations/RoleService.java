package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.RoleDTO;
import com.example.roombooking.security.Role;
import com.example.roombooking.security.RoleRepository;
import com.example.roombooking.security.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

    public RoleDTO convertToDto(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public Role convertDtoToRole(RoleDTO role) {
        return Role.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public Role findRoleById(UUID id) {
        return roleRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public RoleDTO findRoleByNameDto(String name) {
        return roleRepository.findByName(name)
                .map(this::convertToDto)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<RoleDTO> findAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }


//    public List<UserDTO> findAllUsersSorted(String sortOrder, String userNameColumn) {
//        return userRepository.findAll(Sort.by(Sort.Direction.fromString(sortOrder), userNameColumn))
//                .stream()
//                .map(this::convertToDto)
//                .toList();
//    }
//
//    public List<UserDTO> findAllUsersSortAndQuery(String query, String sortOrder, String sortColumn) {
//        return userRepository.findAllByUsernameStartingWith(query, Sort.by(Sort.Direction.fromString(sortOrder), sortColumn))
//                .stream()
//                .map(this::convertToDto)
//                .toList();
//    }

//    public String addUser(UserDTO user) {
//        var message = new AtomicReference<String>();
//
//        return userRepository.findByUsername(user.getUsername())
//                .map(foundUser -> {
//                    message.set("User with username: %s exists".formatted(foundUser.getUsername()));
//                    LOGGER.warn(message.get());
//                    return message.get();
//                })
//                .orElseGet(() -> {
//                    userRepository.save(convertDtoToUser(user));
//                    message.set("User with username: %s added".formatted(user.getUsername()));
//                    LOGGER.info(message.get());
//                    return message.get();
//                });
//    }
//
//    public String updateUser(UserDTO user) {
//        var message = new AtomicReference<String>();
//
//        return userRepository.findById(user.getId())
//                .map(foundUser -> {
//                    foundUser.setUsername(user.getUsername());
//                    foundUser.setEnabled(user.isEnabled());
//                    foundUser.setRoles(user.getRoles());
//                    userRepository.save(foundUser);
//                    message.set("User with username: %s updated".formatted(user.getUsername()));
//                    LOGGER.info(message.get());
//                    return message.get();
//                })
//                .orElseGet(() -> {
//                    message.set("Customer with username: %s not found".formatted(user.getUsername()));
//                    LOGGER.warn(message.get());
//                    return message.get();
//                });
//    }

//    public String deleteUser(String userName) {
//        var message = new AtomicReference<String>();
//
//        return userRepository.findByUsername(userName)
//                .map(foundUser -> {
//                    userRepository.delete(foundUser);
//                    message.set("User with username: %s deleted".formatted(foundUser.getUsername()));
//                    LOGGER.info(message.get());
//                    return message.get();
//                })
//                .orElseGet(() -> {
//                    message.set("User with username: %s not found".formatted(userName));
//                    LOGGER.warn(message.get());
//                    return message.get();
//                });
//    }
}
