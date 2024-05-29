package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.RoleDTO;
import com.example.roombooking.dto.UserDTO;
import com.example.roombooking.security.Role;
import com.example.roombooking.security.RoleRepository;
import com.example.roombooking.security.User;
import com.example.roombooking.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private RoleDTO convertRoleToDto(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .users(role.getUsers()
                        .stream()
                        .map(User::getUsername)
                        .toArray(String[]::new))
                .build();
    }

    public UserDTO convertUserToDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .roleNames(user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .toArray(String[]::new))
                .build();
    }

    public User convertDtoToUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .enabled(userDTO.isEnabled())
                .roles(Arrays.stream(userDTO.getRoleNames())
                        .map(role -> roleRepository.findByName(role)
                                .orElseThrow(NoSuchElementException::new))
                        .toList())
                .build();
    }

    public List<RoleDTO> findAllRolesDto() {
        return roleRepository.findAll()
                .stream()
                .map(this::convertRoleToDto)
                .toList();
    }

    public UserDTO findUserById(UUID id) {
        return userRepository.findById(id)
                .map(this::convertUserToDto)
                .orElseThrow(NoSuchElementException::new);
    }

    public UserDTO findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertUserToDto)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertUserToDto)
                .toList();
    }

    public List<UserDTO> findAllUsersSorted(String sortOrder, String userNameColumn) {
        return userRepository.findAll(Sort.by(Sort.Direction.fromString(sortOrder), userNameColumn))
                .stream()
                .map(this::convertUserToDto)
                .toList();
    }

    public List<UserDTO> findAllUsersSortAndQuery(String query, String sortOrder, String sortColumn) {
        return userRepository.findAllByUsernameStartingWith(query, Sort.by(Sort.Direction.fromString(sortOrder), sortColumn))
                .stream()
                .map(this::convertUserToDto)
                .toList();
    }

    private Collection<Role> arrayRolesToCollection(String[] roles) {
        return Arrays.stream(roles)
                .map(role -> roleRepository.findByName(role)
                        .orElseThrow(NoSuchElementException::new))
                .collect(Collectors.toList());
    }

    public String addUser(UserDTO user) {
        var message = new AtomicReference<String>();

        return userRepository.findByUsername(user.getUsername())
                .map(foundUser -> {
                    message.set("User with username: %s exists".formatted(foundUser.getUsername()));
                    LOGGER.warn(message.get());
                    return message.get();
                })
                .orElseGet(() -> {
                    User newUser = convertDtoToUser(user);
                    newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
                    newUser.setEnabled(!newUser.getRoles().isEmpty());
                    userRepository.save(newUser);
                    message.set("User with username: %s added".formatted(user.getUsername()));
                    LOGGER.info(message.get());
                    return message.get();
                });
    }

    public String updateUser(UserDTO user) {
        var message = new AtomicReference<String>();

        return userRepository.findById(user.getId())
                .map(foundUser -> {
                    foundUser.setUsername(user.getUsername());
                    foundUser.setEnabled(user.isEnabled());
                    foundUser.setRoles(arrayRolesToCollection(user.getRoleNames()));
                    userRepository.save(foundUser);
                    message.set("User with username: %s updated".formatted(user.getUsername()));
                    LOGGER.info(message.get());
                    return message.get();
                })
                .orElseGet(() -> {
                    message.set("User with username: %s not found".formatted(user.getUsername()));
                    LOGGER.warn(message.get());
                    return message.get();
                });
    }

    public String deleteUser(String userName) {
        var message = new AtomicReference<String>();

        return userRepository.findByUsername(userName)
                .map(foundUser -> {
                    userRepository.delete(foundUser);
                    message.set("User with username: %s deleted".formatted(foundUser.getUsername()));
                    LOGGER.info(message.get());
                    return message.get();
                })
                .orElseGet(() -> {
                    message.set("User with username: %s not found".formatted(userName));
                    LOGGER.warn(message.get());
                    return message.get();
                });
    }
}
