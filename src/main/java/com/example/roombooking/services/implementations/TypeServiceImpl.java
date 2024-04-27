package com.example.roombooking.services.implementations;

import com.example.roombooking.repos.TypeRepo;
import com.example.roombooking.services.TypeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepo typeRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(TypeServiceImpl.class);



}
