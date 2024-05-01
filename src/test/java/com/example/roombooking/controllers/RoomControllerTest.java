package com.example.roombooking.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomControllerTest {
    @Autowired
    private RoomController roomController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(roomController).isNotNull();
    }
    @Test
    void getAllRooms() {
    }

    @Test
    void getRoom() {
    }

    @Test
    void searchRooms() {
    }
}