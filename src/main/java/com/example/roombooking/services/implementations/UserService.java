package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.ContractCustomerDTO;
import com.example.roombooking.dto.UserDTO;
import com.example.roombooking.models.External.ContractCustomer;
import com.example.roombooking.repos.ContractCustomerRepo;
import com.example.roombooking.security.User;
import com.example.roombooking.security.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserDTO convertToDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .roles(user.getRoles())
                .build();
    }

    public UserDTO findUserByUsername(String username) {
        return userRepository.getUserByUsername(username)
                .map(this::convertToDto)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<UserDTO> findAllUsersSorted(String sortOrder, String userNameColumn) {
        return userRepository.findAll(Sort.by(Sort.Direction.fromString(sortOrder), userNameColumn))
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<UserDTO> findAllUsersSortAndQuery(String query, String sortOrder, String sortColumn) {
        return userRepository.findAllByUsernameStartingWith(query, Sort.by(Sort.Direction.fromString(sortOrder), sortColumn))
                .stream()
                .map(this::convertToDto)
                .toList();
    }

}
