package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.RoleDTO;
import com.example.roombooking.security.Role;
import com.example.roombooking.security.RoleRepository;
import com.example.roombooking.security.User;
import com.example.roombooking.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

    public RoleDTO convertToDto(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .users(role.getUsers()
                        .stream()
                        .map(User::getUsername)
                        .toArray(String[]::new))
                .build();
    }

    public Role convertDtoToRole(RoleDTO role) {
        return Role.builder()
                .id(role.getId())
                .name(role.getName())
                .users(Arrays.stream(role.getUsers())
                        .map(user -> userRepository.findByUsername(user)
                                .orElseThrow(NoSuchElementException::new))
                        .toList())
                .build();
    }

    public Role findRoleById(UUID id) {
        return roleRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(NoSuchElementException::new);
    }

    public RoleDTO findRoleByNameDto(String name) {
        return convertToDto(findRoleByName(name));
    }

    public List<RoleDTO> findAllRolesDto() {
        return roleRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<RoleDTO> findAllRolesSorted(String sortOrder, String userNameColumn) {
        return roleRepository.findAll(Sort.by(Sort.Direction.fromString(sortOrder), userNameColumn))
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<RoleDTO> findAllRolesSortAndQuery(String query, String sortOrder, String sortColumn) {
        return roleRepository.findAllByNameStartingWith(query, Sort.by(Sort.Direction.fromString(sortOrder), sortColumn))
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public String addRole(RoleDTO role) {
        var message = new AtomicReference<String>();

        return roleRepository.findByName(role.getName())
                .map(foundRole -> {
                    message.set("Role %s exists".formatted(foundRole.getName()));
                    LOGGER.warn(message.get());
                    return message.get();
                })
                .orElseGet(() -> {
                    roleRepository.save(convertDtoToRole(role));
                    message.set("Role %s added".formatted(role.getName()));
                    LOGGER.info(message.get());
                    return message.get();
                });
    }

    public String deleteRole(String userName) {
        var message = new AtomicReference<String>();

        return roleRepository.findByName(userName)
                .map(foundRole -> {
                    roleRepository.delete(foundRole);
                    message.set("Role %s deleted".formatted(foundRole.getName()));
                    LOGGER.info(message.get());
                    return message.get();
                })
                .orElseGet(() -> {
                    message.set("Role %s not found".formatted(userName));
                    LOGGER.warn(message.get());
                    return message.get();
                });
    }
}
