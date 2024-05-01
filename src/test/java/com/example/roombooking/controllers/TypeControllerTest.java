package com.example.roombooking.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TypeControllerTest {
    @Autowired
    private TypeController typeController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(typeController).isNotNull();
    }
    @Test
    void getAllTypes() {
    }

    @Test
    void getType() {
    }
}